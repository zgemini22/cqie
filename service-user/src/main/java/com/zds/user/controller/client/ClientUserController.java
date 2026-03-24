package com.zds.user.controller.client;

import com.zds.biz.targetcheck.Authorization;
import com.zds.biz.vo.BaseResult;
import com.zds.biz.vo.IdRequest;
import com.zds.biz.vo.SelectResponse;
import com.zds.biz.vo.request.user.PwdChangeOldRequest;
import com.zds.biz.vo.request.user.PwdRenewRequest;
import com.zds.biz.vo.request.user.UserClientLoginRequest;
import com.zds.biz.vo.response.user.LoginUserResponse;
import com.zds.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "前台用户模块")
@RestController
@RequestMapping(value = "/client/user")
public class ClientUserController {

    @Autowired
    private UserService userService;

    @ApiOperation("用户登录")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public BaseResult<String> login(@RequestBody UserClientLoginRequest request) {
        return BaseResult.success("登录成功", userService.clientUserLogin(request));
    }

    @ApiOperation("用户登录(从业资格考核)")
    @RequestMapping(value = "/examine/login", method = RequestMethod.POST)
    public BaseResult<String> loginByExamine(@RequestBody UserClientLoginRequest request) {
        return BaseResult.success("登录成功", userService.clientUserLoginByExamine(request));
    }

    @Authorization
    @ApiOperation("退出登录")
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public BaseResult<String> logout() {
        return BaseResult.judgeOperate(userService.adminUserLogout());
    }

    @Authorization
    @ApiOperation("修改当前用户密码")
    @RequestMapping(value = "/pwd/change", method = RequestMethod.POST)
    public BaseResult<String> changeOldPassword(@RequestBody PwdChangeOldRequest request) {
        return BaseResult.judgeOperate(userService.changeOldPassword(request));
    }

    @ApiOperation("忘记密码")
    @RequestMapping(value = "/pwd/renew", method = RequestMethod.POST)
    public BaseResult<String> renewPassword(@RequestBody PwdRenewRequest request) {
        return BaseResult.judgeOperate(userService.renewPassword(request));
    }

    @Authorization
    @ApiOperation("查询当前登录用户信息")
    @RequestMapping(value = "/userInfo", method = RequestMethod.POST)
    public BaseResult<LoginUserResponse> getUserInfo() {
        return BaseResult.success(userService.getUserInfo());
    }

    @Authorization
    @ApiOperation("查询当前登录用户信息(从业资格考核)")
    @RequestMapping(value = "/examine/userInfo", method = RequestMethod.POST)
    public BaseResult<LoginUserResponse> getUserInfoByExamine() {
        return BaseResult.success(userService.getUserInfoByExamine());
    }

    @Authorization
    @ApiOperation(value = "用户下拉", notes = "id入参为单位ID")
    @RequestMapping(value = "/select", method = RequestMethod.POST)
    public BaseResult<List<SelectResponse>> userSelect(@RequestBody IdRequest request) {
        return BaseResult.success(userService.userSelect(request.getId()));
    }

    @Authorization
    @ApiOperation(value = "用户下拉(不过滤权限)", notes = "id入参为单位ID")
    @RequestMapping(value = "/select/not/filter", method = RequestMethod.POST)
    public BaseResult<List<SelectResponse>> userSelectNotFilter(@RequestBody IdRequest request) {
        return BaseResult.success(userService.userSelectNotFilter(request.getId()));
    }

    @Authorization
    @ApiOperation(value = "用户下拉(企业)", notes = "id入参为单位ID,不传即查所有")
    @RequestMapping(value = "/select/company", method = RequestMethod.POST)
    public BaseResult<List<SelectResponse>> userSelectByCompany(@RequestBody IdRequest request) {
        return BaseResult.success(userService.userSelectByCompany(request.getId()));
    }
}
