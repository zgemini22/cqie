package com.zds.user.service.impl;

import com.zds.biz.vo.request.user.StationDeviceRequest;
import com.zds.biz.vo.response.user.StationDeviceResponse;
import com.zds.user.dao.StationDeviceDao;
import com.zds.user.service.StationDeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class StationDeviceServiceImpl implements StationDeviceService {

    @Autowired
    private StationDeviceDao stationDeviceDao;

    @Override
    public List<StationDeviceResponse> getStationDeviceInfo(StationDeviceRequest request) {
        return stationDeviceDao.selectStationDeviceInfo(request);
    }
}