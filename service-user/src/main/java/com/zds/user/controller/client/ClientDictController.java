package com.zds.user.controller.client;

import com.zds.biz.targetcheck.Authorization;
import com.zds.biz.vo.BaseResult;
import com.zds.biz.vo.request.user.DictSelectRequest;
import com.zds.biz.vo.response.user.DictSelectResponse;
import com.zds.user.service.DictService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "前台数据字典模块")
@RestController
@RequestMapping(value = "/client/dict")
public class ClientDictController {

    @Autowired
    private DictService dictService;

    @Authorization
    @ApiOperation(value = "字典列表")
    @RequestMapping(value = "/group/list", method = RequestMethod.POST)
    public BaseResult<List<DictSelectResponse>> findListByGroup(@RequestBody DictSelectRequest request) {
        return BaseResult.success(dictService.findListByGroup(request));
    }
}
