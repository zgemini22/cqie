package com.zds.user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zds.biz.vo.BaseResult;
import com.zds.biz.vo.GasSupplyQueryRequest;
import com.zds.biz.vo.GasSupplyRequest;
import com.zds.biz.vo.GasSupplyResponse;

public interface GasSupplyService {

    IPage<GasSupplyResponse> selectPage(GasSupplyQueryRequest request);

    BaseResult<String> add(GasSupplyRequest request);

    BaseResult<String> update(GasSupplyRequest request);

    BaseResult<String> delete(Integer id);

    GasSupplyResponse getDetail(Integer id);
}