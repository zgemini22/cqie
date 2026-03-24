package com.zds.user.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zds.biz.targetcheck.Authorization;
import com.zds.biz.vo.BaseResult;
import com.zds.biz.vo.IdRequest;
import com.zds.biz.vo.request.user.TpcConstructionRequest;
import com.zds.biz.vo.response.user.TpcConstructionDetailResponse;
import com.zds.biz.vo.response.user.TpcConstructionResponse;
import com.zds.user.service.TpcConstructionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "三方施工模块-三方施工")
@RestController
@RequestMapping("/tpc/construction")
public class TpcConstructionController {

    @Autowired
    private TpcConstructionService tpcConstructionService;

    @Authorization
    @ApiOperation("保存三方施工")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public BaseResult<String> save(@RequestBody TpcConstructionRequest request) {
        return BaseResult.judgeOperate(tpcConstructionService.save(request));
    }

    @Authorization
    @ApiOperation("修改三方施工")
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public BaseResult<String> edit(@RequestBody TpcConstructionRequest request) {
        return BaseResult.judgeOperate(tpcConstructionService.edit(request));
    }

    @Authorization
    @ApiOperation("删除三方施工")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public BaseResult<String> deleteById(@RequestBody IdRequest request) {
        return BaseResult.judgeOperate(tpcConstructionService.deleteById(request.getId()));
    }

    @Authorization
    @ApiOperation("三方施工列表")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public BaseResult<IPage<TpcConstructionResponse>> list(@RequestBody TpcConstructionRequest request) {
        return BaseResult.success(tpcConstructionService.list(request));
    }

    @Authorization
    @ApiOperation("三方施工详情")
    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    public BaseResult<TpcConstructionDetailResponse> detail(@RequestBody IdRequest request) {
        return BaseResult.success(tpcConstructionService.detail(request.getId()));
    }
}
