package com.zds.user.manager;

import com.zds.biz.constant.PermissionEnum;

/**
 * 检查用户权限
 */
public interface CheckUserPermissionManager {

    void checkUserPermission(PermissionEnum permissionEnum);
}
