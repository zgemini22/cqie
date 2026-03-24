package com.zds.user.controller.bi;

import com.zds.biz.targetcheck.Authorization;
import com.zds.biz.vo.BaseResult;
import com.zds.user.service.TodoListService;
import com.zds.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@Api(tags = "大屏用户模块")
@RestController
@RequestMapping(value = "/bi/user")
public class BiUserController {

    @Autowired
    private UserService userService;

    @Autowired
    private TodoListService todoListService;

    @ApiIgnore
    @ApiOperation(value = "大屏模拟登录")
    @RequestMapping(value = "/simulated/login", method = RequestMethod.POST)
    public BaseResult<String> simulatedLogin() {
        return BaseResult.success("登录成功", userService.simulatedLogin());
    }


    @Authorization
    @ApiOperation(value = "大屏token校验")
    @RequestMapping(value = "/check/token", method = RequestMethod.POST)
    public BaseResult<String> biCheckToken() {
        return BaseResult.success("success", "success");
    }

}
