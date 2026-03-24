package com.zds.user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zds.biz.vo.request.user.TpcConstructionRequest;
import com.zds.biz.vo.response.user.TpcConstructionDetailResponse;
import com.zds.biz.vo.response.user.TpcConstructionResponse;

public interface TpcConstructionService {

    boolean save(TpcConstructionRequest saveRequest);

    boolean edit(TpcConstructionRequest request);

    boolean deleteById(Long id);

    TpcConstructionDetailResponse detail(Long id);

    IPage<TpcConstructionResponse> list(TpcConstructionRequest request);
}
