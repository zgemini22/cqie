package com.zds.user.service;

import com.zds.biz.vo.request.user.StationDeviceRequest;
import com.zds.biz.vo.response.user.StationDeviceResponse;
import java.util.List;

public interface StationDeviceService {
    // 获取站点和设备状态信息
    List<StationDeviceResponse> getStationDeviceInfo(StationDeviceRequest request);
}