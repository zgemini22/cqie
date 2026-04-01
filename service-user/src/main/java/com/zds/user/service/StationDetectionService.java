package com.zds.user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zds.biz.vo.request.user.DeviceDetectionListRequest;
import com.zds.biz.vo.response.user.DeviceDetailResponse;
import com.zds.biz.vo.response.user.LeakDetectionResponse;
import com.zds.biz.vo.response.user.RunDetectionResponse;

public interface StationDetectionService {
    /**
     * 运行监测列表
     */
    IPage<RunDetectionResponse> getRunDetectionList(DeviceDetectionListRequest request);

    /**
     * 泄漏监测列表
     */
    IPage<LeakDetectionResponse> getLeakDetectionList(DeviceDetectionListRequest request);

    /**
     * 根据设备编码查询设备详情
     */
    DeviceDetailResponse getDeviceDetail(String deviceCode);
}