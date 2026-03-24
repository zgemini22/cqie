package com.zds.user.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zds.biz.vo.request.user.TpcInspectionPlanRequest;
import com.zds.biz.vo.response.user.TpcInspectionPlanDetailResponse;
import com.zds.biz.vo.response.user.TpcInspectionPlanResponse;
import com.zds.user.po.TpcInspectionPlan;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TpcInspectionPlanDao extends BaseMapper<TpcInspectionPlan> {

    IPage<TpcInspectionPlanResponse> selectPageWithJoin(Page<?> page, @Param("request") TpcInspectionPlanRequest request);

    TpcInspectionPlanDetailResponse selectDetailById(@Param("id") Long id);

}
