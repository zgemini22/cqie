package com.zds.flow.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zds.biz.constant.BaseException;
import com.zds.biz.constant.dispose.SzCqFlowEnum;
import com.zds.biz.constant.flow.ProcessTypeEnum;
import com.zds.biz.constant.user.OrganizationTypeEnum;
import com.zds.biz.constant.user.UserTodoListEnum;
import com.zds.biz.util.ThreadLocalUtil;
import com.zds.biz.vo.IdRequest;
import com.zds.biz.vo.request.flow.*;
import com.zds.biz.vo.response.flow.*;
import com.zds.flow.dao.TblDraftDao;
import com.zds.flow.dao.TblNodeDepartmentDao;
import com.zds.flow.dao.TblProjectShipDao;
import com.zds.flow.dao.TblTaskShipDao;
import com.zds.flow.po.TblDraft;
import com.zds.flow.po.TblNodeDepartment;
import com.zds.flow.po.TblProjectShip;
import com.zds.flow.po.TblTaskShip;
import com.zds.flow.service.FlowService;
import org.apache.commons.lang3.StringUtils;
import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.impl.util.IoUtil;
import org.camunda.bpm.engine.repository.DeploymentWithDefinitions;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.camunda.bpm.engine.rest.dto.repository.ProcessDefinitionDiagramDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class FlowServiceImpl implements FlowService {
    @Autowired
    private TblProjectShipDao projectShipDao;

    @Autowired
    private TblDraftDao draftDao;

    @Autowired
    private TblNodeDepartmentDao nodeDepartmentDao;

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private TblTaskShipDao taskShipDao;

    @Autowired
    private ThreadLocalUtil threadLocalUtil;

    @Autowired
    private HistoryService historyService;

    @Override
    public IPage<FlowDeployListReponse> deployList(FlowDeployListRequest request) {
        String userType = threadLocalUtil.getOrganizationType();
        LambdaQueryWrapper<TblDraft> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotEmpty(request.getName())) {
            wrapper.like(TblDraft::getProcessName, request.getName());
        }
        if (StringUtils.isNotEmpty(request.getType())) {
            wrapper.eq(TblDraft::getProcessType, request.getType());
        }
        if (StringUtils.isNotEmpty(request.getStatus())) {
            wrapper.eq(TblDraft::getProcessStatus, request.getStatus());
        }
        wrapper.eq(TblDraft::getDeleted, false);
        if (userType.equals(OrganizationTypeEnum.COMPANY.getKey())) {
            List<String> typeList = new ArrayList<>();
            typeList.add(ProcessTypeEnum.GAS_SUPPLY.getKey());
            typeList.add(ProcessTypeEnum.GAS_STOP.getKey());
            typeList.add(ProcessTypeEnum.GAS_SUPPLY_PLAN.getKey());
            typeList.add(ProcessTypeEnum.GAS_STOP_PLAN.getKey());
            wrapper.in(TblDraft::getProcessType, typeList);
            wrapper.eq(TblDraft::getOrganizationId, threadLocalUtil.getOrganizationId());
        } else {
            List<String> typeList = new ArrayList<>();
            typeList.add(ProcessTypeEnum.GAS_SUPPLY.getKey());
            typeList.add(ProcessTypeEnum.GAS_STOP.getKey());
            typeList.add(ProcessTypeEnum.GAS_SUPPLY_PLAN.getKey());
            typeList.add(ProcessTypeEnum.GAS_STOP_PLAN.getKey());
            wrapper.notIn(TblDraft::getProcessType, typeList);
        }

        wrapper.orderByDesc(TblDraft::getCreateTime);
        Page<TblDraft> page = new Page<>(request.getPageNum(), request.getPageSize());
        Page<TblDraft> page1 = draftDao.selectPage(page, wrapper);
        return page1.convert(x -> FlowDeployListReponse.builder()
                .id(x.getId())
                .key(x.getProcessKey())
                .xml(x.getProcessXml())
                .name(x.getProcessName())
                .status(x.getProcessStatus().toString())
                .type(x.getProcessType())
                .createTime(x.getCreateTime())
                .build());
    }

    @Override
    public IPage<FlowDeployListReponse> deployListEnterprise(FlowDeployListRequest request) {
        LambdaQueryWrapper<TblDraft> wrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotEmpty(request.getName())) {
            wrapper.like(TblDraft::getProcessName, request.getName());
        }
        if (StringUtils.isNotEmpty(request.getType())) {
            wrapper.eq(TblDraft::getProcessType, request.getType());
        }
        if (StringUtils.isNotEmpty(request.getStatus())) {
            wrapper.eq(TblDraft::getProcessStatus, request.getStatus());
        }
        List<String> typeList = new ArrayList<>();
        typeList.add(ProcessTypeEnum.GAS_SUPPLY.getKey());
        typeList.add(ProcessTypeEnum.GAS_STOP.getKey());
        typeList.add(ProcessTypeEnum.GAS_SUPPLY_PLAN.getKey());
        typeList.add(ProcessTypeEnum.GAS_STOP_PLAN.getKey());
        wrapper.in(TblDraft::getProcessType, typeList);
        wrapper.eq(TblDraft::getOrganizationId, threadLocalUtil.getOrganizationId());

        wrapper.eq(TblDraft::getDeleted, false);
        wrapper.orderByDesc(TblDraft::getCreateTime);
        Page<TblDraft> page = new Page<>(request.getPageNum(), request.getPageSize());
        Page<TblDraft> page1 = draftDao.selectPage(page, wrapper);
        return page1.convert(x -> FlowDeployListReponse.builder()
                .id(x.getId())
                .key(x.getProcessKey())
                .xml(x.getProcessXml())
                .name(x.getProcessName())
                .status(x.getProcessStatus().toString())
                .type(x.getProcessType())
                .createTime(x.getCreateTime())
                .build());
    }

    @Override
    @Transactional
    public Boolean deploySave(FlowDeployAddRequest request) {
        LambdaQueryWrapper<TblDraft> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(TblDraft::getId, request.getId());
        wrapper.eq(TblDraft::getDeleted, false);
        TblDraft flowProcessDraft = draftDao.selectById(request.getId());

        if (ObjectUtils.isNotEmpty(flowProcessDraft)) {
            TblDraft build = TblDraft.builder()
                    .organizationId(threadLocalUtil.getOrganizationId())
                    .processKey(request.getKey())
                    .processName(request.getName())
                    .processType(request.getType())
                    .processStatus(2)
                    .processXml(request.getXml()).build();

            List<FlowNodeDepartmentRequest> nodeDepartment = request.getNodeDepartment();
            List<FlowNodeDepartmentRequest> updateNodeDepartment = new ArrayList<>();
            List<FlowNodeDepartmentRequest> insertNodeDepartment = new ArrayList<>();
            List<String> nodeId = nodeDepartment.stream()
                    .map(FlowNodeDepartmentRequest::getNodeId)
                    .collect(Collectors.toList());

            LambdaQueryWrapper<TblNodeDepartment> queryWrapper1 = new LambdaQueryWrapper<>();
            queryWrapper1.in(TblNodeDepartment::getNodeId, nodeId);
            List<TblNodeDepartment> tblNodeDepartments = nodeDepartmentDao.selectList(queryWrapper1);

            Map<String, TblNodeDepartment> nodeDepartmentMap = tblNodeDepartments.stream()
                    .collect(Collectors.toMap(TblNodeDepartment::getNodeId, Function.identity()));
            //检查节点有则更新无则新增
            for (FlowNodeDepartmentRequest flowNodeDepartmentRequest : nodeDepartment) {
                flowNodeDepartmentRequest.setProcessId(flowProcessDraft.getId());
                if (ObjectUtils.isEmpty(nodeDepartmentMap.get(flowNodeDepartmentRequest.getNodeId()))) {
                    insertNodeDepartment.add(flowNodeDepartmentRequest);
                } else {
                    updateNodeDepartment.add(flowNodeDepartmentRequest);
                }
            }
            if (ObjectUtils.isEmpty(insertNodeDepartment)) {
                nodeDepartmentDao.batchUpdate(updateNodeDepartment);
            } else {
                nodeDepartmentDao.insertList(insertNodeDepartment);
            }

            int i = draftDao.update(build, wrapper);
            return i > 0;
        } else {
            LambdaQueryWrapper<TblDraft> wrapper1 = new LambdaQueryWrapper<>();
            wrapper1.eq(TblDraft::getProcessType, request.getType());
            List<TblDraft> flowProcessDrafts = draftDao.selectList(wrapper1);
            if (ObjectUtils.isNotEmpty(flowProcessDrafts) && !Objects.equals(request.getType(), UserTodoListEnum.ACCIDENT_LINKAGE_FLOW.getKey())) {
                throw new BaseException("此类型流程已创建");
            }
            //供气停气
            wrapper1.eq(TblDraft::getOrganizationId, threadLocalUtil.getOrganizationId());
            List<TblDraft> flowProcessDrafts2 = draftDao.selectList(wrapper1);

            Set<String> restrictedProcessTypes = Set.of(
                    UserTodoListEnum.GAS_SUPPLY.getKey(),
                    UserTodoListEnum.GAS_STOP.getKey(),
                    UserTodoListEnum.GAS_SUPPLY_PLAN.getKey(),
                    UserTodoListEnum.GAS_STOP_PLAN.getKey()
            );
            if (ObjectUtils.isNotEmpty(flowProcessDrafts2) && restrictedProcessTypes.contains(request.getType())) {
                throw new BaseException("此类型流程已创建");
            }

            TblDraft build = TblDraft.builder()
                    .organizationId(threadLocalUtil.getOrganizationId())
                    .processKey(request.getKey())
                    .processName(request.getName())
                    .processType(request.getType())
                    .createTime(new Date())
                    .processXml(request.getXml()).build();
            int i = draftDao.insert(build);

            List<FlowNodeDepartmentRequest> nodeDepartment = request.getNodeDepartment();
            for (FlowNodeDepartmentRequest flowNodeDepartmentRequest : nodeDepartment) {
                flowNodeDepartmentRequest.setProcessId(build.getId());
            }
            nodeDepartmentDao.insertList(nodeDepartment);
            return i > 0;
        }
    }

    @Override
    @Transactional
    public void deployAndSave(FlowDeployRequest request) {
        LambdaQueryWrapper<TblDraft> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(TblDraft::getId, request.getId());
        wrapper.eq(TblDraft::getDeleted, false);
        TblDraft flowProcessDraft = draftDao.selectById(request.getId());

        if (ObjectUtils.isNotEmpty(flowProcessDraft)) {
            TblDraft build = TblDraft.builder()
                    .organizationId(threadLocalUtil.getOrganizationId())
                    .processKey(request.getKey())
                    .processName(request.getName())
                    .processType(request.getType())
                    .processStatus(1)
                    .processXml(request.getXml()).build();

            List<FlowNodeDepartmentRequest> nodeDepartment = request.getNodeDepartment();
            List<FlowNodeDepartmentRequest> updateNodeDepartment = new ArrayList<>();
            List<FlowNodeDepartmentRequest> insertNodeDepartment = new ArrayList<>();
            List<String> nodeId = nodeDepartment.stream()
                    .map(FlowNodeDepartmentRequest::getNodeId)
                    .collect(Collectors.toList());

            LambdaQueryWrapper<TblNodeDepartment> queryWrapper1 = new LambdaQueryWrapper<>();
            queryWrapper1.in(TblNodeDepartment::getNodeId, nodeId);
            List<TblNodeDepartment> tblNodeDepartments = nodeDepartmentDao.selectList(queryWrapper1);

            Map<String, TblNodeDepartment> nodeDepartmentMap = tblNodeDepartments.stream()
                    .collect(Collectors.toMap(TblNodeDepartment::getNodeId, Function.identity()));
            //检查节点有则更新无则新增
            for (FlowNodeDepartmentRequest flowNodeDepartmentRequest : nodeDepartment) {
                flowNodeDepartmentRequest.setProcessId(flowProcessDraft.getId());
                if (ObjectUtils.isEmpty(nodeDepartmentMap.get(flowNodeDepartmentRequest.getNodeId()))) {
                    insertNodeDepartment.add(flowNodeDepartmentRequest);
                } else {
                    updateNodeDepartment.add(flowNodeDepartmentRequest);
                }
            }
            if (ObjectUtils.isEmpty(insertNodeDepartment)) {
                nodeDepartmentDao.batchUpdate(updateNodeDepartment);
            } else {
                nodeDepartmentDao.insertList(insertNodeDepartment);
            }

            draftDao.update(build, wrapper);
        } else {
            LambdaQueryWrapper<TblDraft> wrapper1 = new LambdaQueryWrapper<>();
            wrapper1.eq(TblDraft::getProcessType, request.getType());
            wrapper1.eq(TblDraft::getDeleted, false);
            List<TblDraft> flowProcessDrafts = draftDao.selectList(wrapper1);
            if (ObjectUtils.isNotEmpty(flowProcessDrafts) && !Objects.equals(request.getType(), UserTodoListEnum.ACCIDENT_LINKAGE_FLOW.getKey())) {
                throw new BaseException("此类型流程已创建");
            }

            //供气停气
            wrapper1.eq(TblDraft::getOrganizationId, threadLocalUtil.getOrganizationId());
            List<TblDraft> flowProcessDrafts2 = draftDao.selectList(wrapper1);

            Set<String> restrictedProcessTypes = Set.of(
                    UserTodoListEnum.GAS_SUPPLY.getKey(),
                    UserTodoListEnum.GAS_STOP.getKey(),
                    UserTodoListEnum.GAS_SUPPLY_PLAN.getKey(),
                    UserTodoListEnum.GAS_STOP_PLAN.getKey()
            );
            if (ObjectUtils.isNotEmpty(flowProcessDrafts2) && restrictedProcessTypes.contains(request.getType())) {
                throw new BaseException("此类型流程已创建");
            }

            TblDraft build = TblDraft.builder()
                    .organizationId(threadLocalUtil.getOrganizationId())
                    .processKey(request.getKey())
                    .processName(request.getName())
                    .processType(request.getType())
                    .processStatus(1)
                    .createTime(new Date())
                    .processXml(request.getXml()).build();

            draftDao.insert(build);

            List<FlowNodeDepartmentRequest> nodeDepartment = request.getNodeDepartment();
            for (FlowNodeDepartmentRequest flowNodeDepartmentRequest : nodeDepartment) {
                flowNodeDepartmentRequest.setProcessId(build.getId());
            }
            nodeDepartmentDao.insertList(nodeDepartment);
        }
    }

    @Override
    public Boolean deployDelete(IdRequest request) {
        TblDraft tblDraft = draftDao.selectById(request.getId());
        if (StringUtils.isNotEmpty(tblDraft.getProcessKey())) {
            List<ProcessDefinition> processDefinitions = repositoryService.createProcessDefinitionQuery()
                    .processDefinitionKey(tblDraft.getProcessKey())
                    .list();
            for (ProcessDefinition processDefinition : processDefinitions) {
                repositoryService.deleteDeployment(processDefinition.getDeploymentId(), true, false);
            }
        }
        tblDraft.setDeleted(true);
        int i = draftDao.updateById(tblDraft);
        return i > 0;
    }

    @Override
    public List<FlowProcessTypeReponse> getProcessType() {
        String userType = threadLocalUtil.getOrganizationType();
        if (userType.equals(OrganizationTypeEnum.COMPANY.getKey())) {
            List<FlowProcessTypeReponse> reponses = new ArrayList<>();
            reponses.add(FlowProcessTypeReponse.builder()
                    .typeEnum(ProcessTypeEnum.GAS_SUPPLY.getKey())
                    .typeName(ProcessTypeEnum.GAS_SUPPLY.getTitle())
                    .build());
            reponses.add(FlowProcessTypeReponse.builder()
                    .typeEnum(ProcessTypeEnum.GAS_STOP.getKey())
                    .typeName(ProcessTypeEnum.GAS_STOP.getTitle())
                    .build());
            reponses.add(FlowProcessTypeReponse.builder()
                    .typeEnum(ProcessTypeEnum.GAS_SUPPLY_PLAN.getKey())
                    .typeName(ProcessTypeEnum.GAS_SUPPLY_PLAN.getTitle())
                    .build());
            reponses.add(FlowProcessTypeReponse.builder()
                    .typeEnum(ProcessTypeEnum.GAS_STOP_PLAN.getKey())
                    .typeName(ProcessTypeEnum.GAS_STOP_PLAN.getTitle())
                    .build());
            return reponses;
        } else {
            Set<String> excludedKeys = Stream.of(
                    ProcessTypeEnum.GAS_STOP.getKey(),
                    ProcessTypeEnum.GAS_SUPPLY.getKey(),
                    ProcessTypeEnum.GAS_SUPPLY_PLAN.getKey(),
                    ProcessTypeEnum.GAS_STOP_PLAN.getKey()
            ).collect(Collectors.toSet());

            return Stream.of(ProcessTypeEnum.values())
                    .filter(value -> !excludedKeys.contains(value.getKey()))  // 排除停气供气类型
                    .map(value -> FlowProcessTypeReponse.builder()
                            .typeEnum(value.getKey())
                            .typeName(value.getTitle())
                            .build())
                    .collect(Collectors.toList());
        }
    }

    @Override
    public List<FlowProcessTypeReponse> getProcessTypeEnterprise() {
        List<FlowProcessTypeReponse> reponses = new ArrayList<>();
        reponses.add(FlowProcessTypeReponse.builder()
                .typeEnum(ProcessTypeEnum.GAS_SUPPLY.getKey())
                .typeEnum(ProcessTypeEnum.GAS_SUPPLY.getTitle())
                .build());
        reponses.add(FlowProcessTypeReponse.builder()
                .typeEnum(ProcessTypeEnum.GAS_STOP.getKey())
                .typeEnum(ProcessTypeEnum.GAS_STOP.getTitle())
                .build());
        reponses.add(FlowProcessTypeReponse.builder()
                .typeEnum(ProcessTypeEnum.GAS_SUPPLY_PLAN.getKey())
                .typeEnum(ProcessTypeEnum.GAS_SUPPLY_PLAN.getTitle())
                .build());
        reponses.add(FlowProcessTypeReponse.builder()
                .typeEnum(ProcessTypeEnum.GAS_STOP_PLAN.getKey())
                .typeEnum(ProcessTypeEnum.GAS_STOP_PLAN.getTitle())
                .build());
        return reponses;
    }

    @Override
    public FlowXmlDiagramResponse getXml(IdRequest request) {
        if (request.getId() == 0) {
            return new FlowXmlDiagramResponse();
        }
        LambdaQueryWrapper<TblNodeDepartment> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(TblNodeDepartment::getProcessId, request.getId());
        List<TblNodeDepartment> flowNodeDepartments = nodeDepartmentDao.selectList(wrapper);
        List<FlowNodeDepartmentResponse> list = new ArrayList<>();
        for (TblNodeDepartment flowNodeDepartment : flowNodeDepartments) {
            FlowNodeDepartmentResponse build = FlowNodeDepartmentResponse.builder().nodeId(flowNodeDepartment.getNodeId())
                    .departmentId(flowNodeDepartment.getDepartmentId())
                    .dueDay(flowNodeDepartment.getDueDay())
                    .id(flowNodeDepartment.getId()).build();
            list.add(build);
        }

        TblDraft flowProcessDraft = draftDao.selectById(request.getId());
        return FlowXmlDiagramResponse.builder()
                .nodeDepartment(list)
                .processId(String.valueOf(flowProcessDraft.getId()))
                .processXml(flowProcessDraft.getProcessXml())
                .processName(flowProcessDraft.getProcessName())
                .processType(flowProcessDraft.getProcessType())
                .build();
    }

    @Override
    public Boolean startProcessInstanceByDraft(IdRequest request) {
        TblDraft flowProcessDraft = draftDao.selectById(request.getId());
        //部署流程
        DeploymentWithDefinitions deployWithResult = repositoryService.createDeployment()
                .name(flowProcessDraft.getProcessName()) // 部署名称
                .addString("File.bpmn", flowProcessDraft.getProcessXml()) // 添加需要部署的流程文件
                .deployWithResult();// 部署
        //获取流程定义
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .deploymentId(deployWithResult.getId())
                .singleResult();
        //草稿状态为已发布
        flowProcessDraft.setProcessKey(processDefinition.getKey());
        flowProcessDraft.setProcessStatus(1);
        draftDao.updateById(flowProcessDraft);

        return true;
    }

    @Override
    public FlowXmlDiagramResponse getProjectProcessDiagram(FlowProcessDetailRequest request) {
        //项目Id查询流程Id
        LambdaQueryWrapper<TblProjectShip> Wrapper = new LambdaQueryWrapper<>();
        Wrapper.eq(TblProjectShip::getProjectId, request.getId());
        Wrapper.eq(TblProjectShip::getProjectType, request.getType());
        TblProjectShip flowProject = projectShipDao.selectOne(Wrapper);
        if (ObjectUtils.isEmpty(flowProject)) {
            return new FlowXmlDiagramResponse();
        }
        InputStream processModelIn;

        processModelIn = repositoryService.getProcessModel(flowProject.getProcessDefinitionId());
        if (ObjectUtils.isEmpty(processModelIn)) {
            throw new BaseException("未查询到流程图");
        }
        byte[] processModel = IoUtil.readInputStream(processModelIn, "processModelBpmn20Xml");
        ProcessDefinitionDiagramDto dto = ProcessDefinitionDiagramDto.create(flowProject.getProcessDefinitionId(), new String(processModel, StandardCharsets.UTF_8));
        return FlowXmlDiagramResponse.builder()
                .processId(String.valueOf(request.getId()))
                .processXml(dto.getBpmn20Xml())
                .build();
//        HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery()
//                .processInstanceId(flowProject.getProcessInstanceId())
//                .singleResult();
//        // 获取流程启动 key
//        String startKey = null;
//        FlowXmlDiagramResponse projectProcessDiagram = new FlowXmlDiagramResponse();
//        if (historicProcessInstance != null) {
//            String processDefinitionId = historicProcessInstance.getProcessDefinitionId();
//
//            ProcessDefinition processDefinition = repositoryService.getProcessDefinition(processDefinitionId);
//            startKey = processDefinition.getKey();
//        }
//        if (startKey != null){
//            projectProcessDiagram = getXmlByKey(FlowGetXmlRequest.builder()
//                    .processId(startKey)
//                    .build());
//        }
//        return projectProcessDiagram;
    }

    @Override
    public String getTaskId(Long todoId) {
        return taskShipDao.getTaskId(todoId);
    }

    @Override
    public List<FlowEmergencyListReponse> getEmergencyProcess() {
        LambdaQueryWrapper<TblDraft> wrapper = new LambdaQueryWrapper<>();

        wrapper.eq(TblDraft::getProcessType, UserTodoListEnum.ACCIDENT_LINKAGE_FLOW.getKey());
        wrapper.eq(TblDraft::getProcessStatus, 1);
        wrapper.eq(TblDraft::getDeleted, false);
        wrapper.orderByDesc(TblDraft::getCreateTime);

        List<TblDraft> flowProcessDrafts = draftDao.selectList(wrapper);
        List<FlowEmergencyListReponse> reponse = new ArrayList<>();
        //默认添加数字重庆流程下拉
        reponse.add(FlowEmergencyListReponse.builder()
                .id(0L)
                .key(SzCqFlowEnum.SZCQFLOW_1.getKey())
                .name(SzCqFlowEnum.SZCQFLOW_1.getTitle())
                .build());
        reponse.add(FlowEmergencyListReponse.builder()
                .id(0L)
                .key(SzCqFlowEnum.SZCQFLOW_2.getKey())
                .name(SzCqFlowEnum.SZCQFLOW_2.getTitle())
                .build());
        for (TblDraft x : flowProcessDrafts) {
            reponse.add(FlowEmergencyListReponse.builder()
                    .id(x.getId())
                    .key(x.getProcessKey())
                    .name(x.getProcessName()).build());
        }
        return reponse;
    }

    @Override
    public String getGasProcess(String request) {
        LambdaQueryWrapper<TblDraft> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(TblDraft::getOrganizationId, threadLocalUtil.getOrganizationId());
        wrapper.eq(TblDraft::getProcessType, request);
        wrapper.eq(TblDraft::getProcessStatus, 1);
        wrapper.eq(TblDraft::getDeleted, false);

        TblDraft flowProcessDrafts = draftDao.selectOne(wrapper);
        if (ObjectUtils.isEmpty(flowProcessDrafts)) {
            throw new BaseException("请先进行流程设计");
        }
        return flowProcessDrafts.getProcessKey();
    }

    @Override
    public Map<String, String> getProjectIdBytodoId(List<String> taskId) {
        return taskShipDao.selectList(TblTaskShip.getWrapper().in(TblTaskShip::getTaskId, taskId))
                .stream()
                .collect(Collectors.toMap(
                        TblTaskShip::getTaskId,
                        TblTaskShip::getProjectId
                ));
    }

    @Override
    public List<Long> getTodoId(String projectId) {
        LambdaQueryWrapper<TblTaskShip> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(TblTaskShip::getProjectId, projectId);
        List<String> ids = taskShipDao.selectList(wrapper).stream().map(TblTaskShip::getTodoId).collect(Collectors.toList());
        List<Long> longList = new ArrayList<>();
        for (String id : ids) {
            longList.add(Long.valueOf(id));
        }
        return longList;
    }

    @Override
    public FlowXmlDiagramResponse getXmlByKey(FlowGetXmlRequest request) {
        TblDraft flowProcessDraft = draftDao.selectOne(TblDraft.getWrapper().eq(TblDraft::getProcessKey, request.getProcessId()));
        if (ObjectUtils.isEmpty(flowProcessDraft)) {
            return new FlowXmlDiagramResponse();
        }
        LambdaQueryWrapper<TblNodeDepartment> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(TblNodeDepartment::getProcessId, flowProcessDraft.getId());
        List<TblNodeDepartment> flowNodeDepartments = nodeDepartmentDao.selectList(wrapper);
        List<FlowNodeDepartmentResponse> list = new ArrayList<>();
        for (TblNodeDepartment flowNodeDepartment : flowNodeDepartments) {
            FlowNodeDepartmentResponse build = FlowNodeDepartmentResponse.builder().nodeId(flowNodeDepartment.getNodeId())
                    .departmentId(flowNodeDepartment.getDepartmentId())
                    .dueDay(flowNodeDepartment.getDueDay())
                    .id(flowNodeDepartment.getId()).build();
            list.add(build);
        }
        return FlowXmlDiagramResponse.builder()
                .nodeDepartment(list)
                .processId(String.valueOf(flowProcessDraft.getId()))
                .processXml(flowProcessDraft.getProcessXml())
                .processName(flowProcessDraft.getProcessName())
                .processType(flowProcessDraft.getProcessType())
                .build();
    }

}
