package com.zds.flow.controller;

import com.zds.biz.targetcheck.Authorization;
import com.zds.biz.vo.BaseResult;
import com.zds.biz.vo.IdRequest;
import com.zds.biz.vo.request.flow.FlowAddSignRequest;
import com.zds.biz.vo.request.flow.FlowEnumStartProcessRequest;
import com.zds.biz.vo.request.flow.FlowStartProcessRequest;
import com.zds.biz.vo.response.flow.FlowStartResponse;
import com.zds.flow.service.FlowRuntimeService;
import com.zds.flow.service.FlowService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.camunda.bpm.engine.RepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;


@RequestMapping(value = "/flow/runtime")
@RestController
@Api(tags = "运行服务")
public class RuntimeController {
    @Autowired
    private FlowService flowService;
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private FlowRuntimeService flowRuntimeService;

    @Authorization
    @RequestMapping(value = "/startProcessInstanceByKey", method = RequestMethod.POST)
    @ApiOperation("后端调用通过流程定义KEY启动流程")
    public BaseResult<FlowStartResponse> startProcessInstanceByKey(@RequestBody FlowStartProcessRequest flowStartProcessRequest) {
        FlowStartResponse response = flowRuntimeService.startProcessInstanceByKey(flowStartProcessRequest);
        return BaseResult.success(response);
    }

    @Authorization
    @RequestMapping(value = "/startProcessInstanceByEnum", method = RequestMethod.POST)
    @ApiOperation("后端调用通过项目枚举启动流程")
    public BaseResult<Boolean> startProcessInstanceByEnum(@RequestBody FlowEnumStartProcessRequest request){
        return BaseResult.judgeOperate(flowRuntimeService.startProcessInstanceByEnum(request));
    }

    @Authorization
    @RequestMapping(value = "/startProcessInstanceByDraft", method = RequestMethod.POST)
    @ApiOperation("将流程草稿部署")
    public BaseResult<Boolean> startProcessInstanceByDraft(@RequestBody IdRequest request) {
        return BaseResult.judgeOperate(flowService.startProcessInstanceByDraft(request));
    }

    @ApiIgnore
    @Authorization
    @RequestMapping(value = "/stopProcessInstance", method = RequestMethod.POST)
    @ApiOperation("终止流程")
    public BaseResult<String> stopProcessInstance(@RequestBody String processInstanceId) {
        flowRuntimeService.stopProcessInstance(processInstanceId);
        return BaseResult.success("终止流程");
    }

    @ApiIgnore
    @Authorization
    @RequestMapping(value = "/addSign", method = RequestMethod.POST)
    @ApiOperation("节点加签")
    public BaseResult<Boolean> addSign(@RequestBody FlowAddSignRequest request) {
        flowRuntimeService.addSign(request);
        return BaseResult.success("节点加签");
    }


//    @RequestMapping(value = "/auto/end", method = RequestMethod.POST)
//    @ApiOperation("自动审批至结束")
//    public BaseResult<Boolean> autoEnd() throws InterruptedException {
//        return BaseResult.judgeOperate(flowRuntimeService.autoEnd());
//    }

}
