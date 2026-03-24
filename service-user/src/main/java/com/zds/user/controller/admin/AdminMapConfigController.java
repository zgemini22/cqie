package com.zds.user.controller.admin;

import com.zds.biz.vo.BaseResult;
import com.zds.biz.vo.response.user.MapConfigResponse;
import com.zds.user.service.MapConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "地图配置模块")
@RestController
@RequestMapping("/admin/mapConfig")
public class AdminMapConfigController {

    @Autowired
    private MapConfigService mapConfigService;

    @ApiOperation(value = "查询地图配置列表")
    @RequestMapping(value = "/list")
    public BaseResult<List<MapConfigResponse>> findList(){
        return BaseResult.success(mapConfigService.findList());
    }
}
