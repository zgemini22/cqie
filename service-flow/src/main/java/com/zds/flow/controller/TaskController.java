package com.zds.flow.controller;


import com.zds.biz.targetcheck.Authorization;
import com.zds.biz.vo.BaseResult;
import com.zds.biz.vo.request.flow.FlowCommentRequest;
import com.zds.biz.vo.request.flow.FlowProcessDetailRequest;
import com.zds.biz.vo.request.flow.FlowProcessUnitRequest;
import com.zds.biz.vo.response.flow.FlowCommentResponse;
import com.zds.biz.vo.response.flow.FlowProcessCompletionDegreeResponse;
import com.zds.biz.vo.response.flow.FlowProcessDetailResponse;
import com.zds.biz.vo.response.flow.GasReviewTaskResponse;
import com.zds.flow.service.FlowService;
import com.zds.flow.service.FlowTaskService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.camunda.bpm.engine.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
import java.util.Map;


@RequestMapping(value = "/flow/task")
@RestController
@Api(tags = "任务服务")
public class TaskController {
    @Autowired
    private FlowService flowService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private FlowTaskService flowTaskService;

    @ApiIgnore
    @Authorization
    @RequestMapping(value = "/deleteTask", method = RequestMethod.POST)
    @ApiOperation("根据ID删除待办")
    public BaseResult<String> deleteTask(@RequestBody Long todoId) {
        String taskId = flowService.getTaskId(todoId);
        taskService.deleteTask(taskId);

        return BaseResult.success("成功删除");
    }

    @ApiIgnore
    @Authorization
    @PostMapping("/transfer")
    @ApiOperation("转办")
    public BaseResult<String> transfer(String taskId, String userId) {
        taskService.setAssignee(taskId, userId);
        taskService.setOwner(taskId, userId);
        return BaseResult.success("转办人id" + userId);
    }


    @ApiIgnore
    @Authorization
    @RequestMapping(value = "/completeAndComment", method = RequestMethod.POST)
    @ApiOperation("根据ID完成待办并添加审批意见")
    @Transactional
    public BaseResult<String> completeAndComment(@RequestBody FlowCommentRequest flowCommentRequest) {
        flowTaskService.completeAndComment(flowCommentRequest);
        return BaseResult.success("完成待办");
    }

    @ApiIgnore
    @Authorization
    @RequestMapping(value = "/getTaskComments", method = RequestMethod.POST)
    @ApiOperation("根据ID获取审批意见")
    public BaseResult<FlowCommentResponse> getTaskComments(@RequestBody String taskId) {
        return BaseResult.success(flowTaskService.getTaskComments(taskId));
    }

    @Authorization
    @ApiOperation(value = "业务id查询流程详情")
    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    public BaseResult<List<FlowProcessDetailResponse>> details(@RequestBody FlowProcessDetailRequest request) {
        return BaseResult.success(flowTaskService.details(request));
    }

    @Authorization
    @ApiOperation(value = "业务id查询流程完成度")
    @RequestMapping(value = "/completion/degree", method = RequestMethod.POST)
    public BaseResult<FlowProcessCompletionDegreeResponse> completionDegree(@RequestBody FlowProcessDetailRequest request) {
        return BaseResult.success(flowTaskService.completionDegree(request));
    }

    @ApiIgnore
    @Authorization
    @RequestMapping(value = "/getProjectId", method = RequestMethod.POST)
    @ApiOperation("后端通过待办id查询项目id")
    public Map<String , String> getProjectIdBytodoId(@RequestBody List<String> taskId) {
        return flowService.getProjectIdBytodoId(taskId);
    }

    @ApiIgnore
    @Authorization
    @RequestMapping(value = "/getTodoId", method = RequestMethod.POST)
    @ApiOperation("后端通过项目id查询待办id")
    public List<Long> getTodoId(@RequestBody String projectId) {
        return flowService.getTodoId(projectId);
    }

    @Authorization
    @RequestMapping(value = "/getProcessUnit", method = RequestMethod.POST)
    @ApiOperation("后端查询当前流程审核部门")
    public BaseResult<Map<Long,List<String>>> getProcessUnit(@RequestBody List<FlowProcessUnitRequest> request) {
        return BaseResult.success(flowTaskService.getProcessUnit(request));
    }

    @ApiIgnore
    @Authorization
    @ApiOperation("查询当前用户是否为管理员账号权限或流程指定人")
    @RequestMapping(value = "/audit/permission", method = RequestMethod.POST)
    public BaseResult<Map<Long , GasReviewTaskResponse>> auditPermission(@RequestBody List<FlowProcessDetailRequest> request){
        return BaseResult.success(flowTaskService.auditPermission(request));
    }
}
