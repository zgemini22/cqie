package com.zds.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zds.biz.vo.request.user.StationDeviceRequest;
import com.zds.biz.vo.response.user.StationDeviceResponse;
import java.util.List;

public interface StationDeviceDao extends BaseMapper<StationDeviceResponse> {
    // 联合查询站点和设备状态
    List<StationDeviceResponse> selectStationDeviceInfo(StationDeviceRequest request);
}