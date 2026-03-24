package com.zds.user.controller.client;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zds.biz.targetcheck.Authorization;
import com.zds.biz.vo.BaseResult;
import com.zds.biz.vo.IdRequest;
import com.zds.biz.vo.request.flow.FlowCommentRequest;
import com.zds.biz.vo.request.flow.FlowGetXmlRequest;
import com.zds.biz.vo.request.user.AccidentHandlingRequest;
import com.zds.biz.vo.request.user.AdminLoadAllTodoListPageListRequest;
import com.zds.biz.vo.response.flow.FlowCommentReponse;
import com.zds.biz.vo.response.flow.FlowXmlDiagramResponse;
import com.zds.biz.vo.response.user.AdminLoadAllTodoListPageListResponse;
import com.zds.biz.vo.response.user.AdminUserTodoStatisticsResponse;
import com.zds.biz.vo.response.user.ClientUserTodoDetailResponse;
import com.zds.user.service.TodoListService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "前台待办事项模块")
@RestController
@RequestMapping(value = "/client/todoList")
public class ClientTodoListController {

    @Autowired
    private TodoListService todoListService;

    @Authorization
    @ApiOperation("我的待办")
    @RequestMapping(value = "/listAllTodoList", method = RequestMethod.POST)
    public BaseResult<IPage<AdminLoadAllTodoListPageListResponse>> listAllTodoList(@RequestBody AdminLoadAllTodoListPageListRequest request) {
        return BaseResult.success(todoListService.listAllTodoList(request));
    }

    @Authorization
    @ApiOperation("当前用户待办统计")
    @RequestMapping(value = "/user/statistics", method = RequestMethod.POST)
    public BaseResult<AdminUserTodoStatisticsResponse> findUserTodoStatistics() {
        return BaseResult.success(todoListService.findUserTodoStatistics());
    }

    @Authorization
    @ApiOperation(value = "待办id查看事故处理详情",notes = "入参为待办id")
    @RequestMapping(value = "/user/accident/detail", method = RequestMethod.POST)
    public BaseResult<ClientUserTodoDetailResponse> accidentDetail(@RequestBody IdRequest request) {
        return BaseResult.success(todoListService.accidentDetail(request));
    }


    @Authorization
    @ApiOperation(value = "查询处置方案流程图",notes = "入参为处置方案id")
    @RequestMapping(value = "/accident/process", method = RequestMethod.POST)
    public BaseResult<FlowXmlDiagramResponse> accidentProcess(@RequestBody FlowGetXmlRequest request) {
        return BaseResult.success(todoListService.accidentProcess(request));
    }

    @Authorization
    @ApiOperation("事故处理")
    @RequestMapping(value = "/accident/hand", method = RequestMethod.POST)
    public BaseResult<String> accidentHand(@RequestBody AccidentHandlingRequest request){
        return BaseResult.judgeOperate(todoListService.accidentHand(request));
    }

    @Authorization
    @ApiOperation("完成流程类型待办")
    @RequestMapping(value = "/processedTodoList", method = RequestMethod.POST)
    public BaseResult<FlowCommentReponse> processedTodoList(@RequestBody FlowCommentRequest request){
        return BaseResult.success(todoListService.processedTodoList(request));
    }

}
