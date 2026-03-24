package com.zds.user.controller.admin;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zds.biz.constant.PowerEnum;
import com.zds.biz.targetcheck.Authorization;
import com.zds.biz.vo.BaseResult;
import com.zds.biz.vo.IdRequest;
import com.zds.biz.vo.SelectResponse;
import com.zds.biz.vo.request.user.*;
import com.zds.biz.vo.response.user.MenuListResponse;
import com.zds.biz.vo.response.user.RoleDetailResponse;
import com.zds.biz.vo.response.user.RoleResponse;
import com.zds.user.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "后台角色模块")
@RestController
@RequestMapping("/admin/role")
public class AdminRoleController {

    @Autowired
    private RoleService roleService;

    @Authorization(PowerEnum.ROLE_SAVE)
    @ApiOperation("角色保存")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public BaseResult<String> saveRole(@RequestBody RoleSaveRequest request){
        return BaseResult.judgeOperate(roleService.saveRole(request));
    }

    @Authorization(PowerEnum.ROLE_DETAIL)
    @ApiOperation("角色详情")
    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    public BaseResult<RoleDetailResponse> findDetail(@RequestBody IdRequest request){
        //检查入参
        request.toRequestCheck();
        return BaseResult.success(roleService.findDetail(request.getId()));
    }

    @Authorization(PowerEnum.ROLE_DELETE)
    @ApiOperation("角色删除")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public BaseResult<String> roleDelete(@RequestBody IdRequest request){
        //检查入参
        request.toRequestCheck();
        return BaseResult.judgeOperate(roleService.deleteById(request.getId()));
    }

    @Authorization(PowerEnum.ROLE_PAGELIST)
    @ApiOperation("角色列表")
    @RequestMapping(value = "/pageList", method = RequestMethod.POST)
    public BaseResult<IPage<RoleResponse>> rolePageList(@RequestBody RolePageListRequest request){
        return BaseResult.success(roleService.findRolePageList(request));
    }

    @Authorization(PowerEnum.ROLE_STATUS)
    @ApiOperation("更新角色状态")
    @RequestMapping(value = "/status", method = RequestMethod.POST)
    public BaseResult<String> updateStatus(@RequestBody RoleStatusRequest request){
        return BaseResult.judgeOperate(roleService.updateStatus(request));
    }

    @Authorization(PowerEnum.ROLE_SELECT_LIST)
    @ApiOperation("角色下拉列表")
    @RequestMapping(value = "/select/list", method = RequestMethod.POST)
    public BaseResult<List<SelectResponse>> selectList(@RequestBody RoleSelectRequest request){
        return BaseResult.success(roleService.selectList(request));
    }

    @Authorization(PowerEnum.ROLE_GRANT)
    @ApiOperation("角色授权")
    @RequestMapping(value = "/grant", method = RequestMethod.POST)
    public BaseResult<String> grantRole(@RequestBody RoleGrantRequest request){
        return BaseResult.judgeOperate(roleService.grantRole(request));
    }

    @Authorization(PowerEnum.ROLE_VISIBLE_MENU)
    @ApiOperation(value = "查询可见角色权限", notes = "入参传入单位ID")
    @RequestMapping(value = "/visible/menu", method = RequestMethod.POST)
    public BaseResult<List<MenuListResponse>> findVisibleMenu(@RequestBody VisibleMenuRequest request){
        return BaseResult.success(roleService.findVisibleMenu(request.getOrganizationId()));
    }
}
