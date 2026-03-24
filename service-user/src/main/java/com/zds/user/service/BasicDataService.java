package com.zds.user.service;

import com.zds.biz.vo.request.user.BasicDataRequest;
import com.zds.biz.vo.request.user.BasicSaveRequest;
import com.zds.biz.vo.response.user.BasicDataResponse;

import java.util.List;

public interface BasicDataService {

    /**
     * 根据数据标识查询基础数据
     */
    BasicDataResponse selectByKey(BasicDataRequest request);

    /**
     * 基础数据列表
     */
    List<BasicDataResponse> findList();

    /**
     * 修改基础数据
     */
    boolean save(BasicSaveRequest request);
}
