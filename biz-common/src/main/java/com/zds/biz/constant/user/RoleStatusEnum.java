package com.zds.biz.constant.user;

import com.zds.biz.constant.BaseEnum;

/**
 * 角色状态,字典group_id=ROLE_STATUS
 */
public enum RoleStatusEnum implements BaseEnum<String> {

    ENABLE("ENABLE", "启用"),
    DISABLE("DISABLE", "禁用"),
    ;

    private String key;

    private String title;

    RoleStatusEnum(String key, String title) {
        this.key = key;
        this.title = title;
    }

    @Override
    public String getKey() {
        return this.key;
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    public static RoleStatusEnum query(String key){
        if (key != null) {
            RoleStatusEnum[] values = RoleStatusEnum.values();
            for (RoleStatusEnum result : values) {
                if (result.getKey().equals(key)) {
                    return result;
                }
            }
        }
        return null;
    }
}
