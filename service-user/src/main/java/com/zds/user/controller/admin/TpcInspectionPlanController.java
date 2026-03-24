package com.zds.user.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zds.biz.targetcheck.Authorization;
import com.zds.biz.vo.BaseResult;
import com.zds.biz.vo.IdRequest;
import com.zds.biz.vo.request.user.TpcInspectionPlanRequest;
import com.zds.biz.vo.response.user.TpcInspectionPlanDetailResponse;
import com.zds.biz.vo.response.user.TpcInspectionPlanResponse;
import com.zds.user.service.TpcInspectionPlanService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "三方施工模块-巡检计划")
@RestController
@RequestMapping("/tpc/plan")
public class TpcInspectionPlanController {

    @Autowired
    private TpcInspectionPlanService tpcInspectionPlanService;

    @Authorization
    @ApiOperation("保存巡检计划")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public BaseResult<String> save(@RequestBody TpcInspectionPlanRequest request) {
        return BaseResult.judgeOperate(tpcInspectionPlanService.save(request));
    }

    @Authorization
    @ApiOperation("修改巡检计划")
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public BaseResult<String> edit(@RequestBody TpcInspectionPlanRequest request) {
        return BaseResult.judgeOperate(tpcInspectionPlanService.edit(request));
    }

    @Authorization
    @ApiOperation("删除巡检计划")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public BaseResult<String> deleteById(@RequestBody IdRequest request) {
        return BaseResult.judgeOperate(tpcInspectionPlanService.deleteById(request.getId()));
    }

    @Authorization
    @ApiOperation("巡检计划列表")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public BaseResult<IPage<TpcInspectionPlanResponse>> list(@RequestBody TpcInspectionPlanRequest request) {
        return BaseResult.success(tpcInspectionPlanService.list(request));
    }

    @Authorization
    @ApiOperation("巡检计划详情")
    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    public BaseResult<TpcInspectionPlanDetailResponse> detail(@RequestBody IdRequest request) {
        return BaseResult.success(tpcInspectionPlanService.detail(request.getId()));
    }
}
