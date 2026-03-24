package com.zds.user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zds.biz.vo.request.user.TpcInspectionPlanRequest;
import com.zds.biz.vo.response.user.TpcInspectionPlanDetailResponse;
import com.zds.biz.vo.response.user.TpcInspectionPlanResponse;

public interface TpcInspectionPlanService {

    boolean save(TpcInspectionPlanRequest saveRequest);

    boolean edit(TpcInspectionPlanRequest request);

    boolean deleteById(Long id);

    TpcInspectionPlanDetailResponse detail(Long id);

    IPage<TpcInspectionPlanResponse> list(TpcInspectionPlanRequest request);
}
