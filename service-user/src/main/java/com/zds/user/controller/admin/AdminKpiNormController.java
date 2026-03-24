package com.zds.user.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zds.biz.targetcheck.Authorization;
import com.zds.biz.vo.BaseResult;
import com.zds.biz.vo.IdRequest;
import com.zds.biz.vo.SelectResponse;
import com.zds.biz.vo.request.user.*;
import com.zds.biz.vo.response.user.*;
import com.zds.user.service.KpiNormService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@Api(tags = "指标维护模块")
@RestController
@RequestMapping(value = "/admin/kpi")
public class AdminKpiNormController {

    @Autowired
    private KpiNormService kpiNormService;

    @Authorization
    @ApiOperation(value = "指标类别列表")
    @RequestMapping(value = "/classify/list", method = RequestMethod.POST)
    public BaseResult<List<AdminKpiNormListResponse>> list(@RequestBody AdminKpiNormListRequest request) {
        return BaseResult.success(kpiNormService.list(request));
    }

    @Authorization
    @ApiOperation(value = "指标类别下拉")
    @RequestMapping(value = "/classify/selectList", method = RequestMethod.POST)
    public BaseResult<List<AdminKpiNormListResponse>> selectList() {
        return BaseResult.success(kpiNormService.selectList());
    }

    @Authorization
    @ApiOperation(value = "指标类别详情")
    @RequestMapping(value = "/classify/detail", method = RequestMethod.POST)
    public BaseResult<AdminKpiNormDetailResponse> detail(@RequestBody IdRequest request) {
        return BaseResult.success(kpiNormService.detail(request.getId()));
    }

    @Authorization
    @ApiOperation("指标类别新增修改")
    @RequestMapping(value = "/classify/saveOrUpdate", method = RequestMethod.POST)
    public BaseResult<String> saveOrUpdate(@RequestBody AdminKpiNormSaveUpdateRequest request ){
        return BaseResult.judgeOperate(kpiNormService.saveOrUpdate(request));
    }

    @Authorization
    @ApiOperation(value = "指标类别删除")
    @RequestMapping(value = "/classify/delete", method = {RequestMethod.POST})
    public BaseResult<String> delete(@RequestBody IdRequest request) {
        return BaseResult.judgeOperate(kpiNormService.delete(request.getId()));
    }

    @Authorization
    @ApiOperation(value = "指标列表")
    @RequestMapping(value = "/norm/list", method = RequestMethod.POST)
    public BaseResult<IPage<AdminKpiNormInfoListResponse>> normPageList(@RequestBody AdminKpiNormInfoListRequest request) {
        return BaseResult.success(kpiNormService.normPageList(request));
    }

    @Authorization
    @ApiOperation(value = "指标下拉")
    @RequestMapping(value = "/norm/selectList", method = RequestMethod.POST)
    public BaseResult<List<KpiNormSelectResponse>> normSelect(@RequestBody AdminKpiSelectRequest request) {
        return BaseResult.success(kpiNormService.normSelect(request));
    }

    @Authorization
    @ApiOperation(value = "指标详情")
    @RequestMapping(value = "/norm/detail", method = RequestMethod.POST)
    public BaseResult<AdminKpiNormInfoDetailResponse> normDetail(@RequestBody IdRequest request) {
        return BaseResult.success(kpiNormService.normDetail(request.getId()));
    }

    @Authorization
    @ApiOperation("指标新增修改")
    @RequestMapping(value = "/norm/saveOrUpdate", method = RequestMethod.POST)
    public BaseResult<String> normSaveUpdate(@RequestBody AdminKpiNormInfoSaveRequest request ){
        return BaseResult.judgeOperate(kpiNormService.normSaveUpdate(request));
    }

    @Authorization
    @ApiOperation(value = "指标删除")
    @RequestMapping(value = "/norm/delete", method = {RequestMethod.POST})
    public BaseResult<String> normDelete(@RequestBody IdRequest request) {
        return BaseResult.judgeOperate(kpiNormService.normDelete(request.getId()));
    }

    @Authorization
    @ApiOperation(value = "指标状态修改")
    @RequestMapping(value = "/norm/statusUpdate", method = {RequestMethod.POST})
    public BaseResult<String> normStatusUpdate(@RequestBody AdminKpiNormStatusRequest request) {
        return BaseResult.judgeOperate(kpiNormService.normStatusUpdate(request));
    }

    @Authorization
    @ApiOperation("数据源分页列表")
    @RequestMapping(value = "/data/list", method = RequestMethod.POST)
    public BaseResult<IPage<AdminKpiDataListResponse>> dataList(@RequestBody AdminKpiDataListRequest request) {
        return BaseResult.success(kpiNormService.dataList(request));
    }

    @Authorization
    @ApiOperation("数据源下拉")
    @RequestMapping(value = "/data/select", method = RequestMethod.POST)
    public BaseResult<List<SelectResponse>> dataSelect() {
        return BaseResult.success(kpiNormService.dataSelect());
    }

    @Authorization
    @ApiOperation("运算符下拉")
    @RequestMapping(value = "/operator/select", method = RequestMethod.POST)
    public BaseResult<List<SelectResponse>> operatorSelect() {
        return BaseResult.success(kpiNormService.operatorSelect());
    }

    @Authorization
    @ApiOperation("数据源详情")
    @RequestMapping(value = "/data/detail", method = RequestMethod.POST)
    public BaseResult<AdminKpiDataListResponse> dataDetail(@RequestBody IdRequest request) {
        return BaseResult.success(kpiNormService.dataDetail(request.getId()));
    }

    @ApiIgnore
    @Authorization
    @ApiOperation("数据源删除")
    @RequestMapping(value = "/data/delete", method = {RequestMethod.POST})
    public BaseResult<String> dataDelete(@RequestBody IdRequest request) {
        return BaseResult.judgeOperate(kpiNormService.dataDelete(request.getId()));
    }


    @Authorization
    @ApiOperation("数据源停用/启用")
    @RequestMapping(value = "/data/start/stop", method = {RequestMethod.POST})
    public BaseResult<String> dataStartStop(@RequestBody AdminKpiDataStopRequest request) {
        return BaseResult.judgeOperate(kpiNormService.dataStartStop(request));
    }

    @Authorization
    @ApiOperation("数据源明细分页列表")
    @RequestMapping(value = "/data/detail/list", method = RequestMethod.POST)
    public BaseResult<IPage<AdminKpiDetailListResponse>> dataDetailList(@RequestBody AdminKpiDetailListRequest request) {
        return BaseResult.success(kpiNormService.dataDetailList(request));
    }

    @Authorization
    @ApiOperation("标杆值维护管理分页列表")
    @RequestMapping(value = "/benchmark/info/list", method = RequestMethod.POST)
    public BaseResult<IPage<AdminKpiBenchmarkInfoListResponse>> dataDetailList(@RequestBody AdminKpiBenchmarkInfoListRequest request) {
        return BaseResult.success(kpiNormService.benchmarkInfoList(request));
    }


    @Authorization
    @ApiOperation(value = "标杆值维护管理保存")
    @RequestMapping(value = "/benchmark/info/save", method = {RequestMethod.POST})
    public BaseResult<String> benchmarkInfoSave(@RequestBody AdminKpiBenchmarkInfoSaveRequest request) {
        return BaseResult.judgeOperate(kpiNormService.benchmarkInfoSave(request));
    }

    @Authorization
    @ApiOperation("标杆值维护管理查看")
    @RequestMapping(value = "/benchmark/info/detail", method = RequestMethod.POST)
    public BaseResult<AdminKpiBenchmarkInfoDetailResponse> benchmarkInfoDetail(@RequestBody IdRequest request) {
        return BaseResult.success(kpiNormService.benchmarkInfoDetail(request));
    }

    @Authorization
    @ApiOperation("标杆值维护管理停用/启用")
    @RequestMapping(value = "/benchmark/info/start/stop", method = RequestMethod.POST)
    public BaseResult<String> benchmarkInfoStartStop(@RequestBody AdminKpiBenchmarkInfoStopRequest request) {
        return BaseResult.judgeOperate(kpiNormService.benchmarkInfoStartStop(request));
    }

    @Authorization
    @ApiOperation("标杆值维护管理删除")
    @RequestMapping(value = "/benchmark/info/delete", method = RequestMethod.POST)
    public BaseResult<String> benchmarkInfoDelete(@RequestBody IdRequest request) {
        return BaseResult.judgeOperate(kpiNormService.benchmarkInfoDelete(request));
    }

}
