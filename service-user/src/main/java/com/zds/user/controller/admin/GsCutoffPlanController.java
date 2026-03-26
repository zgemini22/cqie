package com.zds.user.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zds.biz.targetcheck.Authorization;
import com.zds.biz.vo.BaseResult;
import com.zds.biz.vo.IdRequest;
import com.zds.biz.vo.request.user.GsCutoffPlanRequest;
import com.zds.biz.vo.response.user.GsCutoffPlanDetailResponse;
import com.zds.biz.vo.response.user.GsCutoffPlanResponse;
import com.zds.user.service.GsCutoffPlanService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "保供管理-停气计划")
@RestController
@RequestMapping("/cutoff/plan")
public class GsCutoffPlanController {

    @Autowired
    private GsCutoffPlanService gsCutoffPlanService;

    @Authorization
    @ApiOperation("保存停气计划")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public BaseResult<String> save(@RequestBody GsCutoffPlanRequest request) {
        return BaseResult.judgeOperate(gsCutoffPlanService.save(request));
    }

    @Authorization
    @ApiOperation("修改停气计划")
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public BaseResult<String> edit(@RequestBody GsCutoffPlanRequest request) {
        return BaseResult.judgeOperate(gsCutoffPlanService.edit(request));
    }

    @Authorization
    @ApiOperation("删除停气计划")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public BaseResult<String> deleteById(@RequestBody IdRequest request) {
        return BaseResult.judgeOperate(gsCutoffPlanService.deleteById(request.getId()));
    }

    @Authorization
    @ApiOperation("停气计划列表")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public BaseResult<IPage<GsCutoffPlanResponse>> list(@RequestBody GsCutoffPlanRequest request) {
        return BaseResult.success(gsCutoffPlanService.list(request));
    }

    @Authorization
    @ApiOperation("停气计划详情")
    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    public BaseResult<GsCutoffPlanDetailResponse> detail(@RequestBody IdRequest request) {
        return BaseResult.success(gsCutoffPlanService.detail(request.getId()));
    }
}
