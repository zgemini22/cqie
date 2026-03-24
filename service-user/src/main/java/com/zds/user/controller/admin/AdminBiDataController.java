package com.zds.user.controller.admin;

import com.zds.biz.targetcheck.Authorization;
import com.zds.biz.vo.BaseResult;
import com.zds.biz.vo.IdRequest;
import com.zds.biz.vo.request.user.*;
import com.zds.biz.vo.response.user.BiDataExampleDetailResponse;
import com.zds.biz.vo.response.user.BiDataExampleResponse;
import com.zds.biz.vo.response.user.BiDataListResponse;
import com.zds.biz.vo.response.user.BiDataModelResponse;
import com.zds.user.po.TblBiDataAll;
import com.zds.user.po.TblBiDataQy;
import com.zds.user.po.TblBiDataZj;
import com.zds.user.service.BiDataService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@Api(tags = "大屏填报模块")
@RestController
@RequestMapping(value = "/admin/mock/bi")
public class AdminBiDataController {

    @Autowired
    private BiDataService biDataService;

    @Authorization
    @ApiOperation("数据类型列表")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public BaseResult<List<BiDataListResponse>> findList() {
        return BaseResult.success(biDataService.findList());
    }

    @Authorization
    @ApiOperation("数据类型保存")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public BaseResult<String> save(@RequestBody BiDataSaveRequest request) {
        return BaseResult.judgeOperate(biDataService.save(request));
    }

    @Authorization
    @ApiOperation("数据类型删除")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public BaseResult<String> delete(@RequestBody IdRequest request) {
        return BaseResult.judgeOperate(biDataService.delete(request));
    }

    @Authorization
    @ApiOperation("数据类型-模型查询")
    @RequestMapping(value = "/model/find", method = RequestMethod.POST)
    public BaseResult<BiDataModelResponse> findModel(@RequestBody IdRequest request) {
        return BaseResult.success(biDataService.findModel(request));
    }

    @Authorization
    @ApiOperation("数据类型-模型保存")
    @RequestMapping(value = "/model/save", method = RequestMethod.POST)
    public BaseResult<String> saveModel(@RequestBody BiDataModelSaveRequest request) {
        return BaseResult.judgeOperate(biDataService.saveModel(request));
    }

    @Authorization
    @ApiOperation("数据类型-实例查询")
    @RequestMapping(value = "/example/find", method = RequestMethod.POST)
    public BaseResult<BiDataExampleResponse> findExample(@RequestBody IdRequest request) {
        return BaseResult.success(biDataService.findExample(request));
    }

    @Authorization
    @ApiOperation("数据类型-实例保存")
    @RequestMapping(value = "/example/save", method = RequestMethod.POST)
    public BaseResult<String> saveExample(@RequestBody BiDataExampleSaveRequest request) {
        return BaseResult.judgeOperate(biDataService.saveExample(request));
    }

    @Authorization
    @ApiOperation("查询指定数据实例")
    @RequestMapping(value = "/select/example/key", method = RequestMethod.POST)
    public BaseResult<BiDataExampleDetailResponse> selectExampleByKey(@RequestBody BiDataExampleFindRequest request) {
        return BaseResult.success(biDataService.selectExampleByKey(request));
    }

    @Authorization
    @ApiOperation("全部指标的数据(按年月季）")
    @RequestMapping(value = "/all/list", method = RequestMethod.POST)
    public BaseResult<List<TblBiDataAll>> allList(@RequestBody BiDataAllsRequest request) {
        return BaseResult.success(biDataService.biDataAlls(request));
    }

    @Authorization
    @ApiOperation("按城燃企业")
    @RequestMapping(value = "/qy/list", method = RequestMethod.POST)
    public BaseResult<List<TblBiDataQy>> qyList(@RequestBody BiDataQysRequest request) {
        return BaseResult.success(biDataService.biDataQys(request));
    }

    @Authorization
    @ApiOperation("按镇街")
    @RequestMapping(value = "/zj/list", method = RequestMethod.POST)
    public BaseResult<List<TblBiDataZj>> zjList(@RequestBody BiDataZjsRequest request) {
        return BaseResult.success(biDataService.biDataZjs(request));
    }

    /**
     * 按月新增决策处置指标细项
     */
    @ApiIgnore
    @ApiOperation(value = "按月新增决策处置指标细项")
    @RequestMapping(value = "/jccz/insert", method = RequestMethod.POST)
    public BaseResult<String> jcczInsert(@RequestBody String time){
        return BaseResult.judgeOperate(biDataService.jcczInsert(time));
    };
}
