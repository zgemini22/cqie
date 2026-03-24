package com.zds.user.controller.admin;

import com.zds.biz.targetcheck.Authorization;
import com.zds.biz.vo.BaseResult;
import com.zds.biz.vo.request.user.DemoAddRequest;
import com.zds.user.service.DemoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDateTime;

@Api(tags = "demo测试模块")
@RestController
@RequestMapping(value = "/demo")
public class DemoController {

    @Resource
    private DemoService demoService;

//    @Authorization
    @ApiOperation("打印输出")
    @GetMapping(value = "/print")
    public BaseResult print(){
        return BaseResult.success("time=" + LocalDateTime.now());
    }

    @Authorization
    @ApiOperation("保存demo")
    @PostMapping(value = "/add")
    public BaseResult add(@RequestBody DemoAddRequest request){
        return BaseResult.judgeOperate(demoService.add(request));
    }

}
