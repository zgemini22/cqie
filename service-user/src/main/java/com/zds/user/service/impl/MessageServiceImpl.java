package com.zds.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zds.biz.constant.BaseException;
import com.zds.biz.constant.user.MessageSourceEnum;
import com.zds.biz.util.BeanConvertUtil;
import com.zds.biz.util.ThreadLocalUtil;
import com.zds.biz.vo.IdRequest;
import com.zds.biz.vo.request.user.*;
import com.zds.biz.vo.response.user.AdminTblMessageListPageResponse;
import com.zds.biz.vo.response.user.AdminTblMessageloadResponse;
import com.zds.biz.vo.response.user.ClientMessageStatisticsResponse;
import com.zds.user.dao.TblMessageDao;
import com.zds.user.dao.TblUserDao;
import com.zds.user.manager.RoleMenuManager;
import com.zds.user.po.TblMessage;
import com.zds.user.po.TblUser;
import com.zds.user.service.MessageService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private TblMessageDao tblMessageDao;

    @Autowired
    private TblUserDao userDao;

    @Autowired
    private RoleMenuManager roleMenuManager;

    @Autowired
    private ThreadLocalUtil threadLocalUtil;

    @Override
    public IPage<AdminTblMessageListPageResponse> listUserMessage(AdminUserMessageRequest request) {
        //获取当前登录用户ID
        Long userId = threadLocalUtil.getUserId();
        //构建查询条件
        IPage<TblMessage> page = new Page<>(request.getPageNum(), request.getPageSize());
        LambdaQueryWrapper<TblMessage> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(TblMessage::getCreateTime)
                .eq(TblMessage::getDeleted, 0)
                .eq(TblMessage::getUserId, userId)
                .eq(request.getReadFlag() != null, TblMessage::getReadFlag, request.getReadFlag())
                .in(request.getMessageGroup() != null && request.getMessageGroup().size() > 0, TblMessage::getMessageGroup, request.getMessageGroup())
                .like(StringUtils.isNotEmpty(request.getMessageTitle()), TblMessage::getMessageTitle, "%" + request.getMessageTitle() + "%")
                .between(request.getStartTime() != null && request.getEndTime() != null, TblMessage::getCreateTime, request.getStartTime(), request.getEndTime());
        IPage<TblMessage> selectPage = tblMessageDao.selectPage(page, queryWrapper);
        //查询并返回
        return selectPage.convert(data -> {
            AdminTblMessageListPageResponse vo = new AdminTblMessageListPageResponse();
            BeanUtils.copyProperties(data, vo);
            return vo;
        });
    }

    @Override
    public boolean addMessage(AdminAddMessageRequest request) {
        TblUser user = userDao.selectById(request.getUserId());
        if (user == null) {
            throw new BaseException("未找到指定用户");
        }
        //转换为数据库实体类
        TblMessage po = BeanConvertUtil.convertTo(request, TblMessage::new);
        po.setOrganizationId(user.getOrganizationId());
        po.setReadFlag(false);
        int count = tblMessageDao.insert(po);
        return count == 1;
    }

    @Override
    public boolean addMessageBatch(List<AdminAddMessageRequest> request) {
        if (request != null && request.size() > 0) {
            List<Long> userIds = request.stream().map(AdminAddMessageRequest::getUserId).distinct().filter(x -> x != null && x > 0).collect(Collectors.toList());
            if (userIds.size() == 0) {
                throw new BaseException("未找到指定用户集合");
            }
            Map<Long, Long> map = userDao.selectBatchIds(request.stream().map(AdminAddMessageRequest::getUserId).collect(Collectors.toList()))
                    .stream()
                    .collect(Collectors.toMap(TblUser::getId, TblUser::getOrganizationId, (a, b) -> b));
            List<TblMessage> list = new ArrayList<>();
            for (AdminAddMessageRequest vo : request) {
                TblMessage po = BeanConvertUtil.convertTo(vo, TblMessage::new);
                po.setOrganizationId(map.getOrDefault(vo.getUserId(), 0L));
                po.setReadFlag(false);
                list.add(po);
            }
            if (list.size() > 0) {
                tblMessageDao.insertList(list);
            }
        }
        return true;
    }

    @Override
    public boolean addMessageToOrg(AdminOrgAddMessageRequest request) {
        List<TblMessage> list = new ArrayList<>();
        List<Long> userIds = roleMenuManager.getUserIdsByMessageSource(request.getOrganizationId(), MessageSourceEnum.query(request.getMessageSource()));
        for (Long userId : userIds) {
            TblMessage po = BeanConvertUtil.convertTo(request, TblMessage::new);
            po.setUserId(userId);
            po.setReadFlag(false);
            list.add(po);
        }
        if (list.size() > 0) {
            tblMessageDao.insertList(list);
        }
        return true;
    }

    @Override
    public boolean addMessageListToOrg(List<AdminOrgAddMessageRequest> megList) {
        if (megList == null || megList.size() == 0) {
            throw new BaseException("参数错误");
        }
        List<TblMessage> list = new ArrayList<>();
        List<Long> userIds = roleMenuManager.getUserIdsByMessageSource(megList.get(0).getOrganizationId(), MessageSourceEnum.query(megList.get(0).getMessageSource()));
        for (Long userId : userIds) {
            for (AdminOrgAddMessageRequest request : megList) {
                TblMessage po = BeanConvertUtil.convertTo(request, TblMessage::new);
                po.setUserId(userId);
                po.setReadFlag(false);
                list.add(po);
            }
        }
        if (list.size() > 0) {
            tblMessageDao.insertList(list);
        }
        return true;
    }

    @Override
    public boolean delMessage(AdminDelMessageRequest request) {
        tblMessageDao.update(TblMessage.builder().deleted(true).build(),
                TblMessage.getWrapper()
                        .eq(TblMessage::getId, request.getId())
                        .eq(TblMessage::getDeleted, false)
                        .eq(TblMessage::getUserId, threadLocalUtil.getUserId()));
        return true;
    }

    @Override
    public boolean modifyMessage(AdminModifyMessageRequest request) {
        //入参检查
        request.toRequestCheck();
        //通过ID查询消息
        TblMessage tblMessage = tblMessageDao.selectById(request.getId());
        //判断消息是否存在，是否删除
        if (tblMessage == null || tblMessage.getDeleted()) {
            throw new BaseException("消息不存在或已删除");
        }
        //转换类型
        TblMessage tmg = BeanConvertUtil.convertTo(request, TblMessage::new);
        tmg.setUpdateTime(Calendar.getInstance().getTime());
        tmg.setUpdateId(threadLocalUtil.getUserId());
        //修改后消息设置为未读
        tmg.setReadFlag(false);
        int count = tblMessageDao.updateById(tmg);
        return count == 1;
    }

    @Override
    public AdminTblMessageloadResponse loadMessage(IdRequest request) {
        //通过ID查询消息
        TblMessage tblMessage = tblMessageDao.selectById(request.getId());
        //判断消息是否存在，是否删除
        if (tblMessage == null || tblMessage.getDeleted()) {
            throw new BaseException("消息不存在或已删除");
        }
        return BeanConvertUtil.convertTo(tblMessage, AdminTblMessageloadResponse::new);
    }

    @Override
    public boolean readMessage(AdminReadMessageRequest request) {
        //通过ID查询消息
        TblMessage tblMessage = tblMessageDao.selectById(request.getId());
        //判断消息是否存在，是否删除
        if (tblMessage == null || tblMessage.getDeleted()) {
            throw new BaseException("消息不存在或已删除");
        }
        //已读消息
        tblMessage.setReadFlag(true);
        int count = tblMessageDao.updateById(tblMessage);
        return count == 1;
    }

    @Override
    public boolean readMessageAll(AdminReadMessageAllRequest request) {
        tblMessageDao.update(TblMessage.builder().readFlag(true).build(),
                TblMessage.getWrapper()
                        .eq(TblMessage::getDeleted, false)
                        .eq(TblMessage::getReadFlag, false)
                        .in(request.getMessageGroup() != null && request.getMessageGroup().size() > 0, TblMessage::getMessageGroup, request.getMessageGroup())
                        .eq(TblMessage::getUserId, threadLocalUtil.getUserId()));
        return true;
    }

    @Override
    public ClientMessageStatisticsResponse findStatistics(AdminReadMessageAllRequest request) {
        int readTotal = Math.toIntExact(tblMessageDao.selectCount(TblMessage.getWrapper()
                .eq(TblMessage::getDeleted, false)
                .eq(TblMessage::getReadFlag, false)
                .eq(TblMessage::getUserId, threadLocalUtil.getUserId())
                .in(request.getMessageGroup() != null && request.getMessageGroup().size() > 0, TblMessage::getMessageGroup, request.getMessageGroup())));
        return ClientMessageStatisticsResponse.builder().readTotal(readTotal).build();
    }
}
