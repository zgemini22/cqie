package com.zds.user.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zds.biz.util.ThreadLocalUtil;
import com.zds.biz.vo.request.user.GsCutoffPlanRequest;
import com.zds.biz.vo.response.user.GsCutoffPlanDetailResponse;
import com.zds.biz.vo.response.user.GsCutoffPlanResponse;
import com.zds.user.dao.GsCutoffAreaNodeDao;
import com.zds.user.dao.GsCutoffPlanDao;
import com.zds.user.dao.TblOrganizationDao;
import com.zds.user.po.GsCutoffAreaNode;
import com.zds.user.po.GsCutoffPlan;
import com.zds.user.po.TblOrganization;
import com.zds.user.service.GsCutoffPlanService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class GsCutoffPlanServiceImpl implements GsCutoffPlanService {

    @Autowired
    private ThreadLocalUtil threadLocalUtil;

    @Autowired
    private GsCutoffPlanDao gsCutoffPlanDao;

    @Autowired
    private GsCutoffAreaNodeDao  gsCutoffAreaNodeDao;

    @Autowired
    private TblOrganizationDao tblOrganizationDao;

    @Override
    public boolean save(GsCutoffPlanRequest request) {
        GsCutoffPlan gsCutoffPlan = new GsCutoffPlan();
        BeanUtil.copyProperties(request, gsCutoffPlan);
        gsCutoffPlan.setOrgId(threadLocalUtil.getOrganizationId());
        return gsCutoffPlanDao.insert(gsCutoffPlan) > 0;
    }

    @Override
    public boolean edit(GsCutoffPlanRequest request) {
        GsCutoffPlan gsCutoffPlan = new GsCutoffPlan();
        BeanUtil.copyProperties(request, gsCutoffPlan);
        gsCutoffPlan.setOrgId(null);
        return gsCutoffPlanDao.updateById(gsCutoffPlan) > 0;
    }

    @Override
    public boolean deleteById(Long id) {
        return gsCutoffPlanDao.deleteById(id) > 0;
    }

    @Override
    public GsCutoffPlanDetailResponse detail(Long id, String detailAddress) {
        GsCutoffPlan gsCutoffPlan = gsCutoffPlanDao.selectById(id);
        if (gsCutoffPlan == null) {
            throw new IllegalArgumentException("停气计划不存在");
        }
        if (StrUtil.isBlank(detailAddress)) {
            throw new IllegalArgumentException("停气区域为空");
        }
        GsCutoffPlanDetailResponse response = new GsCutoffPlanDetailResponse();
        response.setDetailAddress(detailAddress); // 停气区域
        BeanUtil.copyProperties(gsCutoffPlan, response);

        // 查询organization表获得城燃企业
        LambdaQueryWrapper<TblOrganization> orgWrapper = new LambdaQueryWrapper<>();
        orgWrapper.eq(TblOrganization::getId, gsCutoffPlan.getOrgId());
        TblOrganization tblOrganization = tblOrganizationDao.selectOne(orgWrapper);
        if (tblOrganization != null) {
            response.setOrganizationName(tblOrganization.getOrganizationName());
        }

        return response;
    }

    /**
     * 分页查询停气计划列表
     * @param request 查询请求参数，包含停气时间、恢复时间、停气类型等条件
     * @return 分页结果，包含停气计划信息及关联的停气区域地址
     */
    @Override
    public IPage<GsCutoffPlanResponse> list(GsCutoffPlanRequest request) {
        return gsCutoffPlanDao.selectPageWithJoin(new Page<>(request.getPageNum(), request.getPageSize()), request);
    }
}
