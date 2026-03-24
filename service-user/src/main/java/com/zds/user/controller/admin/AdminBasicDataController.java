package com.zds.user.controller.admin;

import com.zds.biz.targetcheck.Authorization;
import com.zds.biz.vo.BaseResult;
import com.zds.biz.vo.request.user.BasicDataRequest;
import com.zds.biz.vo.request.user.BasicSaveRequest;
import com.zds.biz.vo.response.user.BasicDataResponse;
import com.zds.user.service.BasicDataService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@Api(tags = "后台基础数据模块")
@RestController
@RequestMapping(value = "/admin/basic")
public class AdminBasicDataController {

    @Autowired
    private BasicDataService basicDataService;

    @ApiIgnore
    @ApiOperation("根据数据标识查询基础数据")
    @RequestMapping(value = "/select/key", method = RequestMethod.POST)
    public BaseResult<BasicDataResponse> selectByKey(@RequestBody BasicDataRequest request){
        return BaseResult.success(basicDataService.selectByKey(request));
    }

    @Authorization
    @ApiOperation(value = "基础数据列表")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public BaseResult<List<BasicDataResponse>> findList() {
        return BaseResult.success(basicDataService.findList());
    }

    @Authorization
    @ApiOperation(value = "修改基础数据")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public BaseResult<String> save(@RequestBody BasicSaveRequest request) {
        return BaseResult.judgeOperate(basicDataService.save(request));
    }
}
