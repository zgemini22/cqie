package com.zds.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zds.biz.vo.request.user.DeviceDetectionListRequest;
import com.zds.biz.vo.request.user.StationDeviceRequest;
import com.zds.biz.vo.response.user.DeviceDetailResponse;
import com.zds.biz.vo.response.user.LeakDetectionResponse;
import com.zds.biz.vo.response.user.RunDetectionResponse;
import com.zds.biz.vo.response.user.StationDeviceResponse;
import feign.Param;

import java.util.List;

public interface StationDeviceDao extends BaseMapper<StationDeviceResponse> {
    // 联合查询站点和设备状态
    List<StationDeviceResponse> selectStationDeviceInfo(StationDeviceRequest request);

    // 获取运行监测列表
    IPage<RunDetectionResponse> getRunDetectionList(IPage<?> page, @Param("request") DeviceDetectionListRequest request);

    // 获取泄漏监测列表
    IPage<LeakDetectionResponse> getLeakDetectionList(IPage<?> page, @Param("request") DeviceDetectionListRequest request);

    // 根据设备编码查询设备详情
    DeviceDetailResponse selectDeviceDetailByCode(@Param("deviceCode") String deviceCode);
}