package com.zds.user.controller.admin;

import com.zds.biz.targetcheck.Authorization;
import com.zds.biz.vo.BaseResult;
import com.zds.biz.vo.request.user.StationDeviceRequest;
import com.zds.biz.vo.response.user.StationDeviceResponse;
import com.zds.user.service.StationDeviceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@Api(tags = "站点简单信息")
@RestController
@RequestMapping("/station/device")
public class StationDeviceController {

    @Autowired
    private StationDeviceService stationDeviceService;

    @Authorization
    @ApiOperation("获取站点简单信息")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public BaseResult<List<StationDeviceResponse>> getStationDeviceInfo(@RequestBody StationDeviceRequest request) {
        return BaseResult.success(stationDeviceService.getStationDeviceInfo(request));
    }
}