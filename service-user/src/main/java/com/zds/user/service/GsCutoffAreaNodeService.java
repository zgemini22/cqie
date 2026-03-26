package com.zds.user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zds.biz.vo.request.user.GsCutoffAreaNodeRequest;
import com.zds.biz.vo.response.user.GsCutoffAreaNodeDetailResponse;
import com.zds.biz.vo.response.user.GsCutoffAreaNodeResponse;

public interface GsCutoffAreaNodeService {

    boolean save(GsCutoffAreaNodeRequest request);

    boolean edit(GsCutoffAreaNodeRequest request);

    boolean deleteById(Long id);

    GsCutoffAreaNodeDetailResponse detail(Long id);

    IPage<GsCutoffAreaNodeResponse> list(GsCutoffAreaNodeRequest request);
}
