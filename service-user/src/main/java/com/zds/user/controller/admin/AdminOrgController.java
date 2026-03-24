package com.zds.user.controller.admin;

import com.zds.biz.targetcheck.Authorization;
import com.zds.biz.vo.BaseResult;
import com.zds.biz.vo.IdRequest;
import com.zds.biz.vo.request.user.OrgSaveRequest;
import com.zds.biz.vo.request.user.OrgSelectRequest;
import com.zds.biz.vo.request.user.OrgStatusRequest;
import com.zds.biz.vo.response.user.OrgDetailResponse;
import com.zds.biz.vo.response.user.OrgResponse;
import com.zds.user.service.OrgService;
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

@Api(tags = "后台单位模块")
@RestController
@RequestMapping(value = "/admin/org")
public class AdminOrgController {

    @Autowired
    private OrgService orgService;

    @Authorization
    @ApiOperation("查询所有单位")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public BaseResult<List<OrgResponse>> findList(){
        return BaseResult.success(orgService.findList());
    }

    @Authorization
    @ApiOperation("单位详情")
    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    public BaseResult<OrgDetailResponse> findDetail(@RequestBody IdRequest request){
        return BaseResult.success(orgService.findDetail(request.getId()));
    }

    @Authorization
    @ApiOperation("保存单位")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public BaseResult<String> saveOrg(@RequestBody OrgSaveRequest request){
        return BaseResult.judgeOperate(orgService.saveOrg(request));
    }

    @Authorization
    @ApiOperation("更新单位状态")
    @RequestMapping(value = "/status", method = RequestMethod.POST)
    public BaseResult<String> updateStatus(@RequestBody OrgStatusRequest request){
        return BaseResult.judgeOperate(orgService.updateStatus(request));
    }

    @Authorization
    @ApiOperation("删除单位")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public BaseResult<String> delete(@RequestBody IdRequest request){
        return BaseResult.judgeOperate(orgService.delete(request.getId()));
    }

    @Authorization
    @ApiOperation("单位下拉")
    @RequestMapping(value = "/select", method = RequestMethod.POST)
    public BaseResult<List<OrgResponse>> findSelect(@RequestBody OrgSelectRequest request){
        return BaseResult.success(orgService.findSelect(request));
    }

    @Authorization
    @ApiOperation("单位下拉(不过滤权限,过滤政府单位有用户的)")
    @RequestMapping(value = "/select/hav/user", method = RequestMethod.POST)
    public BaseResult<List<OrgResponse>> orgSelectHavUser(){
        return BaseResult.success(orgService.orgSelectHavUser());
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

    @ApiIgnore
    @Authorization
    @ApiOperation(value = "查询指定单位范围的单位名称")
    @RequestMapping(value = "/map", method = RequestMethod.POST)
    public BaseResult<Map<Long, String>> findOrgMapById(@RequestBody List<Long> request) {
        return BaseResult.success(orgService.findOrgMapById(request));
    }

    @ApiIgnore
    @ApiOperation(value = "查询所有单位信息")
    @RequestMapping(value = "/info/all", method = RequestMethod.POST)
    public BaseResult<List<OrgResponse>> findAllOrgInfo(@RequestBody String secretKey) {
        return BaseResult.success(orgService.findAllOrgInfo(secretKey));
    }

    @ApiIgnore
    @Authorization
    @ApiOperation(value = "单位名称模糊查询单位ID集合")
    @RequestMapping(value = "/name/like/list", method = RequestMethod.POST)
    public BaseResult<List<Long>> findOrgListByName(@RequestBody String organizationName) {
        return BaseResult.success(orgService.findOrgListByName(organizationName));
    }

    @ApiIgnore
    @Authorization
    @ApiOperation(value = "查询当前用户可见的单位ID集合")
    @RequestMapping(value = "/user/visible/list", method = RequestMethod.POST)
    public BaseResult<List<Long>> findOrgVisibleListByUser() {
        return BaseResult.success(orgService.findOrgVisibleListByUser());
    }

    @ApiIgnore
    @Authorization
    @ApiOperation(value = "查询指定单位可见的单位ID集合")
    @RequestMapping(value = "/visible/list", method = RequestMethod.POST)
    public BaseResult<List<Long>> findOrgVisibleListByOrg(@RequestBody Long organizationId) {
        return BaseResult.success(orgService.findOrgVisibleListByOrg(organizationId));
    }

    @ApiIgnore
    @ApiOperation(value = "查询所有企业单位信息")
    @RequestMapping(value = "/info/company", method = RequestMethod.POST)
    public BaseResult<List<OrgResponse>> infoCompany() {
        return BaseResult.success(orgService.infoCompany());
    }

    @Authorization
    @ApiOperation(value = "查询三个城燃企业对应的组织集合")
    @RequestMapping(value = "/get/childMap/select", method = RequestMethod.POST)
    public BaseResult<Map<String, List<Long>>> getOrgChildOrgMap(){
        return BaseResult.success(orgService.getOrgChildOrgMap());
    }

    @Authorization
    @ApiOperation(value = "查询三个城燃企业对应的组织集合")
    @RequestMapping(value = "/childOrgSelect", method = RequestMethod.POST)
    public BaseResult<List<OrgResponse>> childOrgSelect(){
        return BaseResult.success(orgService.childOrgSelect());
    }
}
