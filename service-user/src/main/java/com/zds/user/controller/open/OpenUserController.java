package com.zds.user.controller.open;

import com.zds.biz.vo.BaseResult;
import com.zds.biz.vo.request.user.OpenCheckTokenRequest;
import com.zds.biz.vo.request.user.OpenLoginRequest;
import com.zds.biz.vo.response.user.OpenCheckTokenResponse;
import com.zds.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Api(tags = "开放-用户认证")
@RestController
@RequestMapping(value = "/open/org/user")
public class OpenUserController {

    @Resource
    private UserService userService;

    @ApiOperation(value = "获取认证token")
    @RequestMapping(value = "/login/token", method = RequestMethod.POST)
    public BaseResult<String> openLoginToken(@RequestBody OpenLoginRequest request) {
        return BaseResult.success("success", userService.openLoginToken(request));
    }

    @ApiOperation(value = "校验token有效性")
    @RequestMapping(value = "/check/token", method = RequestMethod.POST)
    public BaseResult<OpenCheckTokenResponse> openCheckToken(@RequestBody OpenCheckTokenRequest request) {
        return BaseResult.success("success", userService.openCheckToken(request));
    }
}
