package com.zds.user.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zds.biz.targetcheck.Authorization;
import com.zds.biz.vo.BaseResult;
import com.zds.biz.vo.IdRequest;
import com.zds.biz.vo.request.user.AppVersionAddRequest;
import com.zds.biz.vo.request.user.AppVersionFindRequest;
import com.zds.biz.vo.request.user.AppVersionStatusRequest;
import com.zds.user.po.AppVersion;
import com.zds.user.service.AppService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "后台APP版本模块")
@RestController
@RequestMapping(value = "/admin/appInfo")
public class AdminAppInfoController {

    @Autowired
    private AppService appService;

    @Authorization
    @ApiOperation("APP版本信息列表")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public BaseResult<IPage<AppVersion>> findList(@RequestBody AppVersionFindRequest request){
        return BaseResult.success(appService.findList(request));
    }

    @Authorization
    @ApiOperation("新增APP版本信息")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public BaseResult<String> addAppVersion(@RequestBody AppVersionAddRequest request){
        return BaseResult.judgeOperate(appService.addAppVersion(request));
    }

    @Authorization
    @ApiOperation("更新APP版本信息状态")
    @RequestMapping(value = "/update/status", method = RequestMethod.POST)
    public BaseResult<String> updateAppVersionStatus(@RequestBody AppVersionStatusRequest request){
        return BaseResult.judgeOperate(appService.updateAppVersionStatus(request));
    }

    @Authorization
    @ApiOperation("删除APP版本信息")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public BaseResult<String> deleteAppVersion(@RequestBody IdRequest request){
        return BaseResult.judgeOperate(appService.deleteAppVersion(request.getId()));
    }
}
