package com.zds.user.controller.client;

import com.zds.biz.targetcheck.Authorization;
import com.zds.biz.vo.BaseResult;
import com.zds.biz.vo.request.user.AreaRequest;
import com.zds.biz.vo.response.user.AreaResponse;
import com.zds.user.service.AreaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "前台地理编码模块")
@RestController
@RequestMapping(value = "/client/area")
public class ClientAreaController {

    @Autowired
    private AreaService areaService;

    @Authorization
    @ApiOperation(value = "查询省市区街道编码")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public BaseResult<List<AreaResponse>> findAreaList(@RequestBody AreaRequest request) {
        return BaseResult.success(areaService.findAreaList(request));
    }
}
