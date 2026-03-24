package com.zds.user.controller.admin;

import com.zds.biz.vo.BaseResult;
import com.zds.biz.vo.CoordinateVo;
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
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
import java.util.Map;

@Api(tags = "后台地理编码模块")
@RestController
@RequestMapping(value = "/admin/area")
public class AdminAreaController {

    @Autowired
    private AreaService areaService;

    @ApiIgnore
    @ApiOperation(value = "查询指定省市区编码范围的省市区名称")
    @RequestMapping(value = "/map", method = RequestMethod.POST)
    public BaseResult<Map<String, String>> findAreaMapByCode(@RequestBody List<String> request) {
        return BaseResult.success(areaService.findAreaMapByCode(request));
    }

    @ApiOperation(value = "查询省市区街道编码")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public BaseResult<List<AreaResponse>> findAreaList(@RequestBody AreaRequest request) {
        return BaseResult.success(areaService.findAreaList(request));
    }

    @ApiIgnore
    @ApiOperation(value = "查询重庆指定区名称范围的区街道编码")
    @RequestMapping(value = "/name/map", method = RequestMethod.POST)
    public BaseResult<Map<String, String>> findAreaNameMapByCode(@RequestBody List<String> list) {
        return BaseResult.success(areaService.findAreaNameMapByCode(list));
    }

    @ApiOperation(value = "经纬度查询镇街（回参key为拼接 lng-lat）")
    @RequestMapping(value = "/find/street", method = RequestMethod.POST)
    public BaseResult<Map<String , String>> findStreet(@RequestBody List<CoordinateVo> request) {
        return BaseResult.success(areaService.findStreet(request));
    }
}
