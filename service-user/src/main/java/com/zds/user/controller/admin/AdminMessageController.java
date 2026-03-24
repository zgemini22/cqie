package com.zds.user.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zds.biz.targetcheck.Authorization;
import com.zds.biz.vo.BaseResult;
import com.zds.biz.vo.IdRequest;
import com.zds.biz.vo.request.user.*;
import com.zds.biz.vo.response.user.AdminTblMessageListPageResponse;
import com.zds.biz.vo.response.user.AdminTblMessageloadResponse;
import com.zds.user.service.MessageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@Api(tags = "后台消息模块")
@RestController
@RequestMapping(value = "/admin/message")
public class AdminMessageController {

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
    @ApiOperation("新增消息")
    @RequestMapping(value = "/addMessage",method = RequestMethod.POST)
    public BaseResult<String> addMessage(@RequestBody AdminAddMessageRequest request){
        return BaseResult.judgeOperate(messageService.addMessage(request));
    }

    @Authorization
    @ApiOperation("批量新增用户消息")
    @RequestMapping(value = "/addMessage/batch",method = RequestMethod.POST)
    public BaseResult<String> addMessageBatch(@RequestBody List<AdminAddMessageRequest> request){
        return BaseResult.judgeOperate(messageService.addMessageBatch(request));
    }

    @ApiIgnore
    @Authorization
    @ApiOperation("指定单位新增消息")
    @RequestMapping(value = "/org/addMessage",method = RequestMethod.POST)
    public BaseResult<String> addMessageToOrg(@RequestBody AdminOrgAddMessageRequest request){
        return BaseResult.judgeOperate(messageService.addMessageToOrg(request));
    }

    @ApiIgnore
    @Authorization
    @ApiOperation("指定单位批量新增消息")
    @RequestMapping(value = "/org/addMessage/batch",method = RequestMethod.POST)
    public BaseResult<String> addMessageListToOrg(@RequestBody List<AdminOrgAddMessageRequest> request){
        return BaseResult.judgeOperate(messageService.addMessageListToOrg(request));
    }

    @Authorization
    @ApiOperation("删除消息")
    @RequestMapping(value = "/delMessage", method = RequestMethod.POST)
    public BaseResult<String> delMessage(@RequestBody AdminDelMessageRequest request){
        return BaseResult.judgeOperate(messageService.delMessage(request));
    }

    @Authorization
    @ApiOperation("修改消息")
    @RequestMapping(value = "/modifyMessage",method = RequestMethod.POST)
    public BaseResult<String> modifyMessage(@RequestBody AdminModifyMessageRequest request){
        return BaseResult.judgeOperate(messageService.modifyMessage(request));
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
}
