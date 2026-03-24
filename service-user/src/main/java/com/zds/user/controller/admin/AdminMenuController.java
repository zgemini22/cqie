package com.zds.user.controller.admin;

import com.zds.biz.constant.PowerEnum;
import com.zds.biz.targetcheck.Authorization;
import com.zds.biz.vo.BaseResult;
import com.zds.biz.vo.IdRequest;
import com.zds.biz.vo.request.user.MenuFindRequest;
import com.zds.biz.vo.request.user.MenuSaveRequest;
import com.zds.biz.vo.request.user.MenuStatusRequest;
import com.zds.biz.vo.response.user.MenuDetailResponse;
import com.zds.biz.vo.response.user.MenuListResponse;
import com.zds.user.service.MenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@Api(tags = "后台菜单模块")
@RestController
@RequestMapping("/admin/menu")
public class AdminMenuController {

    @Autowired
    private MenuService menuService;

    @Authorization(PowerEnum.MENU_SAVE)
    @ApiOperation("菜单保存")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public BaseResult<String> saveMenu(@RequestBody MenuSaveRequest request){
        return BaseResult.judgeOperate(menuService.saveMenu(request));
    }

    @Authorization(PowerEnum.MENU_DETAIL)
    @ApiOperation("菜单详情")
    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    public BaseResult<MenuDetailResponse> findDetail(@RequestBody IdRequest request){
        //检查入参
        request.toRequestCheck();
        return BaseResult.success(menuService.findDetail(request.getId()));
    }

    @Authorization(PowerEnum.MENU_DELETE)
    @ApiOperation("菜单删除")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public BaseResult<String> RuleDelete(@RequestBody IdRequest request){
        //检查入参
        request.toRequestCheck();
        return BaseResult.judgeOperate(menuService.deleteById(request.getId()));
    }

    @Authorization(PowerEnum.MENU_LIST)
    @ApiOperation("菜单列表")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public BaseResult<List<MenuListResponse>> findList(@RequestBody MenuFindRequest request){
        return BaseResult.success(menuService.findList(request));
    }

    @Authorization(PowerEnum.MENU_STATUS)
    @ApiOperation("更新菜单状态")
    @RequestMapping(value = "/status", method = RequestMethod.POST)
    public BaseResult<String> updateStatus(@RequestBody MenuStatusRequest request){
        return BaseResult.judgeOperate(menuService.updateStatus(request));
    }
}
