// GsSupplyOperationService.java
package com.zds.user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zds.biz.vo.GasSupplyOperationQueryRequest;
import com.zds.biz.vo.GasSupplyOperationResponse;

public interface GsSupplyOperationService {
    IPage<GasSupplyOperationResponse> selectPage(GasSupplyOperationQueryRequest request);
}