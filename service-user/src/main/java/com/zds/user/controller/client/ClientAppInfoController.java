package com.zds.user.controller.client;

import com.zds.biz.vo.BaseResult;
import com.zds.biz.vo.request.user.AppVersionRequest;
import com.zds.user.po.AppVersion;
import com.zds.user.service.AppService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "前台APP版本模块")
@RestController
@RequestMapping(value = "/client/appInfo")
public class ClientAppInfoController {

    @Autowired
    private AppService appService;

    @ApiOperation("获取APP最新版本信息(燃气系统)")
    @RequestMapping(value = "/version/info", method = RequestMethod.POST)
    public BaseResult<AppVersion> getNewAppInfo(@RequestBody AppVersionRequest request){
        return BaseResult.success(appService.getNewAppInfo(request));
    }
}
