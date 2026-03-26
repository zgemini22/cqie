package com.zds.user.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zds.biz.targetcheck.Authorization;
import com.zds.biz.vo.BaseResult;
import com.zds.biz.vo.IdRequest;
import com.zds.biz.vo.SelectResponse;
import com.zds.biz.vo.request.user.*;
import com.zds.biz.vo.response.user.LoginUserResponse;
import com.zds.biz.vo.response.user.UserDetailResponse;
import com.zds.biz.vo.response.user.UserResponse;
import com.zds.user.service.TranslucentKaptchaService;
import com.zds.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@Api(tags = "后台用户模块")
@RestController
@RequestMapping(value = "/admin/user")
public class AdminUserController {

    @Autowired
    private UserService userService;

    @Autowired
    private TranslucentKaptchaService translucentKaptchaService;

    @ApiOperation("用户登录")
    @RequestMapping(value = "/system/login", method = RequestMethod.POST)
    public BaseResult<String> login(@RequestBody UserLoginRequest request) {
        return BaseResult.success("登录成功", userService.adminUserLogin(request));
    }

    @ApiOperation("用户登录(无需验证码)")
    @RequestMapping(value = "/login/uncode", method = RequestMethod.POST)
    public BaseResult<String> loginUncode(@RequestBody UserLoginUncodeRequest request) {
        return BaseResult.success("登录成功", userService.adminUserLoginUnCode(request));
    }

    @ApiIgnore
    @ApiOperation("组织ID查询该组织第一个用户")
    @RequestMapping(value = "/syncData/findFirstUserLoginNameByOrgId", method = RequestMethod.POST)
    public BaseResult<String> findFirstUserLoginNameByOrgId(@RequestBody SyncDataLoginRequest request) {
        return BaseResult.success(userService.findFirstUserLoginNameByOrgId(request));
    }

    @ApiIgnore
    @ApiOperation("同步数据-模拟登录获取token")
    @RequestMapping(value = "/syncData/mockLogin", method = RequestMethod.POST)
    public BaseResult<String> mockLogin(@RequestBody SyncDataLoginRequest request) {
        return BaseResult.success(userService.mockLogin(request));
    }

    @ApiOperation("用户登录(从业资格考核)")
    @RequestMapping(value = "/examine/login", method = RequestMethod.POST)
    public BaseResult<String> loginByExamine(@RequestBody UserClientLoginRequest request) {
        return BaseResult.success("登录成功", userService.adminUserLoginByExamine(request));
    }

    @ApiOperation("用户登录(抄表信息化)")
    @RequestMapping(value = "/meter/login", method = RequestMethod.POST)
    public BaseResult<String> loginByMeter(@RequestBody UserClientLoginRequest request) {
        return BaseResult.success("登录成功", userService.adminUserLoginByMeter(request));
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

    @ApiIgnore
    @Authorization
    @ApiOperation("修改当前用户密码")
    @RequestMapping(value = "/pwd/change/phone", method = RequestMethod.POST)
    public BaseResult<String> changePassword(@RequestBody PwdChangeRequest request) {
        return BaseResult.judgeOperate(userService.changePassword(request));
    }

    @ApiOperation("忘记密码")
    @RequestMapping(value = "/pwd/renew", method = RequestMethod.POST)
    public BaseResult<String> renewPassword(@RequestBody PwdRenewRequest request) {
        return BaseResult.judgeOperate(userService.renewPassword(request));
    }

    @ApiIgnore
    @Authorization
    @ApiOperation("更新用户状态")
    @RequestMapping(value = "/status", method = RequestMethod.POST)
    public BaseResult<String> updateStatus(@RequestBody UserStatusRequest request) {
        return BaseResult.judgeOperate(userService.updateStatus(request));
    }

    @Authorization
    @ApiOperation("查询当前登录用户信息")
    @RequestMapping(value = "/system/userInfo", method = RequestMethod.POST)
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
    @ApiOperation("查询当前登录用户信息(抄表信息化)")
    @RequestMapping(value = "/meter/userInfo", method = RequestMethod.POST)
    public BaseResult<LoginUserResponse> getUserInfoByMeter() {
        return BaseResult.success(userService.getUserInfoByMeter());
    }

    @Authorization
    @ApiOperation("删除用户")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public BaseResult<String> deleteUser(@RequestBody IdRequest request) {
        return BaseResult.judgeOperate(userService.deleteUser(request.getId()));
    }

    @ApiOperation("发送验证码")
    @RequestMapping(value = "/send/code", method = RequestMethod.POST)
    public BaseResult<String> sendCode(@RequestBody SendCodeRequest request) {
        return BaseResult.judgeOperate(userService.sendCode(request));
    }

    @ApiIgnore
    @Authorization
    @ApiOperation("修改绑定手机号")
    @RequestMapping(value = "/update/phone", method = RequestMethod.POST)
    public BaseResult<String> updatePhone(@RequestBody PhoneUpdateRequest request) {
        return BaseResult.judgeOperate(userService.updatePhone(request));
    }

    @ApiOperation(value = "获取登录验证码", notes = "key存在Response Headers的rightCode")
    @RequestMapping(value = "/login/code", method = RequestMethod.GET)
    public void loginCode(HttpServletResponse response) {translucentKaptchaService.getLoginCode(response);}

    @Authorization
    @ApiOperation("用户列表")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public BaseResult<IPage<UserResponse>> findUserList(@RequestBody UserFindRequest request) {
        return BaseResult.success(userService.findUserList(request));
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

    @Authorization
    @ApiOperation("用户详情")
    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    public BaseResult<UserDetailResponse> findUserDetail(@RequestBody IdRequest request) {
        return BaseResult.success(userService.findUserDetail(request.getId()));
    }

    @Authorization
    @ApiOperation("保存用户")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public BaseResult<String> saveUser(@RequestBody UserSaveRequest request) {
        return BaseResult.judgeOperate(userService.saveUser(request));
    }

    @ApiIgnore
    @Authorization
    @ApiOperation(value = "查询指定用户范围的用户名称")
    @RequestMapping(value = "/map", method = RequestMethod.POST)
    public BaseResult<Map<Long, String>> findUserMapById(@RequestBody List<Long> request) {
        return BaseResult.success(userService.findUserMapById(request));
    }

    @ApiIgnore
    @ApiOperation(value = "查询所有用户信息")
    @RequestMapping(value = "/info/all", method = RequestMethod.POST)
    public BaseResult<List<UserResponse>> findAllUserInfo(@RequestBody String secretKey) {
        return BaseResult.success(userService.findAllUserInfo(secretKey));
    }

    @ApiIgnore
    @Authorization
    @ApiOperation(value = "用户名称模糊查询用户ID集合")
    @RequestMapping(value = "/name/like/list", method = RequestMethod.POST)
    public BaseResult<List<Long>> findUserListByName(@RequestBody String name) {
        return BaseResult.success(userService.findUserListByName(name));
    }

//    @ApiIgnore
    @Authorization
    @ApiOperation(value = "批量修改用户密码")
    @RequestMapping(value = "/test/update/all", method = RequestMethod.POST)
    public BaseResult<String> testUpdateAllPwd(@RequestBody UserLoginRequest request) {
        return BaseResult.judgeOperate(userService.testUpdateAllPwd(request));
    }

    @Authorization
    @ApiOperation("重置指定用户密码")
    @RequestMapping(value = "/select/pwd/change", method = RequestMethod.POST)
    public BaseResult<String> changePasswordByUser(@RequestBody PwdChangeByUserRequest request) {
        return BaseResult.judgeOperate(userService.changePasswordByUser(request));
    }

    @Authorization
    @ApiOperation("判断用户密码是否需要更新")
    @RequestMapping(value = "/need/pwd/change", method = RequestMethod.POST)
    public BaseResult<Boolean> needChangePassword() {
        return BaseResult.success("success", userService.needChangePassword());
    }
}
