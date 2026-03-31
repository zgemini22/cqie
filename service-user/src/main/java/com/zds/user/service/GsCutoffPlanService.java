package com.zds.user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zds.biz.vo.request.user.GsCutoffPlanRequest;
import com.zds.biz.vo.response.user.GsCutoffPlanDetailResponse;
import com.zds.biz.vo.response.user.GsCutoffPlanResponse;

public interface GsCutoffPlanService {

    boolean save(GsCutoffPlanRequest request);

    boolean edit(GsCutoffPlanRequest request);

    boolean deleteById(Long id);

    GsCutoffPlanDetailResponse detail(Long id, String detailAddress);

    IPage<GsCutoffPlanResponse> list(GsCutoffPlanRequest request);
}
