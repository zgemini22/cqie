package com.zds.user.service;


import com.zds.user.po.TblMenuPermission;

import java.util.List;

/**
 * 菜单权限服务
 */
public interface MenuPermissionService {

    /**
     *菜单权限列表
     */
    List<TblMenuPermission> findPageList();



}
