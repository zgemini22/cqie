package com.zds.user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zds.biz.vo.SelectResponse;
import com.zds.biz.vo.request.user.*;
import com.zds.biz.vo.response.user.MenuListResponse;
import com.zds.biz.vo.response.user.RoleDetailResponse;
import com.zds.biz.vo.response.user.RoleResponse;

import java.util.List;

/**
 * 角色服务
 */
public interface RoleService {
    /**
     * 角色保存
     */
    boolean saveRole(RoleSaveRequest request);
    /**
     * 角色详情
     */
    RoleDetailResponse findDetail(Long roleId);
    /**
     * 角色删除
     */
    boolean deleteById(Long roleId);
    /**
     * 角色列表-分页
     */
    IPage<RoleResponse> findRolePageList(RolePageListRequest request);
    /**
     * 更新角色状态
     */
    boolean updateStatus(RoleStatusRequest request);
    /**
     * 角色下拉列表
     */
    List<SelectResponse> selectList(RoleSelectRequest request);
    /**
     * 角色授权
     */
    boolean grantRole(RoleGrantRequest request);
    /**
     * 查询可见角色权限
     */
    List<MenuListResponse> findVisibleMenu(Long organizationId);
}
