package com.zds.biz.constant.user;

/**
 * 字典分组key枚举
 */
public enum DictGroupEnum {

    //用户类型
    USER_TYPE("USER_TYPE"),
    //用户状态
    USER_STATUS("USER_STATUS"),
    //角色状态
    ROLE_STATUS("ROLE_STATUS"),
    //角色类型
    ROLE_TYPE("ROLE_TYPE"),
    //菜单类型
    MENU_TYPE("MENU_TYPE"),
    //菜单状态
    MENU_STATUS("MENU_STATUS"),
    //菜单分组
    MENU_GROUP("MENU_GROUP"),
    //单位状态
    ORGANIZATION_STATUS("ORGANIZATION_STATUS"),
    //单位类别
    ORGANIZATION_TYPE("ORGANIZATION_TYPE"),
    //隐患业务类型
    DANGER_BUSINESS_TYPE("DANGER_BUSINESS_TYPE"),
    //隐患等级
    DANGER_LEVEL("DANGER_LEVEL"),
    //隐患原因
    DANGER_REASON("DANGER_REASON"),
    //隐患来源
    DANGER_SOURCE("DANGER_SOURCE"),
    //问题类别
    SUBJECT_CATEGORY("SUBJECT_CATEGORY"),
    //指标单位
    GAS_NORM_UNIT("GAS_NORM_UNIT"),
    ;

    private String key;

    DictGroupEnum(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
