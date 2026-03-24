package com.zds.user.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zds.biz.targetcheck.Authorization;
import com.zds.biz.vo.BaseResult;
import com.zds.biz.vo.IdRequest;
import com.zds.biz.vo.request.user.TpcInspectionContentRequest;
import com.zds.biz.vo.response.user.TpcInspectionContentDetailResponse;
import com.zds.biz.vo.response.user.TpcInspectionContentResponse;
import com.zds.user.service.TpcInspectionContentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "三方施工模块-巡检内容")
@RestController
@RequestMapping("/tpc/content")
public class TpcInspectionContentController {

    @Autowired
    private TpcInspectionContentService tpcInspectionContentService;

    @Authorization
    @ApiOperation("保存巡检内容")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public BaseResult<String> save(@RequestBody TpcInspectionContentRequest request) {
        return BaseResult.judgeOperate(tpcInspectionContentService.save(request));
    }

    @Authorization
    @ApiOperation("修改巡检内容")
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public BaseResult<String> edit(@RequestBody TpcInspectionContentRequest request) {
        return BaseResult.judgeOperate(tpcInspectionContentService.edit(request));
    }

    @Authorization
    @ApiOperation("删除巡检内容")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public BaseResult<String> deleteById(@RequestBody IdRequest request) {
        return BaseResult.judgeOperate(tpcInspectionContentService.deleteById(request.getId()));
    }

    @Authorization
    @ApiOperation("巡检内容列表")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public BaseResult<IPage<TpcInspectionContentResponse>> list(@RequestBody TpcInspectionContentRequest request) {
        return BaseResult.success(tpcInspectionContentService.list(request));
    }

    @Authorization
    @ApiOperation("巡检内容详情")
    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    public BaseResult<TpcInspectionContentDetailResponse> detail(@RequestBody IdRequest request) {
        return BaseResult.success(tpcInspectionContentService.detail(request.getId()));
    }
}
