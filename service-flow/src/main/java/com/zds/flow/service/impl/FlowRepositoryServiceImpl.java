package com.zds.flow.service.impl;

import com.zds.biz.constant.BaseException;
import com.zds.biz.constant.user.UserTodoListEnum;
import com.zds.biz.vo.IdRequest;
import com.zds.biz.vo.request.flow.FlowDeployRequest;
import com.zds.biz.vo.request.flow.FlowProcessDiagramRequest;
import com.zds.biz.vo.response.flow.FlowDeployResponse;
import com.zds.biz.vo.response.flow.FlowXmlDiagramResponse;
import com.zds.flow.dao.TblDraftDao;
import com.zds.flow.dao.TblProjectShipDao;
import com.zds.flow.service.FlowRepositoryService;
import com.zds.flow.service.FlowService;
import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.IdentityService;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.impl.persistence.entity.DeploymentEntity;
import org.camunda.bpm.engine.impl.util.IoUtil;
import org.camunda.bpm.engine.repository.DeploymentWithDefinitions;
import org.camunda.bpm.engine.repository.ProcessDefinition;
import org.camunda.bpm.engine.rest.dto.repository.ProcessDefinitionDiagramDto;
import org.camunda.bpm.model.bpmn.instance.FlowNode;
import org.camunda.bpm.model.bpmn.instance.SequenceFlow;
import org.camunda.bpm.model.bpmn.instance.UserTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class FlowRepositoryServiceImpl implements FlowRepositoryService {

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private FlowService flowService;

    

    @Autowired
    private TblDraftDao draftDao;

    @Autowired
    private TblProjectShipDao projectShipDao;

    @Autowired
    private HistoryService historyService;

    @Autowired
    private IdentityService identityService;

    @Override
    public FlowDeployResponse deploy(FlowDeployRequest flowDeployRequest) {
        if (flowDeployRequest.getType().equals(UserTodoListEnum.ACCIDENT_LINKAGE_FLOW.getKey()) ||
                //供气停气
                flowDeployRequest.getType().equals(UserTodoListEnum.GAS_SUPPLY.getKey()) ||
                flowDeployRequest.getType().equals(UserTodoListEnum.GAS_STOP.getKey()) ||
                flowDeployRequest.getType().equals(UserTodoListEnum.GAS_SUPPLY_PLAN.getKey()) ||
                flowDeployRequest.getType().equals(UserTodoListEnum.GAS_STOP_PLAN.getKey())) {
            String xml = updateProcessKey(flowDeployRequest.getXml(), flowDeployRequest.getType());
            xml = addListiner(xml);
            DeploymentWithDefinitions deployWithResult = new DeploymentEntity();
            try {
                deployWithResult = repositoryService.createDeployment()
                        .name(flowDeployRequest.getName()) // 部署名称
                        .addString("File.bpmn", xml) // 添加需要部署的流程文件
                        .deployWithResult(); // 部署
            } catch (Exception e) {
                throw new BaseException("流程部署失败，请检查流程图", e);
            }

            //获取流程定义
            ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                    .deploymentId(deployWithResult.getId())
                    .singleResult();

            flowDeployRequest.setXml(xml);
            flowDeployRequest.setKey(processDefinition.getKey());
            flowService.deployAndSave(flowDeployRequest);

            FlowDeployResponse response = new FlowDeployResponse();
            response.setProcessDefinitionId(processDefinition.getId());
            response.setProcessDefinitionKey(processDefinition.getKey());
            return response;
        } else {
            String xml = addListiner(flowDeployRequest.getXml());
            DeploymentWithDefinitions deployWithResult = new DeploymentEntity();
            try {
                deployWithResult = repositoryService.createDeployment()
                        .name(flowDeployRequest.getName()) // 部署名称
                        .addString("File.bpmn", xml) // 添加需要部署的流程文件
                        .deployWithResult(); // 部署
            } catch (Exception e) {
                throw new BaseException("流程部署失败，请检查流程图", e);
            }

            //获取流程定义
            ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                    .deploymentId(deployWithResult.getId())
                    .singleResult();

            flowDeployRequest.setXml(xml);
            flowDeployRequest.setKey(processDefinition.getKey());
            flowService.deployAndSave(flowDeployRequest);

            FlowDeployResponse response = new FlowDeployResponse();
            response.setProcessDefinitionId(processDefinition.getId());
            response.setProcessDefinitionKey(processDefinition.getKey());
            return response;
        }
    }

    @Override
    public FlowXmlDiagramResponse getProcessModeLXml(FlowProcessDiagramRequest request) {
        String processDefinitionId = repositoryService.createProcessDefinitionQuery()
                .processDefinitionKey(request.getProcessKey())
                .latestVersion() // 获取最新版本的流程定义
                .singleResult().getId();

        InputStream processModelIn;
        processModelIn = repositoryService.getProcessModel(processDefinitionId);
        byte[] processModel = IoUtil.readInputStream(processModelIn, "processModelBpmn20Xml");
        ProcessDefinitionDiagramDto dto = ProcessDefinitionDiagramDto.create(processDefinitionId, new String(processModel, StandardCharsets.UTF_8));

        IdRequest request1 = new IdRequest();
        request1.setId(request.getProcessId());
        FlowXmlDiagramResponse response = flowService.getXml(request1);
        response.setProcessXml(dto.getBpmn20Xml());
        return response;
    }

    public static String extractAssignee(String xml) {
        // 匹配 <userTask> 标签及其 camunda:assignee 属性
        String regex = "<userTask[^>]*?camunda:assignee=\"([^\"]*?)\"";
        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(regex);
        java.util.regex.Matcher matcher = pattern.matcher(xml);

        // 查找第一个匹配项
        if (matcher.find()) {
            return matcher.group(1); // 返回括号内匹配内容
        }
        return null; // 未找到时返回 null
    }

    // 递归查找第一个 UserTask
    private UserTask findFirstUserTask(Collection<FlowNode> nodes) {
        for (FlowNode node : nodes) {
            if (node instanceof UserTask) {
                return (UserTask) node;
            }
            // 递归检查当前节点的后续节点
            Collection<FlowNode> nextNodes = node.getOutgoing().stream()
                    .map(SequenceFlow::getTarget)
                    .collect(Collectors.toList());
            UserTask userTask = findFirstUserTask(nextNodes);
            if (userTask != null) {
                return userTask;
            }
        }
        return null;
    }

    //替换流程key
    private static String updateProcessKey(String bpmnXml, String oldProcessKey) {
        oldProcessKey = oldProcessKey.replace("\\", "\\\\").replace(".", "\\.");
        String pattern = "process\\s+id\\s*=\\s*\"" + oldProcessKey + "\"";
        String newProcessKey = oldProcessKey + generateRandomSuffix(4);
        return bpmnXml.replaceAll(pattern, "process id=\"" + newProcessKey + "\"");
    }

    //随机生成流程key后缀
    private static String generateRandomSuffix(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder randomSuffix = new StringBuilder();

        Random random = new Random();
        for (int i = 0; i < length; i++) {
            char randomChar = characters.charAt(random.nextInt(characters.length()));
            randomSuffix.append(randomChar);
        }

        return randomSuffix.toString();
    }

    //添加xml监听器
    private static String addListiner(String xml) {
        StringBuffer xmlBf = new StringBuffer(xml);
        int i = xmlBf.indexOf("<userTask id=");
        while (true) {
            if (i == -1) {
                break;
            } else {
                int start = xmlBf.indexOf(">", i) + 1;
                String listenerToAdd = "\n" +
                        "      <extensionElements>\n" +
                        "        <camunda:taskListener class=\"com.zds.flow.listiner.TaskStartListener\" event=\"assignment\" />\n" +
                        "        <camunda:taskListener class=\"com.zds.flow.listiner.TaskDeleteListener\" event=\"delete\" />\n" +
                        "      </extensionElements>";
                if (xmlBf.indexOf(listenerToAdd, start) == -1) {
                    xmlBf.insert(start, listenerToAdd);
                }
                i = xmlBf.indexOf("<userTask id=", start);
            }
        }
        int k = xmlBf.indexOf("<endEvent id=");
        while (true) {
            if (k == -1) {
                break;
            } else {
                int start = xmlBf.indexOf(">", k) + 1;
                String listenerToAdd = "\n" +
                        "      <extensionElements>\n" +
                        "        <camunda:executionListener class=\"com.zds.flow.listiner.EndProcessListener\" event=\"end\" />\n" +
                        "      </extensionElements>";

                if (xmlBf.indexOf(listenerToAdd, start) == -1) {
                    xmlBf.insert(start, listenerToAdd);
                }
                k = xmlBf.indexOf("<endEvent id=", start);
            }
        }
        return xmlBf.toString();
    }
}
