package com.zds.user.controller.admin;

import com.zds.biz.targetcheck.Authorization;
import com.zds.biz.vo.BaseResult;
import com.zds.biz.vo.IdRequest;
import com.zds.biz.vo.request.user.OrganizationPersonSaveRequest;
import com.zds.biz.vo.request.user.OrganizationPersonSelectRequest;
import com.zds.biz.vo.response.user.OrganizationPersonDetailResponse;
import com.zds.biz.vo.response.user.OrganizationPersonResponse;
import com.zds.user.service.TblOrganizationPersonService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Api(tags = "企业管理—从业人员列表")
@RestController
@RequestMapping(value = "/admin/organization/person")
public class TblOrganizationPersonController {

    @Autowired
    private TblOrganizationPersonService organizationPersonService;

    // ====================== 只改了这个方法 ======================
    @Authorization
    @ApiOperation("查询所有组织人员")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public BaseResult<List<OrganizationPersonResponse>> findList(@RequestBody OrganizationPersonSelectRequest request){
        return BaseResult.success(organizationPersonService.findList(request));
    }

    // ====================== 以下完全和你原来一毛一样 ======================
    @Authorization
    @ApiOperation("组织人员详情")
    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    public BaseResult<OrganizationPersonDetailResponse> findDetail(@RequestBody IdRequest request){
        return BaseResult.success(organizationPersonService.findDetail(request.getId()));
    }

    @Authorization
    @ApiOperation("保存组织人员")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public BaseResult<String> saveOrganizationPerson(@RequestBody OrganizationPersonSaveRequest request){
        return BaseResult.judgeOperate(organizationPersonService.saveOrganizationPerson(request));
    }

    @Authorization
    @ApiOperation("删除组织人员")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public BaseResult<String> delete(@RequestBody IdRequest request){
        return BaseResult.judgeOperate(organizationPersonService.delete(request.getId()));
    }

    @Authorization
    @ApiOperation("组织人员下拉")
    @RequestMapping(value = "/select", method = RequestMethod.POST)
    public BaseResult<List<OrganizationPersonResponse>> findSelect(@RequestBody OrganizationPersonSelectRequest request){
        return BaseResult.success(organizationPersonService.findSelect(request));
    }
    // ====================== ✅ Excel批量导入（最终零报错版） ======================
    @Authorization
    @ApiOperation("Excel批量导入从业人员")
    @RequestMapping(value = "/import", method = RequestMethod.POST)
    public BaseResult<String> importOrganizationPerson(MultipartFile file){
        try {
            organizationPersonService.importByExcel(file);
            return BaseResult.success("导入成功");
        } catch (Exception e) {
            // 完全对齐你项目的 judgeOperate 单参数重载
            return BaseResult.judgeOperate(false);
        }
    }

}