package com.zds.user.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zds.biz.vo.request.user.DeviceDetectionListRequest;
import com.zds.biz.vo.response.user.DeviceDetailResponse;
import com.zds.biz.vo.response.user.LeakDetectionResponse;
import com.zds.biz.vo.response.user.RunDetectionResponse;
import com.zds.user.service.StationDetectionService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/station/detection")
public class StationDetectionController {

    @Resource
    private StationDetectionService stationDetectionService;

    /**
     * 运行监测列表
     */
    @PostMapping("/runDetection/list")
    public IPage<RunDetectionResponse> getRunDetectionList(@RequestBody DeviceDetectionListRequest request) {
        return stationDetectionService.getRunDetectionList(request);
    }

    /**
     * 泄漏监测列表
     */
    @PostMapping("/leakDetection/list")
    public IPage<LeakDetectionResponse> getLeakDetectionList(@RequestBody DeviceDetectionListRequest request) {
        return stationDetectionService.getLeakDetectionList(request);
    }

    /**
     * 设备详情
     */
    @PostMapping("/device/detail")
    public DeviceDetailResponse getDeviceDetail(@RequestParam("deviceCode") String deviceCode) {
        return stationDetectionService.getDeviceDetail(deviceCode);
    }
}