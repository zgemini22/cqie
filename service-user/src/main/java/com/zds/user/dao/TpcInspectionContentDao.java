package com.zds.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zds.biz.vo.request.user.TpcInspectionContentRequest;
import com.zds.biz.vo.response.user.TpcInspectionContentDetailResponse;
import com.zds.biz.vo.response.user.TpcInspectionContentResponse;
import com.zds.user.po.TpcInspectionContent;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TpcInspectionContentDao extends BaseMapper<TpcInspectionContent> {

    IPage<TpcInspectionContentResponse> selectPageWithJoin(Page<?> page, @Param("request") TpcInspectionContentRequest request);

    TpcInspectionContentDetailResponse selectDetailById(@Param("id") Long id);

    TpcInspectionContent selectContentByPlanId(@Param("planId") Long planId);

}
