package com.zds.user.controller.client;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zds.biz.targetcheck.Authorization;
import com.zds.biz.vo.BaseResult;
import com.zds.biz.vo.IdRequest;
import com.zds.biz.vo.request.user.AdminDelMessageRequest;
import com.zds.biz.vo.request.user.AdminReadMessageAllRequest;
import com.zds.biz.vo.request.user.AdminReadMessageRequest;
import com.zds.biz.vo.request.user.AdminUserMessageRequest;
import com.zds.biz.vo.response.user.AdminTblMessageListPageResponse;
import com.zds.biz.vo.response.user.AdminTblMessageloadResponse;
import com.zds.biz.vo.response.user.AdminUserTodoStatisticsResponse;
import com.zds.biz.vo.response.user.ClientMessageStatisticsResponse;
import com.zds.user.service.MessageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "前台消息模块")
@RestController
@RequestMapping(value = "/client/message")
public class ClientMessageController {

    @Autowired
    private MessageService messageService;

    @Authorization
    @ApiOperation("查询当前登录用户消息列表")
    @RequestMapping(value = "/listUserMessage", method = RequestMethod.POST)
    public BaseResult<IPage<AdminTblMessageListPageResponse>> listUserMessage(@RequestBody AdminUserMessageRequest request){
        IPage<AdminTblMessageListPageResponse> messagePage = messageService.listUserMessage(request);
        return BaseResult.success(messagePage);
    }

    @ApiOperation("查询消息详情")
    @RequestMapping(value = "/loadMessage", method = RequestMethod.POST)
    public BaseResult<AdminTblMessageloadResponse> loadMessage(@RequestBody IdRequest request){
        return BaseResult.success(messageService.loadMessage(request));
    }

    @Authorization
    @ApiOperation("删除消息")
    @RequestMapping(value = "/delMessage", method = RequestMethod.POST)
    public BaseResult<String> delMessage(@RequestBody AdminDelMessageRequest request){
        return BaseResult.judgeOperate(messageService.delMessage(request));
    }

    @Authorization
    @ApiOperation("设置消息状态为已读")
    @RequestMapping(value = "/readMessage", method = RequestMethod.POST)
    public BaseResult<String> readMessage(@RequestBody AdminReadMessageRequest request){
        return BaseResult.judgeOperate(messageService.readMessage(request));
    }

    @Authorization
    @ApiOperation("全部消息标为已读")
    @RequestMapping(value = "/readMessage/all", method = RequestMethod.POST)
    public BaseResult<String> readMessageAll(@RequestBody AdminReadMessageAllRequest request){
        return BaseResult.judgeOperate(messageService.readMessageAll(request));
    }

    @ApiOperation("未读消息数量统计")
    @Authorization
    @RequestMapping(value = "/readMessage/statistics", method = RequestMethod.POST)
    public BaseResult<ClientMessageStatisticsResponse> findStatistics(@RequestBody AdminReadMessageAllRequest request) {
        return BaseResult.success(messageService.findStatistics(request));
    }
}
