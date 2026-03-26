package com.zds.user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zds.biz.vo.request.user.GsCutoffOperationRequest;
import com.zds.biz.vo.response.user.GsCutoffOperationDetailResponse;
import com.zds.biz.vo.response.user.GsCutoffOperationResponse;

public interface GsCutoffOperationService {

    boolean save(GsCutoffOperationRequest request);

    boolean edit(GsCutoffOperationRequest request);

    boolean deleteById(Long id);

    GsCutoffOperationDetailResponse detail(Long id);

    IPage<GsCutoffOperationResponse> list(GsCutoffOperationRequest request);
}
