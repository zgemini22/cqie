package com.zds.user.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zds.biz.targetcheck.Authorization;
import com.zds.biz.vo.BaseResult;
import com.zds.biz.vo.IdRequest;
import com.zds.biz.vo.request.user.GsCutoffOperationDetailRequest;
import com.zds.biz.vo.request.user.GsCutoffOperationRequest;
import com.zds.biz.vo.response.user.GsCutoffOperationDetailResponse;
import com.zds.biz.vo.response.user.GsCutoffOperationResponse;
import com.zds.user.service.GsCutoffOperationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "保供管理-停气管理")
@RestController
@RequestMapping("/cutoff/operation")
public class GsCutoffOperationController {

    @Autowired
    private GsCutoffOperationService gsCutoffOperationService;

    @Authorization
    @ApiOperation("保存停气作业")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public BaseResult<String> save(@RequestBody GsCutoffOperationRequest request) {
        return BaseResult.judgeOperate(gsCutoffOperationService.save(request));
    }

    @Authorization
    @ApiOperation("修改停气作业")
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public BaseResult<String> edit(@RequestBody GsCutoffOperationRequest request) {
        return BaseResult.judgeOperate(gsCutoffOperationService.edit(request));
    }

    @Authorization
    @ApiOperation("删除停气作业")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public BaseResult<String> deleteById(@RequestBody IdRequest request) {
        return BaseResult.judgeOperate(gsCutoffOperationService.deleteById(request.getId()));
    }

    @Authorization
    @ApiOperation("停气作业列表")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public BaseResult<IPage<GsCutoffOperationResponse>> list(@RequestBody GsCutoffOperationRequest request) {
        return BaseResult.success(gsCutoffOperationService.list(request));
    }

    @Authorization
    @ApiOperation("停气作业详情")
    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    public BaseResult<GsCutoffOperationDetailResponse> detail(@RequestBody GsCutoffOperationDetailRequest request) {
        return BaseResult.success(gsCutoffOperationService.detail(request.getId(), request.getDetailAddress()));
    }
}
