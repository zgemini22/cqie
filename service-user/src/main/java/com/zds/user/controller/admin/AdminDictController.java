package com.zds.user.controller.admin;

import com.zds.biz.targetcheck.Authorization;
import com.zds.biz.vo.BaseResult;
import com.zds.biz.vo.IdRequest;
import com.zds.biz.vo.request.user.DictGroupSaveRequest;
import com.zds.biz.vo.request.user.DictSaveRequest;
import com.zds.biz.vo.request.user.DictSelectRequest;
import com.zds.biz.vo.response.user.DictGroupSelectResponse;
import com.zds.biz.vo.response.user.DictSelectResponse;
import com.zds.user.service.DictService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
import java.util.Map;

@Api(tags = "后台数据字典模块")
@RestController
@RequestMapping(value = "/admin/dict")
public class AdminDictController {

    @Autowired
    private DictService dictService;

    @Authorization
    @ApiOperation(value = "字典分组列表")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public BaseResult<List<DictGroupSelectResponse>> findGroupList() {
        return BaseResult.success(dictService.findGroupList());
    }

    @Authorization
    @ApiOperation(value = "字典分组保存", notes = "字典分组ID不存在则新增,存在则修改分组名称")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public BaseResult<String> saveGroup(@RequestBody DictGroupSaveRequest request) {
        return BaseResult.judgeOperate(dictService.saveGroup(request));
    }

    @Authorization
    @ApiOperation(value = "字典分组启用/禁用")
    @RequestMapping(value = "/enabled", method = RequestMethod.POST)
    public BaseResult<String> enabledGroup(@RequestBody IdRequest request) {
        return BaseResult.judgeOperate(dictService.enabledGroup(request.getId()));
    }

    @Authorization
    @ApiOperation(value = "字典分组删除")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public BaseResult<String> deleteGroup(@RequestBody IdRequest request) {
        return BaseResult.judgeOperate(dictService.deleteGroup(request.getId()));
    }

    @Authorization
    @ApiOperation(value = "字典列表")
    @RequestMapping(value = "/group/list", method = RequestMethod.POST)
    public BaseResult<List<DictSelectResponse>> findListByGroup(@RequestBody DictSelectRequest request) {
        return BaseResult.success(dictService.findListByGroup(request));
    }

    @Authorization
    @ApiOperation(value = "字典保存", notes = "ID无则新增,有则修改")
    @RequestMapping(value = "/group/save", method = RequestMethod.POST)
    public BaseResult<String> saveByGroup(@RequestBody DictSaveRequest request) {
        return BaseResult.judgeOperate(dictService.saveByGroup(request));
    }

    @Authorization
    @ApiOperation(value = "字典启用/禁用")
    @RequestMapping(value = "/group/enabled", method = RequestMethod.POST)
    public BaseResult<String> enabledByGroup(@RequestBody IdRequest request) {
        return BaseResult.judgeOperate(dictService.enabledByGroup(request.getId()));
    }

    @Authorization
    @ApiOperation(value = "字典删除")
    @RequestMapping(value = "/group/delete", method = RequestMethod.POST)
    public BaseResult<String> deleteByGroup(@RequestBody IdRequest request) {
        return BaseResult.judgeOperate(dictService.deleteByGroup(request.getId()));
    }

    @ApiIgnore
    //@Authorization
    @ApiOperation(value = "查询指定字典分组范围的字典名称")
    @RequestMapping(value = "/group/map", method = RequestMethod.POST)
    public BaseResult<Map<String, String>> findDictMapByGroup(@RequestBody List<String> request) {
        return BaseResult.success(dictService.findDictMapByGroup(request));
    }
}
