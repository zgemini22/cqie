package com.zds.user.controller.client;

import com.zds.biz.targetcheck.Authorization;
import com.zds.biz.vo.BaseResult;
import com.zds.biz.vo.request.user.OrgSelectRequest;
import com.zds.biz.vo.response.user.OrgResponse;
import com.zds.user.service.OrgService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "前台单位模块")
@RestController
@RequestMapping(value = "/client/org")
public class ClientOrgController {

    @Autowired
    private OrgService orgService;

    @Authorization
    @ApiOperation("单位下拉")
    @RequestMapping(value = "/select", method = RequestMethod.POST)
    public BaseResult<List<OrgResponse>> findSelect(@RequestBody OrgSelectRequest request){
        return BaseResult.success(orgService.findSelect(request));
    }

    @Authorization
    @ApiOperation(value = "当前用户单位派系下拉", notes = "查询当前用户的单位以及上下级所有单位")
    @RequestMapping(value = "/factions/select", method = RequestMethod.POST)
    public BaseResult<List<OrgResponse>> findFactionsSelectByUser(){
        return BaseResult.success(orgService.findFactionsSelectByUser());
    }

    @Authorization
    @ApiOperation(value = "企业单位下拉", notes = "只查询企业单位,接口自行判断当前用户是属于政府还是企业")
    @RequestMapping(value = "/company/select", method = RequestMethod.POST)
    public BaseResult<List<OrgResponse>> findCompanySelect(){
        return BaseResult.success(orgService.findCompanySelect());
    }

    @Authorization
    @ApiOperation(value = "单位下拉(默认过滤)", notes = "政府查看所有单位,企业查看自身及下属单位")
    @RequestMapping(value = "/default/select", method = RequestMethod.POST)
    public BaseResult<List<OrgResponse>> findDefaultSelect(){
        return BaseResult.success(orgService.findDefaultSelect());
    }

    @Authorization
    @ApiOperation("单位下拉(不过滤权限,过滤政府单位有用户的)")
    @RequestMapping(value = "/select/hav/user", method = RequestMethod.POST)
    public BaseResult<List<OrgResponse>> orgSelectHavUser(){
        return BaseResult.success(orgService.orgSelectHavUser());
    }
}
