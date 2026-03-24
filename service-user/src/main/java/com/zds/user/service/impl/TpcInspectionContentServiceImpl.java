package com.zds.user.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zds.biz.util.ThreadLocalUtil;
import com.zds.biz.vo.request.user.TpcInspectionContentRequest;
import com.zds.biz.vo.response.user.TpcInspectionContentDetailResponse;
import com.zds.biz.vo.response.user.TpcInspectionContentResponse;
import com.zds.user.dao.TpcInspectionContentDao;
import com.zds.user.dao.TpcInspectionPlanDao;
import com.zds.user.po.TpcInspectionContent;
import com.zds.user.po.TpcInspectionPlan;
import com.zds.user.service.TpcInspectionContentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;

@Slf4j
@Service
public class TpcInspectionContentServiceImpl implements TpcInspectionContentService {

    private static final String STATUS_INSPECTED = "已巡检";
    private static final String STATUS_PENDING = "待巡检";
    private static final String STATUS_OVERDUE = "已超期";

    @Autowired
    private ThreadLocalUtil threadLocalUtil;

    @Autowired
    private TpcInspectionContentDao tpcInspectionContentDao;

    @Autowired
    private TpcInspectionPlanDao tpcInspectionPlanDao;

    /**
     * 保存巡检内容
     * @param request 巡检内容请求参数
     * @return 是否保存成功
     */
    @Override
    public boolean save(TpcInspectionContentRequest request) {
        TpcInspectionContent tpcInspectionContent = new TpcInspectionContent();
        BeanUtil.copyProperties(request, tpcInspectionContent);
        tpcInspectionContent.setOrgId(threadLocalUtil.getOrganizationId());
        return tpcInspectionContentDao.insert(tpcInspectionContent) > 0;
    }

    /**
     * 编辑巡检内容
     * @param request 巡检内容请求参数
     * @return 是否编辑成功
     */
    @Override
    public boolean edit(TpcInspectionContentRequest request) {
        TpcInspectionContent tpcInspectionContent = new TpcInspectionContent();
        BeanUtil.copyProperties(request, tpcInspectionContent);
        tpcInspectionContent.setOrgId(null);
        return tpcInspectionContentDao.updateById(tpcInspectionContent) > 0;
    }

    /**
     * 根据id删除巡检内容
     * @param id 巡检内容id
     * @return 是否删除成功
     */
    @Override
    public boolean deleteById(Long id) {
        return tpcInspectionContentDao.deleteById(id) > 0;
    }

    /**
     * 根据计划id查询巡检内容详情，并对巡检状态进行更新
     * <p>
     * 巡检状态判断逻辑：
     * 1. 有内容 -> 已巡检
     * 2. 无内容且当前时间超过有效期 -> 已超期
     * 3. 无内容且当前时间未超过有效期 -> 待巡检
     * </p>
     * @param id 巡检计划id
     * @return 巡检内容详情
     */
    @Override
    public TpcInspectionContentDetailResponse detail(Long id) {
        TpcInspectionPlan plan = tpcInspectionPlanDao.selectById(id);
        if (plan == null) {
            throw new IllegalArgumentException("巡检计划不存在");
        }

        TpcInspectionContent content = tpcInspectionContentDao.selectContentByPlanId(id);
        if (content == null) {
            throw new IllegalArgumentException("巡检内容不存在");
        }

        String newStatus = determineInspectStatus(content.getContent(), plan.getValidEndDate());
        updateStatusIfNeeded(plan, newStatus);

        return tpcInspectionContentDao.selectDetailById(id);
    }

    /**
     * 根据巡检内容和有效期判断巡检状态
     * @param content 巡检内容
     * @param validEndDate 有效期截止时间
     * @return 巡检状态
     */
    private String determineInspectStatus(String content, Date validEndDate) {
        if (StrUtil.isNotBlank(content)) {
            return STATUS_INSPECTED;
        }

        if (validEndDate != null && DateUtil.date().isAfter(validEndDate)) {
            return STATUS_OVERDUE;
        }

        return STATUS_PENDING;
    }

    /**
     * 如果状态发生变化，更新数据库中的巡检状态
     * @param plan 巡检计划
     * @param newStatus 新状态
     */
    private void updateStatusIfNeeded(TpcInspectionPlan plan, String newStatus) {
        if (!newStatus.equals(plan.getInspectStatus())) {
            log.info("巡检状态变更，计划ID: {}, 原状态: {}, 新状态: {}", plan.getId(), plan.getInspectStatus(), newStatus);
            plan.setInspectStatus(newStatus);
            tpcInspectionPlanDao.updateById(plan);
        }
    }

    /**
     * 分页查询巡检内容列表
     * @param request 查询请求参数
     * @return 分页结果
     */
    @Override
    public IPage<TpcInspectionContentResponse> list(TpcInspectionContentRequest request) {
        return tpcInspectionContentDao.selectPageWithJoin(new Page<>(request.getPageNum(), request.getPageSize()), request);
    }
}
