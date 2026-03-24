package com.zds.user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zds.biz.vo.request.user.TpcInspectionContentRequest;
import com.zds.biz.vo.response.user.TpcInspectionContentDetailResponse;
import com.zds.biz.vo.response.user.TpcInspectionContentResponse;

public interface TpcInspectionContentService {

    boolean save(TpcInspectionContentRequest request);

    boolean edit(TpcInspectionContentRequest request);

    boolean deleteById(Long id);

    TpcInspectionContentDetailResponse detail(Long id);

    IPage<TpcInspectionContentResponse> list(TpcInspectionContentRequest request);
}
