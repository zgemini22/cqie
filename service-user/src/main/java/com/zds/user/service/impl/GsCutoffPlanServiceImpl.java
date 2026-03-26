package com.zds.user.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zds.biz.util.ThreadLocalUtil;
import com.zds.biz.vo.request.user.GsCutoffPlanRequest;
import com.zds.biz.vo.response.user.GsCutoffPlanDetailResponse;
import com.zds.biz.vo.response.user.GsCutoffPlanResponse;
import com.zds.user.dao.GsCutoffPlanDao;
import com.zds.user.po.GsCutoffPlan;
import com.zds.user.service.GsCutoffPlanService;
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
    public GsCutoffPlanDetailResponse detail(Long id) {
        GsCutoffPlan gsCutoffPlan = gsCutoffPlanDao.selectById(id);
        if (gsCutoffPlan == null) {
            throw new IllegalArgumentException("停气计划不存在");
        }
        GsCutoffPlanDetailResponse response = new GsCutoffPlanDetailResponse();
        BeanUtil.copyProperties(gsCutoffPlan, response);
        return response;
    }

    @Override
    public IPage<GsCutoffPlanResponse> list(GsCutoffPlanRequest request) {
        LambdaQueryWrapper<GsCutoffPlan> wrapper = new LambdaQueryWrapper<>();
        // 根据request条件查询所有满足条件的的数据

        // 查询停气区域通过id找到区域表查询detail_address

        // 返回response
        return gsCutoffPlanDao.selectPageWithJoin(new Page<>(request.getPageNum(), request.getPageSize()), request);
    }
}
