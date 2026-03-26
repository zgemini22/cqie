package com.zds.user.dao;

import com.zds.user.po.GsCutoffPlan;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zds.biz.vo.request.user.GsCutoffPlanRequest;
import com.zds.biz.vo.response.user.GsCutoffPlanResponse;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface GsCutoffPlanDao extends BaseMapper<GsCutoffPlan> {

    IPage<GsCutoffPlanResponse> selectPageWithJoin(Page<?> page, @Param("request") GsCutoffPlanRequest request);

}
