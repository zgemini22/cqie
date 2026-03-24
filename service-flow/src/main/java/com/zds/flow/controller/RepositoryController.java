package com.zds.flow.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zds.biz.targetcheck.Authorization;
import com.zds.biz.vo.BaseResult;
import com.zds.biz.vo.IdRequest;
import com.zds.biz.vo.request.flow.*;
import com.zds.biz.vo.response.flow.*;
import com.zds.flow.service.FlowRepositoryService;
import com.zds.flow.service.FlowService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.camunda.bpm.engine.RepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;


@RequestMapping(value = "/flow/repository")
@RestController
@Api(tags = "仓库服务")
public class RepositoryController {
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private FlowService flowService;
    @Autowired
    private FlowRepositoryService flowRepositoryService;

    @Authorization
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ApiOperation("部署草稿列表")
    public BaseResult<IPage<FlowDeployListReponse>> deployList(@RequestBody FlowDeployListRequest request) {
        return BaseResult.success(flowService.deployList(request));
    }

//    @RequestMapping(value = "/list/enterprise", method = RequestMethod.POST)
//    @ApiOperation("部署草稿列表（企业）")
//    public BaseResult<IPage<FlowDeployListReponse>> deployListEnterprise(@RequestBody FlowDeployListRequest request) {
//        return BaseResult.success(flowService.deployListEnterprise(request));
//    }

    @Authorization
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ApiOperation("保存草稿")
    public BaseResult<Boolean> deploySave(@RequestBody FlowDeployAddRequest request) {
        return BaseResult.success(flowService.deploySave(request));
    }

    @Authorization
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ApiOperation("删除草稿")
    public BaseResult<Boolean> deployDelete(@RequestBody IdRequest request) {
        return BaseResult.success(flowService.deployDelete(request));
    }

    @Authorization
    @RequestMapping(value = "/getXml", method = RequestMethod.POST)
    @ApiOperation("获取流程模型XML文件内容")
    public BaseResult<FlowXmlDiagramResponse> getXml(@RequestBody IdRequest request) {
        return BaseResult.success(flowService.getXml(request));
    }

    @Authorization
    @RequestMapping(value = "/getXml/key", method = RequestMethod.POST)
    @ApiOperation("根据草稿key获取流程模型XML文件内容")
    public BaseResult<FlowXmlDiagramResponse> getXmlByKey(@RequestBody FlowGetXmlRequest request) {
        return BaseResult.success(flowService.getXmlByKey(request));
    }

    @ApiIgnore
    @Authorization
    @RequestMapping(value = "/deleteDeployment", method = RequestMethod.POST)
    @ApiOperation("删除已部署流程")
    public BaseResult<String> deleteDeployment(@RequestBody String deploymentId) {
        repositoryService.deleteDeployment(deploymentId);
        return BaseResult.success("成功删除");
    }

    @ApiIgnore
    @Authorization
    @RequestMapping(value = "/suspendProcessDefinitionByKey", method = RequestMethod.POST)
    @ApiOperation("根据KEY挂起流程定义")
    public BaseResult<String> suspendProcessDefinitionByKey(@RequestBody String processDefinitionKey) {
        repositoryService.suspendProcessDefinitionByKey(processDefinitionKey);
        return BaseResult.success("成功挂起");
    }

    @ApiIgnore
    @Authorization
    @RequestMapping(value = "/activateProcessDefinitionByKey", method = RequestMethod.POST)
    @ApiOperation("根据KEY激活流程定义")
    public BaseResult<String> activateProcessDefinitionByKey(@RequestBody String processDefinitionKey) {
        repositoryService.activateProcessDefinitionByKey(processDefinitionKey);
        return BaseResult.success("成功激活");
    }

    @Authorization
    @RequestMapping(value = "/getProcessType", method = RequestMethod.POST)
    @ApiOperation("所有流程类型下拉")
    public BaseResult<List<FlowProcessTypeReponse>> getProcessType() {
        return BaseResult.success(flowService.getProcessType());
    }

//    @RequestMapping(value = "/getProcessType/enterprise", method = RequestMethod.POST)
//    @ApiOperation("企业部署流程类型下拉")
//    public BaseResult<List<FlowProcessTypeReponse>> getProcessTypeEnterprise() {
//        return BaseResult.success(flowService.getProcessTypeEnterprise());
//    }

    @Authorization
    @RequestMapping(value = "/getProjectProcessDiagram", method = RequestMethod.POST)
    @ApiOperation("获取流程XML文件内容")
    public BaseResult<FlowXmlDiagramResponse> getProjectProcessDiagram(@RequestBody FlowProcessDetailRequest request) {
        FlowXmlDiagramResponse response = flowService.getProjectProcessDiagram(request);
        return BaseResult.success(response);
    }

    @Authorization
    @RequestMapping(value = "/getEmergencyProcess", method = RequestMethod.POST)
    @ApiOperation("查询已生效应急流程")
    public BaseResult<List<FlowEmergencyListReponse>> getEmergencyProcess() {
        return BaseResult.success(flowService.getEmergencyProcess());
    }

    @Authorization
    @RequestMapping(value = "/getGasProcess", method = RequestMethod.POST)
    @ApiOperation("查询停气供气企业流程")
    public BaseResult<String> getGasProcess(@RequestBody String request) {
        return BaseResult.success("",flowService.getGasProcess(request));
    }

    @Authorization
    @RequestMapping(value = "/deploy", method = RequestMethod.POST)
    @ApiOperation("保存并部署流程")
    public BaseResult<FlowDeployResponse> deploy(@RequestBody FlowDeployRequest flowDeployRequest) {
        FlowDeployResponse response = flowRepositoryService.deploy(flowDeployRequest);
        return BaseResult.success(response);
    }

    @Authorization
    @RequestMapping(value = "/getProcessDiagram", method = RequestMethod.POST)
    @ApiOperation("获取草稿流程模型XML文件内容")
    public BaseResult<FlowXmlDiagramResponse> getProcessModeLXml(@RequestBody FlowProcessDiagramRequest request) {
        FlowXmlDiagramResponse response = flowRepositoryService.getProcessModeLXml(request);
        return BaseResult.success(response);
    }
}
