package com.zds.biz.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * 权限枚举
 */
public enum PowerEnum {
    /**
     * 不需要权限认证
     */
    UNWANTED,
    /**
     * 以下为接口权限
     * 命名方式：/admin/list -> ADMIN_LIST
     */
    //后台系统用户模块
    ADMIN_STATUS,
    ADMIN_LIST,
    ADMIN_BINDING_BATCH,
    ADMIN_BINDING,
    ADMIN_UNBOUND,
    ADMIN_PWD_RESET,
    ADMIN_ROLE_SELECT,

    //后台数据字典模块
    DICT_LIST,
    DICT_SAVE,
    DICT_ENABLED,
    DICT_DELETE,
    DICT_GROUP_SAVE,
    DICT_GROUP_ENABLED,
    DICT_GROUP_DELETE,

    //后台操作日志模块
    LOG_LIST,
    LOG_DOWNLOAD,

    //后台角色模块
    ROLE_SAVE,
    ROLE_DETAIL,
    ROLE_DELETE,
    ROLE_PAGELIST,
    ROLE_STATUS,
    ROLE_SELECT_LIST,
    ROLE_GRANT,
    ROLE_VISIBLE_MENU,

    //后台系统组织模块
    ORG_LIST,
    ORG_ADD,
    ORG_UPDATE,
    ORG_STATUS,
    ORG_UPDATE_ADMIN,

    //后台菜单模块
    MENU_SAVE,
    MENU_DETAIL,
    MENU_DELETE,
    MENU_LIST,
    MENU_STATUS,

    ;

    public Map<String, String> getPowerMap() {
        Map<String, String> map = new HashMap<>();
        //后台系统用户模块
        map.put("后台系统用户模块-更新用户状态", PowerEnum.ADMIN_STATUS.name());
        map.put("后台系统用户模块-用户列表", PowerEnum.ADMIN_LIST.name());
        map.put("后台系统用户模块-批量绑定角色", PowerEnum.ADMIN_BINDING_BATCH.name());
        map.put("后台系统用户模块-用户绑定角色", PowerEnum.ADMIN_BINDING.name());
        map.put("后台系统用户模块-用户解绑角色", PowerEnum.ADMIN_UNBOUND.name());
        map.put("后台系统用户模块-重置指定用户的密码", PowerEnum.ADMIN_PWD_RESET.name());
        map.put("后台系统用户模块-角色下拉", PowerEnum.ADMIN_ROLE_SELECT.name());

        //后台数据字典模块
        map.put("后台数据字典模块-字典分组列表",PowerEnum.DICT_LIST.name());
        map.put("后台数据字典模块-字典分组保存",PowerEnum.DICT_SAVE.name());
        map.put("后台数据字典模块-字典分组启用/禁用",PowerEnum.DICT_ENABLED.name());
        map.put("后台数据字典模块-字典分组删除",PowerEnum.DICT_DELETE.name());
        map.put("后台数据字典模块-字典保存",PowerEnum.DICT_GROUP_SAVE.name());
        map.put("后台数据字典模块-字典启用/禁用",PowerEnum.DICT_GROUP_ENABLED.name());
        map.put("后台数据字典模块-字典删除",PowerEnum.DICT_GROUP_DELETE.name());

        //后台操作日志模块
        map.put("后台操作日志模块-查询操作日志列表",PowerEnum.LOG_LIST.name());
        map.put("后台操作日志模块-下载最新log日志文件",PowerEnum.LOG_DOWNLOAD.name());

        //后台角色模块
        map.put("后台角色模块-角色保存",PowerEnum.ROLE_SAVE.name());
        map.put("后台角色模块-角色详情",PowerEnum.ROLE_DETAIL.name());
        map.put("后台角色模块-角色删除",PowerEnum.ROLE_DELETE.name());
        map.put("后台角色模块-角色列表",PowerEnum.ROLE_PAGELIST.name());
        map.put("后台角色模块-更新角色状态",PowerEnum.ROLE_STATUS.name());
        map.put("后台角色模块-角色下拉列表",PowerEnum.ROLE_SELECT_LIST.name());
        map.put("后台角色模块-角色授权",PowerEnum.ROLE_GRANT.name());
        map.put("后台角色模块-查询可见角色权限",PowerEnum.ROLE_VISIBLE_MENU.name());

        //后台系统组织模块
        map.put("后台系统组织模块-查询组织列表",PowerEnum.ORG_LIST.name());
        map.put("后台系统组织模块-新增组织",PowerEnum.ORG_ADD.name());
        map.put("后台系统组织模块-修改组织",PowerEnum.ORG_UPDATE.name());
        map.put("后台系统组织模块-更新组织状态",PowerEnum.ORG_STATUS.name());
        map.put("后台系统组织模块-修改组织管理员",PowerEnum.ORG_UPDATE_ADMIN.name());

        //后台菜单模块
        map.put("后台菜单模块-菜单保存",PowerEnum.MENU_SAVE.name());
        map.put("后台菜单模块-菜单详情",PowerEnum.MENU_DETAIL.name());
        map.put("后台菜单模块-菜单删除",PowerEnum.MENU_DELETE.name());
        map.put("后台菜单模块-菜单列表",PowerEnum.MENU_LIST.name());
        map.put("后台菜单模块-更新菜单状态",PowerEnum.MENU_STATUS.name());

        return map;
    }
}
