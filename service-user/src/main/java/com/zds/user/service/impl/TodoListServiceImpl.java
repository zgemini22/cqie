package com.zds.user.service.impl;

import cn.hutool.core.lang.Snowflake;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zds.biz.constant.BaseException;
import com.zds.biz.constant.user.OrganizationTypeEnum;
import com.zds.biz.constant.user.UserTodoListEnum;
import com.zds.biz.util.BeanConvertUtil;
import com.zds.biz.util.CalendarUtil;
import com.zds.biz.util.RedisLuaUtil;
import com.zds.biz.util.ThreadLocalUtil;
import com.zds.biz.vo.IdRequest;
import com.zds.biz.vo.request.flow.FlowCommentAnnexRequest;
import com.zds.biz.vo.request.flow.FlowCommentRequest;
import com.zds.biz.vo.request.flow.FlowGetXmlRequest;
import com.zds.biz.vo.request.user.*;
import com.zds.biz.vo.response.flow.FlowCommentReponse;
import com.zds.biz.vo.response.flow.FlowEmergencyListReponse;
import com.zds.biz.vo.response.flow.FlowFormTypeReponse;
import com.zds.biz.vo.response.flow.FlowXmlDiagramResponse;
import com.zds.biz.vo.response.user.*;
import com.zds.user.dao.TblOrganizationDao;
import com.zds.user.dao.TblTodoFileDao;
import com.zds.user.dao.TblTodoListDao;
import com.zds.user.dao.TblUserDao;
import com.zds.user.feign.FlowService;
import com.zds.user.manager.OrgManager;
import com.zds.user.manager.RoleMenuManager;
import com.zds.user.po.TblOrganization;
import com.zds.user.po.TblTodoFile;
import com.zds.user.po.TblTodoList;
import com.zds.user.po.TblUser;
import com.zds.user.service.CalendarService;
import com.zds.user.service.FormService;
import com.zds.user.service.TodoListService;
import com.zds.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TodoListServiceImpl implements TodoListService {

    @Autowired
    private TblTodoListDao tblTodoListDao;

    @Autowired
    private ThreadLocalUtil threadLocalUtil;

    @Autowired
    private FlowService flowService;

    @Autowired
    private TblUserDao tblUserDao;

    @Autowired
    private TblTodoFileDao tblTodoFileDao;

    @Autowired
    private TblOrganizationDao organizationDao;

    @Autowired
    private FormService formService;

    @Autowired
    private CalendarService calendarService;

    @Autowired
    private OrgManager orgManager;

    @Autowired
    private RedisLuaUtil redisLuaUtil;

    @Autowired
    private UserService userManager;

    @Autowired
    private RoleMenuManager roleMenuManager;


    private Snowflake sf = new Snowflake();

    private final String LOCK_REDIS_KEY_PREFIX = "TODO_LOCK:";

    private String getRealKey(String type, String dataId) {
        return LOCK_REDIS_KEY_PREFIX + type + "_" + dataId;
    }

    /**
     * 新增待办事项
     *
     * @param request
     * @return
     */
    @Override
    public Long addToList(AdminAddTodoListRequest request) {
        //检查入参
        request.toRequestCheck();
        //检查待办是否已发起
        LambdaQueryWrapper<TblTodoList> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TblTodoList::getDataId, request.getDataId());
        TblTodoList check = tblTodoListDao.selectOne(queryWrapper);
        if (ObjectUtils.isEmpty(check)) {
            //加锁
            String lockKey = getRealKey(request.getType(), request.getDataId());
            boolean lock = redisLuaUtil.lock(lockKey, lockKey, "2000", 10);
            if (!lock) {
                throw new BaseException("当前系统繁忙,稍后再试");
            }
            try {
                //转换为数据库实体类
                TblTodoList tblTodoList = BeanConvertUtil.convertTo(request, TblTodoList::new);
                //补充数据并插入数据库
                tblTodoList.setStartTime(Calendar.getInstance().getTime());
                tblTodoList.setCreateTime(Calendar.getInstance().getTime());
                tblTodoList.setStatus(1);
                tblTodoList.setDueTime(request.getDueTime());
                tblTodoList.setTodoCode(sf.nextIdStr());
                tblTodoList.setInitiatorId(request.getInitiatorId());
                tblTodoList.setAssigneeId(request.getAssigneeId());
                tblTodoList.setCreateId(threadLocalUtil.getUserId());
                tblTodoList.setAssigneeUnit(request.getOrganizationId());
                tblTodoList.setFormUrl(formService.getOutsideForm(request.getType()));
                tblTodoList.setProcess(1);
                tblTodoListDao.insert(tblTodoList);
                return tblTodoList.getId();
            } finally {
                //释放锁
                redisLuaUtil.unlock(lockKey, lockKey);
            }
        }
        return check.getId();
    }

    @Override
    public Map<Long, String> addToListBatch(AdminAddTodoListBatchRequest request) {
        //检查待办是否已发起
        List<String> ids = request.getList().stream().map(AdminAddTodoListRequest::getDataId).collect(Collectors.toList());
        LambdaQueryWrapper<TblTodoList> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(TblTodoList::getDataId, ids);
        List<TblTodoList> check = tblTodoListDao.selectList(queryWrapper);
        Map<Long, String> re = new HashMap<>();
        if (ObjectUtils.isEmpty(check)) {
            List<TblTodoList> insertList = new ArrayList<>();
            for (AdminAddTodoListRequest adminAddTodoListRequest : request.getList()) {
                //转换为数据库实体类
                TblTodoList tblTodoList = new TblTodoList();
                //补充数据并插入数据库
                tblTodoList.setName(adminAddTodoListRequest.getName());
                tblTodoList.setDataId(adminAddTodoListRequest.getDataId());
                tblTodoList.setType(adminAddTodoListRequest.getType());
                tblTodoList.setStartTime(Calendar.getInstance().getTime());
                tblTodoList.setCreateTime(Calendar.getInstance().getTime());
                tblTodoList.setStatus(1);
                tblTodoList.setDueTime(adminAddTodoListRequest.getDueTime());
                tblTodoList.setTodoCode(sf.nextIdStr());
                tblTodoList.setInitiatorId(adminAddTodoListRequest.getInitiatorId());
                tblTodoList.setAssigneeId(adminAddTodoListRequest.getAssigneeId());
                tblTodoList.setAssigneeUnit(adminAddTodoListRequest.getOrganizationId());
                tblTodoList.setFormUrl(formService.getOutsideForm(adminAddTodoListRequest.getType()));
                tblTodoList.setProcess(1);
                insertList.add(tblTodoList);
            }

            tblTodoListDao.insertList(insertList);
            re = insertList.stream()
                    .collect(Collectors.toMap(
                            TblTodoList::getId,
                            TblTodoList::getDataId
                    ));
        }
        return re;
    }

    /**
     * 删除待办事项
     *
     * @param request
     * @return
     */
    @Override
    public boolean delToList(AdminDelTodoListRequest request) {
        //通过ID查询待办事项
        TblTodoList tblTodoList = tblTodoListDao.selectById(request.getId());
        //判断待办事项是否存在，是否删除
        if (tblTodoList == null || tblTodoList.getDeleted()) {
            throw new BaseException("消息不存在或已删除");
        }
        //删除待办事项（物理删除）
        tblTodoList.setDeleted(true);
        int count = tblTodoListDao.updateById(tblTodoList);
        //流程待办同步删除
        flowService.deleteTask(request.getId());
        return count == 1;
    }

    @Override
    public boolean delToListByCode(AdminDelTodoListByCodeRequest request) {
        tblTodoListDao.update(TblTodoList.builder().deleted(true).build(), TblTodoList.getWrapper()
                .eq(TblTodoList::getTodoCode, request.getTodoCode())
                .eq(TblTodoList::getType, request.getType()));
        return true;
    }

    @Override
    public boolean userNoProcessedTodoList(NoProcessTodoComentRequest request) {
        LambdaQueryWrapper<TblTodoList> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TblTodoList::getType, request.getType());
        queryWrapper.eq(TblTodoList::getDataId, request.getDataId());
        int i = tblTodoListDao.update(TblTodoList.builder()
                .status(2)
                .endTime(Calendar.getInstance().getTime())
                .build(), queryWrapper);
        return i > 0;
    }

    @Override
    public boolean unitNoProcessedTodoList(NoProcessTodoComentRequest request) {
        LambdaQueryWrapper<TblTodoList> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TblTodoList::getType, request.getType())
                .eq(TblTodoList::getAssigneeUnit, request.getId())
                .eq(TblTodoList::getDataId, request.getDataId());
        tblTodoListDao.update(TblTodoList.builder()
                .status(2)
                .endTime(Calendar.getInstance().getTime())
                .build(), queryWrapper);
        return true;
    }


    /**
     * 修改待办事项
     *
     * @param request
     * @return
     */
    @Override
    public boolean modifyToList(AdminModifyTodoListRequest request) {
        //入参检查
        request.toRequestCheck();
        //通过ID查询待办事项
        TblTodoList tblTodoList = tblTodoListDao.selectById(request.getId());
        //判断待办事项是否存在，是否删除
        if (tblTodoList == null || tblTodoList.getDeleted()) {
            throw new BaseException("事项不存在或已删除");
        }
        //转换类型
        TblTodoList ttl = BeanConvertUtil.convertTo(request, TblTodoList::new);
        ttl.setUpdateTime(Calendar.getInstance().getTime());
        ttl.setUpdateId(threadLocalUtil.getUserId());
        //修改后待办事项设置为待办
        ttl.setStatus(1);
        int count = tblTodoListDao.updateById(ttl);
        return count == 1;
    }

    /**
     * 流程审批
     *
     * @param request
     * @return
     */
    @Override
    @Transactional
    public FlowCommentReponse processedTodoList(FlowCommentRequest request) {
        //通过ID查询待办事项
        TblTodoList tblTodoList = tblTodoListDao.selectById(request.getTodoId());
        //判断待办事项是否存在，是否删除
        if (tblTodoList == null || tblTodoList.getDeleted()) {
            throw new BaseException("待办事项不存在或已删除");
        }
        //流程审批
        WorkCalendarResponse workCalendarResponse = calendarService.checkWork(WorkCalendarRequest.builder()
                .workDay(tblTodoList.getDueTime())
                .endTime(new Date())
                .startTime(tblTodoList.getStartTime()).build());
        //修改待办事项为已处理（1:未处理,2:已处理）
        tblTodoList.setStatus(2);
        tblTodoList.setWorkTime(workCalendarResponse.getWorkTime());
        tblTodoList.setTimeOut(workCalendarResponse.getWorkStatus());
//        tblTodoList.setWorkTime(1);
//        tblTodoList.setTimeOut(1);
        tblTodoList.setEndTime(new Date());
        tblTodoList.setPass(request.getPass());
        tblTodoList.setCommentText(request.getCommentText());
        tblTodoListDao.updateById(tblTodoList);
        //保存附件
        if (ObjectUtils.isNotEmpty(request.getCommentAnnex())) {
            List<FlowCommentAnnexRequest> commentAnnex = request.getCommentAnnex();
            List<TblTodoFile> list = new ArrayList<>();
            if (ObjectUtils.isNotEmpty(commentAnnex)) {
                for (FlowCommentAnnexRequest annex : commentAnnex) {
                    list.add(TblTodoFile.builder()
                            .realFileName(annex.getRealFileName())
                            .fileName(annex.getFileName())
                            .fileType(1)
                            .todoId(request.getTodoId()).build());
                }
            }
            tblTodoFileDao.insertList(list);
        }

        //保存图片
        if (ObjectUtils.isNotEmpty(request.getCommentPicture())) {
            List<FlowCommentAnnexRequest> commentPicture = request.getCommentPicture();
            List<TblTodoFile> list2 = new ArrayList<>();
            if (ObjectUtils.isNotEmpty(commentPicture)) {
                for (FlowCommentAnnexRequest picture : commentPicture) {
                    list2.add(TblTodoFile.builder()
                            .realFileName(picture.getRealFileName())
                            .fileName(picture.getFileName())
                            .fileType(2)
                            .todoId(request.getTodoId()).build());
                }
            }
            tblTodoFileDao.insertList(list2);
        }
        String data = flowService.completeAndComment(request).getData();
        return FlowCommentReponse.builder()
                .commentText(request.getCommentText())
                .todoId(request.getTodoId())
                .pass(request.getPass())
                .commentAnnex(request.getCommentAnnex())
                .build();
    }

    /**
     * 条件分页查询待办事项
     *
     * @param request
     * @return
     */
    @Override
    public IPage<AdminLoadAllTodoListPageListResponse> listAllTodoList(AdminLoadAllTodoListPageListRequest request) {
        //构建查询条件
        LambdaQueryWrapper<TblTodoList> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(TblTodoList::getStartTime);
        if (ObjectUtils.isNotEmpty(request.getStatus())) {
            queryWrapper.eq(TblTodoList::getStatus, request.getStatus());
        }
        if (StringUtils.isNotEmpty(request.getName())) {
            queryWrapper.like(TblTodoList::getName, request.getName());
        }
        if (StringUtils.isNotEmpty(request.getType())) {
            queryWrapper.eq(TblTodoList::getType, request.getType());
        }
        queryWrapper.eq(TblTodoList::getAssigneeId, threadLocalUtil.getUserId());
        queryWrapper.eq(TblTodoList::getDeleted, 0);
        queryWrapper.orderByDesc(TblTodoList::getCreateTime);

        Page<TblTodoList> page = new Page<>(request.getPageNum(), request.getPageSize());
        Page<TblTodoList> page1 = tblTodoListDao.selectPage(page, queryWrapper);

        List<String> ids = page1.getRecords().stream().map(TblTodoList::getDataId).collect(Collectors.toList());
        Map<String, String> projectIdBytodoIdMap = flowService.getProjectIdBytodoId(ids);

        List<String> requiredTypes = List.of(
                UserTodoListEnum.TRANS_STATION_SELF_HIDDEN_DANGER_FLOW.getKey(),
                UserTodoListEnum.TRANS_STATION_GOV_HIDDEN_DANGER_FLOW.getKey(),
                UserTodoListEnum.PROJECT_BUILD_SELF_HIDDEN_DANGER_FLOW.getKey(),
                UserTodoListEnum.PROJECT_BUILD_GOV_HIDDEN_DANGER_FLOW.getKey(),
                UserTodoListEnum.THIRD_PARTY_PREVENTION_HIDDEN_DANGER_FLOW.getKey()
        );
        return page1.convert(x -> {
            TblUser tblUser = tblUserDao.selectById(x.getInitiatorId());
            String projectId;
            if (x.getProcess() == 1) {
                projectId = projectIdBytodoIdMap.getOrDefault(x.getDataId(), "");
            } else {
                projectId = x.getDataId();
            }
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(x.getStartTime());
            calendar.add(Calendar.DAY_OF_MONTH, x.getDueTime());
            return AdminLoadAllTodoListPageListResponse.builder()
                    .name(x.getName())
                    .initiatorId(x.getInitiatorId())
                    .formUrl(x.getFormUrl())
                    .startTime(x.getStartTime())
                    .dueTime(calendar.getTime())
                    .process(x.getProcess())
                    .type(UserTodoListEnum.query(x.getType()).getTitle())
                    .typeEnum(x.getType())
                    .projectId(projectId)
                    .initiatorName(tblUser.getName())
                    .status(x.getStatus())
                    .assigneeUnit(x.getAssigneeUnit())
                    .assigneeId(x.getAssigneeId())
                    .id(x.getId()).build();
        });
    }

    @Override
    public String addNoProcesList(AdminAddNoProcessListRequest request) {
        //检查入参
        request.toRequestCheck();
        //待办事项编码
        String code = sf.nextIdStr();
        //转换为数据库实体类
        TblTodoList tblTodoList = BeanConvertUtil.convertTo(request, TblTodoList::new);
        //补充数据并插入数据库
        UserDetailResponse data = userManager.findUserDetail(Long.valueOf(request.getAssigneeId()));

        tblTodoList.setStartTime(Calendar.getInstance().getTime());
        tblTodoList.setCreateTime(Calendar.getInstance().getTime());
        tblTodoList.setStatus(1);
        tblTodoList.setType(request.getType());
        tblTodoList.setName(request.getName());
        tblTodoList.setOrganizationId(threadLocalUtil.getOrganizationId());
        tblTodoList.setCreateId(threadLocalUtil.getUserId());
        tblTodoList.setInitiatorId(request.getInitiatorId());
        tblTodoList.setAssigneeId(request.getAssigneeId());
        tblTodoList.setAssigneeUnit(data.getOrganizationId().toString());
        tblTodoList.setDueTime(request.getDueTime() != null ? request.getDueTime() : 2);
        tblTodoList.setTodoCode(code);
        tblTodoList.setFormUrl(formService.getOutsideForm(request.getType()));
        tblTodoList.setProcess(2);
        tblTodoListDao.insert(tblTodoList);
        return code;
    }

    @Override
    public String addNoUserNoProcessTodo(AdminAddNoUserNoProcessTodoRequest request) {
        //检查入参
        request.toRequestCheck();
        //待办事项编码
        String code = sf.nextIdStr();
        List<Long> userIds = roleMenuManager.getUserIdsByTodo(request.getAssigneeOrgId(), UserTodoListEnum.query(request.getType()));
        List<TblTodoList> list = new ArrayList<>();
        String formUrl = formService.getOutsideForm(request.getType());
        for (Long userId : userIds) {
            //转换为数据库实体类
            TblTodoList tblTodoList = BeanConvertUtil.convertTo(request, TblTodoList::new);
            //补充数据并插入数据库
            tblTodoList.setStartTime(Calendar.getInstance().getTime());
            tblTodoList.setCreateTime(Calendar.getInstance().getTime());
            tblTodoList.setStatus(1);
            tblTodoList.setType(request.getType());
            tblTodoList.setName(request.getName());
            tblTodoList.setOrganizationId(threadLocalUtil.getOrganizationId());
            tblTodoList.setCreateId(threadLocalUtil.getUserId());
            tblTodoList.setInitiatorId(request.getInitiatorId());
            tblTodoList.setTodoCode(code);
            tblTodoList.setDueTime(request.getDueTime() != null ? request.getDueTime() : 2);
            tblTodoList.setAssigneeId(userId.toString());
            tblTodoList.setAssigneeUnit(String.valueOf(request.getAssigneeOrgId()));
            tblTodoList.setFormUrl(formUrl);
            tblTodoList.setProcess(2);
            list.add(tblTodoList);
        }
        if (list.size() > 0) {
            tblTodoListDao.insertList(list);
        }
        return code;
    }

    @Override
    public String addAccidentTodo(AdminAddAccidentTodoRequest request) {
        request.toRequestCheck();
        TblOrganization po = organizationDao.selectOne(TblOrganization.getWrapper().eq(TblOrganization::getOrganizationName, "沙区经信委"));
        AdminAddNoUserNoProcessTodoRequest todoRequest = AdminAddNoUserNoProcessTodoRequest.builder()
                .name(request.getName())
                .type(request.getType())
                .initiatorId(request.getInitiatorId())
                .assigneeOrgId(po.getId())
                .dataId(request.getDataId())
                .dueTime(request.getDueTime())
                .build();
        return addNoUserNoProcessTodo(todoRequest);
    }

    @Override
    public BigDecimal findUserRatio() {
        List<TblTodoList> list = tblTodoListDao.selectList(TblTodoList.getWrapper()
                .eq(TblTodoList::getDeleted, false)
                .eq(TblTodoList::getAssigneeId, threadLocalUtil.getUserId()));
        if (list.size() > 0) {
            BigDecimal num = new BigDecimal(list.stream().filter(x -> x.getStatus() == 2).count());
            return num.multiply(new BigDecimal(100)).divide(new BigDecimal(list.size()), 0, RoundingMode.HALF_UP);
        } else {
            return BigDecimal.ZERO;
        }
    }

    @Override
    public AdminUserTodoStatisticsResponse findUserTodoStatistics() {
        int waitTotal = Math.toIntExact(tblTodoListDao.selectCount(TblTodoList.getWrapper()
                .eq(TblTodoList::getDeleted, false)
                .eq(TblTodoList::getStatus, 1)
                .eq(TblTodoList::getAssigneeId, threadLocalUtil.getUserId())));
        int completedTotal = Math.toIntExact(tblTodoListDao.selectCount(TblTodoList.getWrapper()
                .eq(TblTodoList::getDeleted, false)
                .eq(TblTodoList::getStatus, 2)
                .eq(TblTodoList::getAssigneeId, threadLocalUtil.getUserId())));
        Calendar calendar = Calendar.getInstance();
        Date startTime = CalendarUtil.getFirstHMSM(calendar).getTime();
        Date endTime = CalendarUtil.getLastHMSM(calendar).getTime();
        int todayUsed = Math.toIntExact(tblTodoListDao.selectCount(TblTodoList.getWrapper()
                .eq(TblTodoList::getDeleted, false)
                .eq(TblTodoList::getStatus, 2)
                .between(TblTodoList::getUpdateTime, startTime, endTime)
                .eq(TblTodoList::getAssigneeId, threadLocalUtil.getUserId())));
        return AdminUserTodoStatisticsResponse.builder()
                .waitTotal(waitTotal)
                .todayUsed(todayUsed)
                .completedTotal(completedTotal)
                .build();
    }

    @Override
    public FlowCommentReponse examineDetail(IdRequest request) {
        //通过ID查询待办事项
        TblTodoList tblTodoList = tblTodoListDao.selectById(request.getId());
        //判断待办事项是否存在，是否删除
        if (tblTodoList == null || tblTodoList.getDeleted()) {
            throw new BaseException("待办事项不存在或已删除");
        }
        //获取附件
        LambdaQueryWrapper<TblTodoFile> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TblTodoFile::getTodoId, request.getId());
        queryWrapper.eq(TblTodoFile::getFileType, 1);
        List<TblTodoFile> tblTodoFiles = tblTodoFileDao.selectList(queryWrapper);
        List<FlowCommentAnnexRequest> list = new ArrayList<>();
        for (TblTodoFile tblTodoFile : tblTodoFiles) {
            list.add(FlowCommentAnnexRequest.builder()
                    .fileName(tblTodoFile.getFileName())
                    .realFileName(tblTodoFile.getRealFileName()).build());
        }
        //获取图片
        LambdaQueryWrapper<TblTodoFile> queryWrapper1 = new LambdaQueryWrapper<>();
        queryWrapper1.eq(TblTodoFile::getTodoId, request.getId());
        queryWrapper1.eq(TblTodoFile::getFileType, 2);
        List<TblTodoFile> tblTodoPict = tblTodoFileDao.selectList(queryWrapper1);
        List<FlowCommentAnnexRequest> list2 = new ArrayList<>();
        for (TblTodoFile tblTodoFile : tblTodoPict) {
            list2.add(FlowCommentAnnexRequest.builder()
                    .fileName(tblTodoFile.getFileName())
                    .realFileName(tblTodoFile.getRealFileName()).build());
        }
        return FlowCommentReponse.builder()
                .commentText(tblTodoList.getCommentText())
                .todoId(tblTodoList.getId())
                .pass(tblTodoList.getPass())
                .commentAnnex(list)
                .commentPicture(list2)
                .build();
    }

    @Override
    public List<FlowFormTypeReponse> getType() {
        List<UserTodoListEnum> enumList = new ArrayList<>();
        if (OrganizationTypeEnum.COMPANY.getKey().equals(threadLocalUtil.getOrganizationType())) {
            List<UserTodoListEnum> collect = Arrays.stream(UserTodoListEnum.values())
                    .filter(x -> x.getLimits() == 2 || x.getLimits() == 3)
                    .collect(Collectors.toList());
            enumList.addAll(collect);
        } else if (OrganizationTypeEnum.GOVERNMENT.getKey().equals(threadLocalUtil.getOrganizationType())) {
            List<UserTodoListEnum> collect = Arrays.stream(UserTodoListEnum.values())
                    .filter(x -> x.getLimits() == 1 || x.getLimits() == 3)
                    .collect(Collectors.toList());
            enumList.addAll(collect);
        } else if (OrganizationTypeEnum.SYSTEM_SAAS.getKey().equals(threadLocalUtil.getOrganizationType())) {
            List<UserTodoListEnum> collect = Arrays.stream(UserTodoListEnum.values())
                    .collect(Collectors.toList());
            enumList.addAll(collect);
        }
        List<FlowFormTypeReponse> response = new ArrayList<>();
        enumList.forEach(x -> {
            response.add(FlowFormTypeReponse.builder()
                    .typeEnum(x.getKey())
                    .typeName(x.getTitle())
                    .build());
        });
        return response;
    }

    @Override
    public FlowXmlDiagramResponse accidentProcess(FlowGetXmlRequest request) {
        return flowService.getXmlByKey(request).getData();
    }

    @Override
    public ClientUserTodoDetailResponse accidentDetail(IdRequest request) {
        //通过ID查询待办事项
        TblTodoList tblTodoList = tblTodoListDao.selectById(request.getId());
        //判断待办事项是否存在，是否删除
        if (tblTodoList == null || tblTodoList.getDeleted()) {
            throw new BaseException("待办事项不存在或已删除");
        }
        //获取附件
        LambdaQueryWrapper<TblTodoFile> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TblTodoFile::getTodoId, request.getId());
        queryWrapper.eq(TblTodoFile::getFileType, 1);
        List<TblTodoFile> tblTodoFiles = tblTodoFileDao.selectList(queryWrapper);
        List<FlowCommentAnnexRequest> list = new ArrayList<>();
        for (TblTodoFile tblTodoFile : tblTodoFiles) {
            list.add(FlowCommentAnnexRequest.builder()
                    .fileName(tblTodoFile.getFileName())
                    .realFileName(tblTodoFile.getRealFileName()).build());
        }
        //获取图片
        LambdaQueryWrapper<TblTodoFile> queryWrapper1 = new LambdaQueryWrapper<>();
        queryWrapper1.eq(TblTodoFile::getTodoId, request.getId());
        queryWrapper1.eq(TblTodoFile::getFileType, 2);
        List<TblTodoFile> tblTodoPict = tblTodoFileDao.selectList(queryWrapper1);
        List<FlowCommentAnnexRequest> list2 = new ArrayList<>();
        for (TblTodoFile tblTodoFile : tblTodoPict) {
            list2.add(FlowCommentAnnexRequest.builder()
                    .fileName(tblTodoFile.getFileName())
                    .realFileName(tblTodoFile.getRealFileName()).build());
        }
        long dataId;
        if (tblTodoList.getProcess() == 1) {
            List<String> taskId = new ArrayList<>();
            taskId.add(tblTodoList.getDataId());
            Map<String, String> projectIdBytodoId = flowService.getProjectIdBytodoId(taskId);
            dataId = Long.parseLong(projectIdBytodoId.getOrDefault(tblTodoList.getDataId(), ""));
        } else {
            dataId = Long.parseLong(tblTodoList.getDataId());
        }

        return ClientUserTodoDetailResponse.builder()
                .todoId(tblTodoList.getId())
                .dataId(dataId)
                .dueDate(tblTodoList.getDueTime())
                .tackleDate(tblTodoList.getEndTime())
                .commentText(tblTodoList.getCommentText())
                .status(tblTodoList.getStatus())
                .commentAnnex(list)
                .commentPicture(list2)
                .build();
    }

    @Override
    public List<FlowEmergencyListReponse> getEmergencyProcess() {
        return flowService.getEmergencyProcess().getData();
    }

    @Override
    @Transactional
    public boolean accidentHand(AccidentHandlingRequest request) {
        //通过ID查询待办事项
        TblTodoList tblTodoList = tblTodoListDao.selectById(request.getTodoId());
        //判断待办事项是否存在，是否删除
        if (tblTodoList == null || tblTodoList.getDeleted()) {
            throw new BaseException("待办事项不存在或已删除");
        }
        FlowCommentRequest flowCommentRequest = new FlowCommentRequest();
        flowCommentRequest.setTodoId(request.getTodoId());
        flowCommentRequest.setPass(request.getPass());
        flowCommentRequest.setCommentAnnex(request.getCommentAnnex());
        flowCommentRequest.setCommentText(request.getCommentText());
        flowCommentRequest.setCommentPicture(request.getCommentPicture());
        String data = flowService.completeAndComment(flowCommentRequest).getData();
        WorkCalendarResponse workCalendarResponse = calendarService.checkWork(WorkCalendarRequest.builder()
                .workDay(tblTodoList.getDueTime())
                .endTime(new Date())
                .startTime(tblTodoList.getStartTime()).build());
        //修改待办事项为已处理（1:未处理,2:已处理）
        tblTodoList.setStatus(2);
        tblTodoList.setWorkTime(workCalendarResponse.getWorkTime());
        tblTodoList.setTimeOut(workCalendarResponse.getWorkStatus());
        tblTodoList.setEndTime(new Date());
        tblTodoList.setPass(request.getPass());
        tblTodoList.setCommentText(request.getCommentText());
        int i = tblTodoListDao.updateById(tblTodoList);
        //保存附件
        if (ObjectUtils.isNotEmpty(request.getCommentAnnex())) {
            List<FlowCommentAnnexRequest> commentAnnex = request.getCommentAnnex();
            List<TblTodoFile> list = new ArrayList<>();
            if (ObjectUtils.isNotEmpty(commentAnnex)) {
                for (FlowCommentAnnexRequest annex : commentAnnex) {
                    list.add(TblTodoFile.builder()
                            .realFileName(annex.getRealFileName())
                            .fileName(annex.getFileName())
                            .fileType(1)
                            .todoId(request.getTodoId()).build());
                }
            }
            tblTodoFileDao.insertList(list);
        }

        //保存图片
        if (ObjectUtils.isNotEmpty(request.getCommentPicture())) {
            List<FlowCommentAnnexRequest> commentPicture = request.getCommentPicture();
            List<TblTodoFile> list2 = new ArrayList<>();
            if (ObjectUtils.isNotEmpty(commentPicture)) {
                for (FlowCommentAnnexRequest picture : commentPicture) {
                    list2.add(TblTodoFile.builder()
                            .realFileName(picture.getRealFileName())
                            .fileName(picture.getFileName())
                            .fileType(2)
                            .todoId(request.getTodoId()).build());
                }
            }
            tblTodoFileDao.insertList(list2);
        }
        return i > 0;
    }

    @Override
    public boolean completeToListByCode(AdminDelTodoListByCodeRequest request) {
        LambdaQueryWrapper<TblTodoList> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TblTodoList::getType, request.getType());
        queryWrapper.eq(TblTodoList::getTodoCode, request.getTodoCode());
        TblTodoList tblTodoList = tblTodoListDao.selectOne(queryWrapper);
        tblTodoList.setStatus(2);
        tblTodoList.setEndTime(new Date());
        int i = tblTodoListDao.updateById(tblTodoList);
        return i > 0;
    }

    @Override
    public List<AdminPointStatisticsResponse> findPointTodoStatistics() {
        List<AdminPointStatisticsResponse> response = new ArrayList<>();
        List<String> types = UserTodoListEnum.getProjectTypes();
        LambdaQueryWrapper<TblTodoList> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(TblTodoList::getDeleted, false)
                .in(TblTodoList::getType, types);
        List<TblTodoList> todoList = tblTodoListDao.selectList(wrapper);

        Set<String> list = new HashSet<>();
        Map<String, Integer> approvalCountMap = new HashMap<>();
        Map<String, Integer> outTimeCountMap = new HashMap<>();
        Map<String, BigDecimal> longitudeMap = new HashMap<>();
        Map<String, BigDecimal> latitudeMap = new HashMap<>();
        Map<String, Map<String, Integer>> typeStreetMap = new HashMap<>();
        Map<String, Map<String, List<AdminGoudyStreetDetailResponse>>> detailMap = new HashMap<>();

        List<String> ids = todoList.stream().map(TblTodoList::getDataId).collect(Collectors.toList());
        Map<String, String> projectIdBytodoIdMap = flowService.getProjectIdBytodoId(ids);

        for (TblTodoList todo : todoList) {
            String type = todo.getType();
            String title = UserTodoListEnum.query(type).getTitle();

            list.add(title);
            boolean hasEndTime = ObjectUtils.isNotEmpty(todo.getEndTime());
            boolean isOutTime = ObjectUtils.isNotEmpty(todo.getTimeOut()) && todo.getTimeOut() == 3;

            if (hasEndTime) {
                approvalCountMap.put(title, approvalCountMap.getOrDefault(title, 0) + 1);
            }

            if (isOutTime) {
                outTimeCountMap.put(title, outTimeCountMap.getOrDefault(title, 0) + 1);
            }
        }

        for (String type : list) {
            Integer approvalNumber = approvalCountMap.getOrDefault(type, 0);
            Integer outTimeNumber = outTimeCountMap.getOrDefault(type, 0);

            response.add(AdminPointStatisticsResponse.builder()
                    .pointName(type)
                    .outTimeNumber(outTimeNumber)
                    .approvalNumber(approvalNumber)
                    .list(new ArrayList<>())
                    .build());
        }
        return response;
    }


}
