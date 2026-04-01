package com.zds.user.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zds.biz.vo.request.user.DeviceDetectionListRequest;
import com.zds.biz.vo.response.user.DeviceDetailResponse;
import com.zds.biz.vo.response.user.LeakDetectionResponse;
import com.zds.biz.vo.response.user.RunDetectionResponse;
import com.zds.user.dao.StationDeviceDao;
import com.zds.user.service.StationDetectionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class StationDetectionServiceImpl implements StationDetectionService {
    /**
     * 站点设备数据访问层
     */
    @Resource
    private StationDeviceDao stationDeviceDao;

    /**
     * 获取运行监测列表
     */
    @Override
    public IPage<RunDetectionResponse> getRunDetectionList(DeviceDetectionListRequest request) {
        Page<RunDetectionResponse> page = new Page<>(request.getPageNum(), request.getPageSize());
        return stationDeviceDao.getRunDetectionList(page, request);
    }

    /**
     * 获取泄漏监测列表
     */
    @Override
    public IPage<LeakDetectionResponse> getLeakDetectionList(DeviceDetectionListRequest request) {
        Page<LeakDetectionResponse> page = new Page<>(request.getPageNum(), request.getPageSize());
        return stationDeviceDao.getLeakDetectionList(page, request);
    }

    /**
     * 根据设备编码查询设备详情
     */
    @Override
    public DeviceDetailResponse getDeviceDetail(String deviceCode) {
        return stationDeviceDao.selectDeviceDetailByCode(deviceCode);
    }
}