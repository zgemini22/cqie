package com.zds.user.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zds.biz.targetcheck.Authorization;
import com.zds.biz.vo.BaseResult;
import com.zds.biz.vo.IdRequest;
import com.zds.biz.vo.request.user.TblOrganizationBusinessRequest;
import com.zds.biz.vo.response.user.TblOrganizationBusinessResponse;
import com.zds.user.service.TblOrganizationBusinessService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "企业管理模块-营业厅")
@RestController
@RequestMapping("/tbl/business")
public class TblOrganizationBusinessController {

    @Autowired
    private TblOrganizationBusinessService tblOrganizationBusinessService;

    @Authorization
    @ApiOperation("保存营业厅")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public BaseResult<String> save(@RequestBody TblOrganizationBusinessRequest request) {
        return BaseResult.judgeOperate(tblOrganizationBusinessService.save(request));
    }

    @Authorization
    @ApiOperation("修改营业厅")
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public BaseResult<String> edit(@RequestBody TblOrganizationBusinessRequest request) {
        return BaseResult.judgeOperate(tblOrganizationBusinessService.edit(request));
    }

    @Authorization
    @ApiOperation("删除营业厅")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public BaseResult<String> deleteById(@RequestBody IdRequest request) {
        return BaseResult.judgeOperate(tblOrganizationBusinessService.deleteById(request.getId()));
    }

    @Authorization
    @ApiOperation("营业厅列表")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public BaseResult<IPage<TblOrganizationBusinessResponse>> list(@RequestBody TblOrganizationBusinessRequest request) {
        return BaseResult.success(tblOrganizationBusinessService.list(request));
    }

    @Authorization
    @ApiOperation("营业厅详情")
    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    public BaseResult<TblOrganizationBusinessResponse> detail(@RequestBody IdRequest request) {
        return BaseResult.success(tblOrganizationBusinessService.detail(request.getId()));
    }

}