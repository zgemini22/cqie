package com.zds.flow.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.zds.biz.constant.BaseException;
import com.zds.biz.constant.ResultValueEnum;
import com.zds.biz.constant.user.BasicDataKeyEnum;
import com.zds.biz.constant.user.UserTodoListEnum;
import com.zds.biz.util.ThreadLocalUtil;
import com.zds.biz.vo.BaseResult;
import com.zds.biz.vo.request.flow.FlowAddSignRequest;
import com.zds.biz.vo.request.flow.FlowCommentRequest;
import com.zds.biz.vo.request.flow.FlowEnumStartProcessRequest;
import com.zds.biz.vo.request.flow.FlowStartProcessRequest;
import com.zds.biz.vo.request.user.AdminAddTodoListBatchRequest;
import com.zds.biz.vo.request.user.AdminAddTodoListRequest;
import com.zds.biz.vo.request.user.BasicDataRequest;
import com.zds.biz.vo.response.flow.FlowStartResponse;
import com.zds.biz.vo.response.user.BasicDataResponse;
import com.zds.flow.dao.HdDemoDao;
import com.zds.flow.dao.TblNodeDepartmentDao;
import com.zds.flow.dao.TblProjectShipDao;
import com.zds.flow.dao.TblTaskShipDao;
import com.zds.flow.feign.UserService;
import com.zds.flow.po.HdDemo;
import com.zds.flow.po.TblNodeDepartment;
import com.zds.flow.po.TblProjectShip;
import com.zds.flow.po.TblTaskShip;
import com.zds.flow.service.FlowRuntimeService;
import org.camunda.bpm.engine.*;
import org.camunda.bpm.engine.history.HistoricActivityInstance;
import org.camunda.bpm.engine.identity.Group;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.instance.UserTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class FlowRuntimeServiceImpl implements FlowRuntimeService {

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TblProjectShipDao projectShipDao;

    @Autowired
    private IdentityService identityService;

    @Autowired
    private UserService userService;

    @Autowired
    private TblTaskShipDao taskShipDao;

    @Autowired
    private TaskService taskService;

    @Autowired
    private HistoryService historyService;

    @Autowired
    private TblNodeDepartmentDao nodeDepartmentDao;

    @Autowired
    private ThreadLocalUtil threadLocalUtil;

    @Autowired
    private RepositoryService repositoryService;

    

    @Autowired
    private HdDemoDao hdDemoDao;

    @Override
    public FlowStartResponse startProcessInstanceByKey(FlowStartProcessRequest flowStartProcessRequest) {
        if ("ACCIDENT_LINKAGE_FLOW".equals(flowStartProcessRequest.getProjectType())){
            //若为发起事故流程判断是否发起模拟流程
            boolean flag = Boolean.parseBoolean(BasicDataKeyEnum.accident_mock.getTitle());
            BaseResult<BasicDataResponse> result = userService.selectByKey(BasicDataRequest.builder().dataKey(BasicDataKeyEnum.accident_mock.getKey()).build());
            if (result != null && org.apache.commons.lang3.StringUtils.isNotEmpty(result.getData().getDataValue())) {
                flag = !result.getData().getDataValue().equals("0");
            }
            //模拟表发起
            if (flag) {
                List<HdDemo> hdDemos = hdDemoDao.selectList(HdDemo.getWrapper()
                        .eq(HdDemo::getAccidentId, 7L)
                        .eq(HdDemo::getType, 1));
                for (HdDemo hdDemo : hdDemos) {
                    hdDemo.setId(null);
                    hdDemo.setCompleteStatus(2);
                    hdDemo.setStatus(2);
                    hdDemo.setStartDate(new Date());
                    hdDemo.setInitiateDate(null);
                    hdDemo.setCommentConet(null);
                    hdDemo.setAccidentId(flowStartProcessRequest.getProjectId());
                }
                hdDemoDao.insertList(hdDemos);
                return new FlowStartResponse();
            }
        }

        //启动流程返回流程定义id
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(flowStartProcessRequest.getProcessDefinitionKey());
        //返回流程实例id与流程定义id
        FlowStartResponse response = new FlowStartResponse();
        response.setProcessInstanceId(processInstance.getId());
        response.setProcessDefinitionId(processInstance.getProcessDefinitionId());
        //关联业务id与流程id
        LambdaQueryWrapper<TblProjectShip> query = new LambdaQueryWrapper<>();
        query.eq(TblProjectShip::getProjectId, flowStartProcessRequest.getProjectId());
        query.eq(TblProjectShip::getProjectType, flowStartProcessRequest.getProjectType());
        TblProjectShip flowProjectShips = projectShipDao.selectOne(query);
        if (ObjectUtils.isNotEmpty(flowProjectShips)) {
            flowProjectShips.setProcessInstanceId(processInstance.getProcessInstanceId());
            flowProjectShips.setProcessDefinitionId(processInstance.getProcessDefinitionId());
            flowProjectShips.setInitiatorId(flowStartProcessRequest.getInitiatorId());
            flowProjectShips.setCreateTime(new Date());
            projectShipDao.update(flowProjectShips, query);
        } else {
            TblProjectShip approvalFlow = TblProjectShip.builder()
                    .processInstanceId(processInstance.getProcessInstanceId())
                    .processDefinitionId(processInstance.getProcessDefinitionId())
                    .projectId(flowStartProcessRequest.getProjectId())
                    .projectType(flowStartProcessRequest.getProjectType())
                    .initiatorId(flowStartProcessRequest.getInitiatorId())
                    .createTime(new Date())
                    .build();
            projectShipDao.insert(approvalFlow);
        }
        //查询task
        List<Task> list = taskService.createTaskQuery()
                .processInstanceId(processInstance.getProcessInstanceId())
                .list();
        if (ObjectUtils.isNotEmpty(list)) {
            List<String> nodeId = list.stream()
                    .map(Task::getTaskDefinitionKey)
                    .collect(Collectors.toList());

            LambdaQueryWrapper<TblNodeDepartment> queryWrapper1 = new LambdaQueryWrapper<>();
            queryWrapper1.in(TblNodeDepartment::getNodeId, nodeId);
            List<TblNodeDepartment> tblNodeDepartments = nodeDepartmentDao.selectList(queryWrapper1);

            Map<String, TblNodeDepartment> nodeDepartmentMap = tblNodeDepartments.stream()
                    .collect(Collectors.toMap(TblNodeDepartment::getNodeId, Function.identity()));

            List<TblTaskShip> list1 = new ArrayList<>();
            List<AdminAddTodoListRequest> insertList = new ArrayList<>();
            for (Task task : list) {
                Group group = identityService.createGroupQuery().groupMember(task.getAssignee()).singleResult();
                //添加待办
                AdminAddTodoListRequest build = AdminAddTodoListRequest.builder()
                        .initiatorId(flowStartProcessRequest.getInitiatorId())
                        .assigneeId(task.getAssignee())
                        .name(task.getName())
                        .dueTime(nodeDepartmentMap.get(task.getTaskDefinitionKey()).getDueDay())
                        .type(flowStartProcessRequest.getProjectType())
                        .organizationId(group.getId())
                        .dataId(task.getId()).build();
                insertList.add(build);
            }
            AdminAddTodoListBatchRequest addToListBatch = new AdminAddTodoListBatchRequest();
            addToListBatch.setList(insertList);
            BaseResult<Map<Long, String>> longBaseResult = userService.addToListBatch(addToListBatch);
            if (longBaseResult.getCode() != ResultValueEnum.SYS_OK.getKey()) {
                throw new BaseException("待办添加失败");
            }
            Map<Long, String> data = longBaseResult.getData();
            //组装关联数据
            for (Map.Entry<Long, String> entry : data.entrySet()) {
                Long todoId = entry.getKey();
                String taskId = entry.getValue();
                TblTaskShip taskShip = TblTaskShip.builder()
                        .taskId(taskId)
                        .todoId(String.valueOf(todoId))
                        .projectId(String.valueOf(flowStartProcessRequest.getProjectId())).build();
                list1.add(taskShip);
            }
            if (list1.size() > 0) {
                //添加待办与流程关联
                taskShipDao.insertList(list1);
            }
        }
        System.err.println(JSONObject.toJSONString(response));
        return response;
    }

    @Override
    @Transactional
    public Boolean startProcessInstanceByEnum(FlowEnumStartProcessRequest request) {
        //key启动流程返回流程定义id
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(request.getFlowEnum());
        //返回流程实例id与流程定义id
        FlowStartResponse response = new FlowStartResponse();
        response.setProcessInstanceId(processInstance.getId());
        response.setProcessDefinitionId(processInstance.getProcessDefinitionId());
        //关联业务id与流程id
        LambdaQueryWrapper<TblProjectShip> query = new LambdaQueryWrapper<>();
        query.eq(TblProjectShip::getProjectId, request.getProjectid());
        query.eq(TblProjectShip::getProjectType, request.getFlowEnum());
        TblProjectShip flowProjectShips = projectShipDao.selectOne(query);
        if (ObjectUtils.isNotEmpty(flowProjectShips)) {
            flowProjectShips.setProcessInstanceId(processInstance.getProcessInstanceId());
            flowProjectShips.setProcessDefinitionId(processInstance.getProcessDefinitionId());
            flowProjectShips.setInitiatorId(request.getInitiatorId());
            flowProjectShips.setCreateTime(new Date());
            projectShipDao.update(flowProjectShips, query);
        } else {
            TblProjectShip approvalFlow = TblProjectShip.builder()
                    .projectId(request.getProjectid())
                    .processInstanceId(processInstance.getProcessInstanceId())
                    .processDefinitionId(processInstance.getProcessDefinitionId())
                    .projectType(request.getFlowEnum())
                    .initiatorId(request.getInitiatorId())
                    .createTime(new Date())
                    .build();
            projectShipDao.insert(approvalFlow);
        }

        //查询task
        List<Task> list = taskService.createTaskQuery()
                .processInstanceId(processInstance.getProcessInstanceId())
                .list();
        if (ObjectUtils.isNotEmpty(list)) {
            List<String> nodeId = list.stream()
                    .map(Task::getTaskDefinitionKey)
                    .collect(Collectors.toList());

            LambdaQueryWrapper<TblNodeDepartment> queryWrapper1 = new LambdaQueryWrapper<>();
            queryWrapper1.in(TblNodeDepartment::getNodeId, nodeId);
            List<TblNodeDepartment> tblNodeDepartments = nodeDepartmentDao.selectList(queryWrapper1);

            Map<String, TblNodeDepartment> nodeDepartmentMap = tblNodeDepartments.stream()
                    .collect(Collectors.toMap(TblNodeDepartment::getNodeId, Function.identity()));

            List<TblTaskShip> list1 = new ArrayList<>();
            List<AdminAddTodoListRequest> insertList = new ArrayList<>();
            for (Task task : list) {
                Group group = identityService.createGroupQuery().groupMember(task.getAssignee()).singleResult();
                //添加待办
                AdminAddTodoListRequest build = AdminAddTodoListRequest.builder()
                        .initiatorId(request.getInitiatorId())
                        .assigneeId(task.getAssignee())
                        .name(task.getName())
                        .dueTime(nodeDepartmentMap.get(task.getTaskDefinitionKey()).getDueDay())
                        .type(request.getFlowEnum())
                        .organizationId(group.getId())
                        .dataId(task.getId()).build();
                insertList.add(build);
            }
            AdminAddTodoListBatchRequest addToListBatch = new AdminAddTodoListBatchRequest();
            addToListBatch.setList(insertList);
            BaseResult<Map<Long, String>> longBaseResult = userService.addToListBatch(addToListBatch);
            if (longBaseResult.getCode() != ResultValueEnum.SYS_OK.getKey()) {
                throw new BaseException("待办添加失败");
            }
            Map<Long, String> data = longBaseResult.getData();
            //组装关联数据
            for (Map.Entry<Long, String> entry : data.entrySet()) {
                Long todoId = entry.getKey();
                String taskId = entry.getValue();
                TblTaskShip taskShip = TblTaskShip.builder()
                        .taskId(taskId)
                        .todoId(String.valueOf(todoId))
                        .projectId(String.valueOf(request.getProjectid())).build();
                list1.add(taskShip);
            }
            if (list1.size() > 0) {
                //添加待办与流程关联
                taskShipDao.insertList(list1);
            }
        }
        return true;
    }

    @Override
    public void stopProcessInstance(String processInstanceId) {
        String processDefinitionId = runtimeService.createProcessInstanceQuery()
                .processInstanceId(processInstanceId)
                .singleResult()
                .getProcessDefinitionId();

        //终止流程
        runtimeService.deleteProcessInstance(processInstanceId, "审核未通过");
    }

    @Override
    public void addSign(FlowAddSignRequest request) {
        for (String userId : request.getUserId()) {
            runtimeService.createProcessInstanceModification(request.getProcessInstanceId())
                    .startBeforeActivity(request.getNodeId())
                    .setVariable("user", userId)
                    .setAnnotation("加签")
                    .execute();
        }
    }

    @Override
    public Boolean autoEnd() throws InterruptedException {
        Map<String , String> commentMap = new HashMap<>();
        commentMap.put("上报重燃企业主要负责人","收到，已通知相关人员进行处理");
        commentMap.put("上报事发地镇街-镇街分管安全领导","收到，已通知相关人员进行处理");
        commentMap.put("上报区应急局-区应急局值班室","收到，已通知相关人员进行处理");
        commentMap.put("上报行业主管部-区经信委","收到，已通知相关人员进行处理");
        commentMap.put("区应急局-抢险救援","抢险队伍已经到达现场，正在进行现场救援工作。");
        commentMap.put("区卫健委-医疗救护","医疗救护组已经到达现场，正在进行现场救护工作。");
        commentMap.put("区公安分局-秩序维护","秩序维护组已经到达现场，正在进行现场秩序维护工作。");
        commentMap.put("区委宣传-舆情引导","舆情引导组已经到达现场，正在进行现场舆情引导工作。 ");
        commentMap.put("磁器口街道办事处-善后处置","善后处置组已经到达现场，正在进行现场善后处置工作。 ");
        commentMap.put("磁器口街道办事处-后勤保障","后勤保障组已经到达现场，正在进行现场后勤保障工作。 ");
        commentMap.put("区应急局-副局长","事故情况已知晓，相关人员正在赶赴现场。");
        commentMap.put("区发改委-抢险救援","已到达现场，正在按照预案开展工作。");
        commentMap.put("磁器口街道办事处负责人-秩序维护","已到达现场，正在按照预案开展工作。");
        commentMap.put("区委网信办-舆情引导","已到达现场，正在按照预案开展工作。");
        commentMap.put("区民政局-善后处置","已到达现场，正在按照预案开展工作。");
        commentMap.put("区经济信息委-后勤保障","已到达现场，正在按照预案开展工作。");
        commentMap.put("磁器口街道办主任-综合协调","事故情况已知晓，相关人员正在赶赴现场。");
        commentMap.put("区生态环境局","已到达现场，正在按照预案开展工作。");
        commentMap.put("区生态环境局-善后处置","已到达现场，正在按照预案开展工作。");
        commentMap.put("区住建委-抢险救援","已到达现场，正在按照预案开展工作。");
        commentMap.put("区医保局-善后处置","已到达现场，正在按照预案开展工作。");
        commentMap.put("区财政局-后勤保障","已到达现场，正在按照预案开展工作。");
        commentMap.put("区城管局-抢险救援","已到达现场，正在按照预案开展工作。");
        commentMap.put("区交通局-后勤保障","已到达现场，正在按照预案开展工作。");
        commentMap.put("区公安分局-抢险救援","已到达现场，正在按照预案开展工作。");
        commentMap.put("区商务委-后勤保障","已到达现场，正在按照预案开展工作。");
        commentMap.put("区消防救援支队-抢险救援","已到达现场，正在按照预案开展工作。");
        commentMap.put("磁器口街道办事处分管安全领导-抢险救援","已到达现场，正在按照预案开展工作。");
        commentMap.put("燃气经营企业-综合协调","已到达现场，正在按照预案开展工作。");


        //查询最新事故流程
        List<TblProjectShip> tblProjectShips = projectShipDao.selectList(TblProjectShip.getWrapper()
                .eq(TblProjectShip::getProjectType, UserTodoListEnum.ACCIDENT_LINKAGE_FLOW.getKey())
                .orderByDesc(TblProjectShip::getCreateTime));
        TblProjectShip tblProjectShip = tblProjectShips.get(0);

        if (tblProjectShip == null) {
            System.out.println("未找到事故联动流程");
            return false;
        }
        String processInstanceId = tblProjectShip.getProcessInstanceId();

        while (true) {
            // 获取未完成的活动实例
            List<HistoricActivityInstance> historicActivityInstanceList = historyService.createHistoricActivityInstanceQuery()
                    .processInstanceId(processInstanceId)
                    .unfinished()
                    .list();

            if (historicActivityInstanceList.isEmpty()) {
                System.out.println("所有任务节点均已完成");
                break;
            }

//            List<HistoricTaskInstance> taskList = historyService.createHistoricTaskInstanceQuery()
//                    .processInstanceId(processInstanceId)
//                    .orderByHistoricTaskInstanceEndTime().desc()
//                    .unfinished()
//                    .listPage(0, 4);
//            String fourthLastTaskId = "";
//            if (taskList != null && taskList.size() >= 4) {
//                // 获取倒数第四个任务
//                fourthLastTaskId = taskList.get(3).getId();
//            }

            for (HistoricActivityInstance historicActivityInstance : historicActivityInstanceList) {
//                if (Objects.equals(fourthLastTaskId, historicActivityInstance.getTaskId())){
//                    return true;
//                }
                String taskId = historicActivityInstance.getTaskId();
                String activityName = historicActivityInstance.getActivityName();
                String todoId = taskShipDao.selectOne(TblTaskShip.getWrapper().eq(TblTaskShip::getTaskId, taskId)).getTodoId();

                // 完成待办
                FlowCommentRequest flowCommentRequest = new FlowCommentRequest();
                flowCommentRequest.setTodoId(Long.valueOf(todoId));
                flowCommentRequest.setPass("1");
                flowCommentRequest.setCommentText(commentMap.getOrDefault(activityName , "收到，已通知相关人员进行处理"));
                userService.processedTodoList(flowCommentRequest);

            }
        }
        return true;
    }


    private static UserTask getLastUserTask(BpmnModelInstance bpmnModelInstance) {
        UserTask lastUserTask = null;

        // 获取流程定义的所有 UserTask 节点
        for (UserTask userTask : bpmnModelInstance.getModelElementsByType(UserTask.class)) {
            lastUserTask = userTask; // 不断更新最后一个 UserTask，直到循环结束
        }
        return lastUserTask;
    }
}

