package com.zds.flow.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.zds.biz.constant.user.UserTypeEnum;
import com.zds.biz.util.ThreadLocalUtil;
import com.zds.biz.vo.IdRequest;
import com.zds.biz.vo.request.flow.FlowCommentRequest;
import com.zds.biz.vo.request.flow.FlowProcessDetailRequest;
import com.zds.biz.vo.request.flow.FlowProcessUnitRequest;
import com.zds.biz.vo.request.user.WorkCalendarRequest;
import com.zds.biz.vo.response.flow.*;
import com.zds.biz.vo.response.user.WorkCalendarResponse;
import com.zds.flow.dao.HdDemoDao;
import com.zds.flow.dao.TblNodeDepartmentDao;
import com.zds.flow.dao.TblProjectShipDao;
import com.zds.flow.dao.TblTaskShipDao;
import com.zds.flow.feign.UserService;
import com.zds.flow.po.TblNodeDepartment;
import com.zds.flow.po.TblProjectShip;
import com.zds.flow.po.TblTaskShip;
import com.zds.flow.service.FlowService;
import com.zds.flow.service.FlowTaskService;
import org.apache.commons.lang3.StringUtils;
import org.camunda.bpm.engine.*;
import org.camunda.bpm.engine.history.HistoricTaskInstance;
import org.camunda.bpm.engine.identity.Group;
import org.camunda.bpm.engine.identity.User;
import org.camunda.bpm.engine.task.Task;
import org.camunda.bpm.model.bpmn.BpmnModelInstance;
import org.camunda.bpm.model.bpmn.instance.UserTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class FlowTaskServiceImpl implements FlowTaskService {

    @Autowired
    private UserService userService;

    @Autowired
    private TblTaskShipDao taskShipDao;

    @Autowired
    private TblProjectShipDao projectShipDao;

    @Autowired
    private TaskService taskService;

    @Autowired
    private HistoryService historyService;

    @Autowired
    private IdentityService identityService;

    @Autowired
    private TblNodeDepartmentDao nodeDepartmentDao;

    @Autowired
    private FlowService flowService;

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private RuntimeService runtimeService;

    

    @Autowired
    private ThreadLocalUtil threadLocalUtil;

    @Autowired
    private HdDemoDao hdDemoDao;

    @Override
    public FlowCommentResponse getTaskComments(String taskId) {
        LambdaQueryWrapper<TblTaskShip> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TblTaskShip::getTaskId, taskId);
        TblTaskShip flowTaskShip = taskShipDao.selectOne(queryWrapper);
        IdRequest id = new IdRequest();
        id.setId(Long.valueOf(flowTaskShip.getTodoId()));
        FlowCommentReponse data = userService.examineDetail(id).getData();
        return FlowCommentResponse.builder()
                .id(String.valueOf(data.getTodoId()))
                .commentText(data.getCommentText())
                .commentAnnex(data.getCommentAnnex()).build();
    }

    @Override
    public List<FlowProcessDetailResponse> details(FlowProcessDetailRequest request) {
        List<FlowProcessDetailResponse> responseList = new ArrayList<>();
        //项目Id查询流程Id
        LambdaQueryWrapper<TblProjectShip> Wrapper = new LambdaQueryWrapper<>();
        Wrapper.eq(TblProjectShip::getProjectId, request.getId());
        Wrapper.eq(TblProjectShip::getProjectType, request.getType());
        TblProjectShip flowProjectShip = projectShipDao.selectOne(Wrapper);
        if (ObjectUtils.isEmpty(flowProjectShip)) {
            return null;
        }
        String processInstanceId = flowProjectShip.getProcessInstanceId();
        // 查询既未删除又未完成的历史任务
        List<HistoricTaskInstance> unfinishedTasks = historyService.createHistoricTaskInstanceQuery()
                .processInstanceId(processInstanceId)
                .list();

        if (!unfinishedTasks.isEmpty()) {
            List<String> taskNodeIds = unfinishedTasks.stream()
                    .map(HistoricTaskInstance::getTaskDefinitionKey)
                    .collect(Collectors.toList());
            List<String> taskId = unfinishedTasks.stream()
                    .map(HistoricTaskInstance::getId)
                    .collect(Collectors.toList());

            LambdaQueryWrapper<TblNodeDepartment> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.in(TblNodeDepartment::getNodeId, taskNodeIds);
            List<TblNodeDepartment> nodeDepartments = nodeDepartmentDao.selectList(queryWrapper);

            LambdaQueryWrapper<TblTaskShip> queryWrapper2 = new LambdaQueryWrapper<>();
            queryWrapper2.in(TblTaskShip::getTaskId, taskId);
            List<TblTaskShip> tblTaskShips = taskShipDao.selectList(queryWrapper2);

            Map<String, TblNodeDepartment> nodeDepartmentMap = nodeDepartments.stream()
                    .collect(Collectors.toMap(TblNodeDepartment::getNodeId, Function.identity()));

            Map<String, TblTaskShip> taskMap = tblTaskShips.stream()
                    .collect(Collectors.toMap(TblTaskShip::getTaskId, Function.identity()));


            for (HistoricTaskInstance task : unfinishedTasks) {
                if (task.getEndTime() == null) {
                    continue;
                }
                Group group = identityService.createGroupQuery().groupMember(task.getAssignee()).singleResult();
                User user = identityService.createUserQuery().userId(task.getAssignee()).singleResult();

                FlowProcessDetailResponse response = new FlowProcessDetailResponse();
                response.setInitiateDate(task.getStartTime());
                response.setName(task.getName());
                response.setTaskId(task.getTaskDefinitionKey());
                response.setAssignee(user.getFirstName());
                response.setGroup(group.getName());

                response.setTodoId(taskMap.get(task.getId()).getTodoId());
                IdRequest id = new IdRequest();
                id.setId(Long.valueOf(taskMap.get(task.getId()).getTodoId()));
                FlowCommentReponse data = userService.examineDetail(id).getData();

                if (ObjectUtils.isEmpty(data)) {
                    response.setComment(null);
                } else if (StringUtils.isNotEmpty(data.getPass()) && "1".equals(data.getPass())) {
                    response.setComment("通过");
                    response.setFiles(data.getCommentAnnex());
                    response.setPicture(data.getCommentPicture());
                } else if (StringUtils.isNotEmpty(data.getPass()) && "2".equals(data.getPass())) {
                    response.setComment("不通过");
                    response.setFiles(data.getCommentAnnex());
                    response.setPicture(data.getCommentPicture());
                }
                //判断是否超期
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(task.getStartTime());
                calendar.add(Calendar.DAY_OF_MONTH, nodeDepartmentMap.get(task.getTaskDefinitionKey()).getDueDay());
                Date resultDate = calendar.getTime();
                if (task.getEndTime() != null) {
                    WorkCalendarResponse data1 = userService.checkWork(WorkCalendarRequest.builder()
                            .startTime(task.getStartTime())
                            .endTime(task.getEndTime())
                            .workDay(nodeDepartmentMap.get(task.getTaskDefinitionKey()).getDueDay())
                            .build()).getData();

                    response.setTackleDate(task.getEndTime());
                    response.setOverdue(data1.getWorkStatus() == 3);
                }
                response.setPass(data.getPass());
                response.setRemarks(data.getCommentText());
                response.setDueDate(resultDate);
                response.setDueDay(nodeDepartmentMap.get(task.getTaskDefinitionKey()).getDueDay());
                responseList.add(response);
            }
        } else {
            return null;
        }
        return responseList;
    }

    @Override
    public void completeAndComment(FlowCommentRequest flowCommentRequest) {
        if ("1".equals(flowCommentRequest.getPass())) {
            String taskId = flowService.getTaskId(flowCommentRequest.getTodoId());

            String processDefinitionId = taskService.createTaskQuery()
                    .taskId(taskId)
                    .singleResult().getProcessDefinitionId();

            taskService.createComment(taskId, processDefinitionId, flowCommentRequest.getCommentText());
            taskService.complete(taskId);
        } else if ("2".equals(flowCommentRequest.getPass())) {
            String taskId = flowService.getTaskId(flowCommentRequest.getTodoId());
            String processInstanceId = taskService.createTaskQuery()
                    .taskId(taskId)
                    .singleResult().getProcessInstanceId();
            runtimeService.deleteProcessInstance(processInstanceId, "审核不通过");
        }
    }


    public String getPhone(String name) {
        Map<String , String> phone = new HashMap<>();
        phone.put("程俊男", "18875012194");
        phone.put("贺伟", "18983382110");
        phone.put("喻冬冬", "18687917311");
        phone.put("谭锐", "13678423200");
        phone.put("朱盈秋", "15023735973");
        phone.put("周励", "13678488106");
        phone.put("刘大海", "13667637257");
        phone.put("杨普", "18580736376");
        phone.put("王龙", "15922684024");
        phone.put("张愉", "13896076572");
        return phone.getOrDefault(name , "");
    }

    @Override
    public FlowProcessCompletionDegreeResponse completionDegree(FlowProcessDetailRequest request) {
        //项目Id查询流程Id
        LambdaQueryWrapper<TblProjectShip> Wrapper = new LambdaQueryWrapper<>();
        Wrapper.eq(TblProjectShip::getProjectId, request.getId());
        Wrapper.eq(TblProjectShip::getProjectType, request.getType());
        TblProjectShip flowProjectShip = projectShipDao.selectOne(Wrapper);
        if (ObjectUtils.isEmpty(flowProjectShip)) {
            return null;
        }
        // 获取流程定义的 BPMN 模型
        BpmnModelInstance bpmnModel = repositoryService.getBpmnModelInstance(flowProjectShip.getProcessDefinitionId());
        // 获取所有用户任务
        Collection<UserTask> userTasks = bpmnModel.getModelElementsByType(UserTask.class);
        // 查询已完成的历史任务实例
        List<HistoricTaskInstance> completedTasks = historyService.createHistoricTaskInstanceQuery()
                .processInstanceId(flowProjectShip.getProcessInstanceId())
                .finished()
                .list();

        FlowProcessCompletionDegreeResponse response = new FlowProcessCompletionDegreeResponse();
        if (userTasks.isEmpty() || completedTasks.isEmpty()) {
            response.setPercentage(0.00);
        } else {
            // 计算完成率
            double value = ((double) completedTasks.size() / userTasks.size());

            double roundedValue = Math.round(value * 100.0);
            response.setPercentage(roundedValue);
        }
        response.setId(flowProjectShip.getProcessInstanceId());

        return response;
    }

    @Override
    public Map<Long, List<String>> getProcessUnit(List<FlowProcessUnitRequest> request) {
        Map<Long, List<String>> response = new HashMap<>();
        List<Long> id = request.stream()
                .map(FlowProcessUnitRequest::getId)
                .collect(Collectors.toList());
        List<String> type = request.stream()
                .map(FlowProcessUnitRequest::getType)
                .collect(Collectors.toList());

        LambdaQueryWrapper<TblProjectShip> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(TblProjectShip::getProjectId, id);
        queryWrapper.in(TblProjectShip::getProjectType, type);
        List<TblProjectShip> tblProjectShips = projectShipDao.selectList(queryWrapper);
        Map<Long, TblProjectShip> map = new HashMap<>();
        for (TblProjectShip tblProjectShip : tblProjectShips) {
            if (ObjectUtils.isNotEmpty(tblProjectShip)) {
                map.put(tblProjectShip.getProjectId(), tblProjectShip);
            }
        }

        for (FlowProcessUnitRequest flowProcessUnitRequest : request) {
            if (ObjectUtils.isNotEmpty(map.get(flowProcessUnitRequest.getId()))) {
                String processInstanceId = map.get(flowProcessUnitRequest.getId()).getProcessInstanceId();
                List<Task> tasks = taskService.createTaskQuery()
                        .processInstanceId(processInstanceId)
                        .active()
                        .list();

                List<String> name = new ArrayList<>();
                for (Task task : tasks) {
                    Group group = identityService.createGroupQuery().groupMember(task.getAssignee()).singleResult();
                    name.add(group.getName());
                }
                response.put(flowProcessUnitRequest.getId(), name);

            } else {
                response.put(flowProcessUnitRequest.getId(), null);
            }
        }
        return response;
    }

    @Override
    public Map<Long, GasReviewTaskResponse> auditPermission(List<FlowProcessDetailRequest> requestList) {
        Map<Long, GasReviewTaskResponse> responseMap = new HashMap<>();
        String userId = threadLocalUtil.getUserId().toString();
        String userType = threadLocalUtil.getUserType();

        for (FlowProcessDetailRequest request : requestList) {
            TblProjectShip flowProjectShip = projectShipDao.selectOne(
                    new LambdaQueryWrapper<TblProjectShip>()
                            .eq(TblProjectShip::getProjectId, request.getId())
                            .eq(TblProjectShip::getProjectType, request.getType())
            );

            if (flowProjectShip == null) {
                continue;
            }
            // 查找当前流程实例中的活跃任务
            List<Task> tasks = taskService.createTaskQuery()
                    .processInstanceId(flowProjectShip.getProcessInstanceId())
                    .active()
                    .list();

            // 检查当前用户是否有审核权限
            boolean review = tasks.stream().anyMatch(task ->
                    userId.equals(task.getAssignee()) || UserTypeEnum.SYSTEM_ADMIN.getKey().equals(userType));

            String todoId = null;
            if (review) {
                String taskId = tasks.stream()
                        .filter(task -> userId.equals(task.getAssignee()) || UserTypeEnum.SYSTEM_ADMIN.getKey().equals(userType))
                        .map(Task::getId)
                        .findFirst()
                        .orElse(null);

                if (taskId != null) {
                    todoId = taskShipDao.selectOne(
                            TblTaskShip.getWrapper().eq(TblTaskShip::getTaskId, taskId)
                    ).getTodoId();
                }
            }
            GasReviewTaskResponse response = new GasReviewTaskResponse();
            response.setReview(review);
            response.setTodoListId(todoId);

            responseMap.put(request.getId(), response);
        }

        return responseMap;
    }

}
