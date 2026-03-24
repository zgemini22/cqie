package com.zds.user.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zds.biz.vo.BaseResult;
import com.zds.biz.vo.LogResponse;
import com.zds.biz.vo.LogVo;
import com.zds.biz.vo.PageRequest;
import com.zds.user.service.LogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;


@Api(tags = "接口调用日志模块")
@RestController
@RequestMapping(value = "/inner/log")
public class AdminLogController {

    @Autowired
    private LogService logService;

    @ApiOperation(value = "接口调用日志分页查询")
    @RequestMapping(value = "/page", method = RequestMethod.POST)
    public BaseResult<IPage<LogResponse>> page(@RequestBody PageRequest request) {
        return BaseResult.success(logService.page(request));
    }

    @ApiIgnore
    @ApiOperation(value = "接口调用日志保存")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public BaseResult<Long> save(@RequestBody LogVo log) {
        return BaseResult.success(logService.save(log));
    }


    @ApiIgnore
    @ApiOperation(value = "接口调用日志修改")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public BaseResult<String> update(@RequestBody LogVo log) {
        return BaseResult.judgeOperate(logService.update(log));
    }
}
