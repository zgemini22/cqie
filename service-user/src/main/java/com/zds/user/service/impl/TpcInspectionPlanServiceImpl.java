package com.zds.user.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zds.biz.util.ThreadLocalUtil;
import com.zds.biz.vo.request.user.TpcInspectionPlanRequest;
import com.zds.biz.vo.response.user.TpcInspectionPlanDetailResponse;
import com.zds.biz.vo.response.user.TpcInspectionPlanResponse;
import com.zds.user.dao.TpcInspectionPlanDao;
import com.zds.user.po.TpcInspectionPlan;
import com.zds.user.service.TpcInspectionPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TpcInspectionPlanServiceImpl implements TpcInspectionPlanService {

    @Autowired
    private ThreadLocalUtil threadLocalUtil;

    @Autowired
    private TpcInspectionPlanDao tpcInspectionPlanDao;

    @Override
    public boolean save(TpcInspectionPlanRequest saveRequest) {
        TpcInspectionPlan tpcInspectionPlan = new TpcInspectionPlan();
        BeanUtil.copyProperties(saveRequest, tpcInspectionPlan);
        tpcInspectionPlan.setOrgId(threadLocalUtil.getOrganizationId());
        return tpcInspectionPlanDao.insert(tpcInspectionPlan) > 0;
    }

    @Override
    public boolean edit(TpcInspectionPlanRequest updateRequest) {
        TpcInspectionPlan tpcInspectionPlan = new TpcInspectionPlan();
        BeanUtil.copyProperties(updateRequest, tpcInspectionPlan);
        tpcInspectionPlan.setOrgId(null);
        return tpcInspectionPlanDao.updateById(tpcInspectionPlan) > 0;
    }

    @Override
    public boolean deleteById(Long id) {
        return tpcInspectionPlanDao.deleteById(id) > 0;
    }

    @Override
    public TpcInspectionPlanDetailResponse detail(Long id) {
        return tpcInspectionPlanDao.selectDetailById(id);
    }

    @Override
    public IPage<TpcInspectionPlanResponse> list(TpcInspectionPlanRequest request) {
        return tpcInspectionPlanDao.selectPageWithJoin(new Page<>(request.getPageNum(), request.getPageSize()), request);
    }
}
