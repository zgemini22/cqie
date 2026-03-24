package com.zds.user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zds.biz.vo.IdRequest;
import com.zds.biz.vo.request.user.*;
import com.zds.biz.vo.response.user.AdminTblMessageListPageResponse;
import com.zds.biz.vo.response.user.AdminTblMessageloadResponse;
import com.zds.biz.vo.response.user.ClientMessageStatisticsResponse;

import java.util.List;

/**
 * 消息服务
 */
public interface MessageService {

    /**
     * 查询当前登录用户消息列表
     */
    IPage<AdminTblMessageListPageResponse> listUserMessage(AdminUserMessageRequest request);

    /**
     * 新增消息
     */
    boolean addMessage(AdminAddMessageRequest request);

    /**
     * 批量新增用户消息
     */
    boolean addMessageBatch(List<AdminAddMessageRequest> request);

    /**
     * 指定单位新增消息
     */
    boolean addMessageToOrg(AdminOrgAddMessageRequest request);

    /**
     * 指定单位新增消息
     */
    boolean addMessageListToOrg(List<AdminOrgAddMessageRequest> megList);

    /**
     * 删除消息
     */
    boolean delMessage(AdminDelMessageRequest request);

    /**
     * 修改消息
     */
    boolean modifyMessage(AdminModifyMessageRequest request);

    /**
     * 查询消息详情
     */
    AdminTblMessageloadResponse loadMessage(IdRequest request);

    /**
     * 设置消息状态为已读
     */
    boolean readMessage(AdminReadMessageRequest request);

    /**
     * 全部消息标为已读
     */
    boolean readMessageAll(AdminReadMessageAllRequest request);

    /**
     * 未读消息统计
     */
    ClientMessageStatisticsResponse findStatistics(AdminReadMessageAllRequest request);

}
