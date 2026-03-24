package com.zds.biz.constant.user;

import com.zds.biz.constant.BaseEnum;

/**
 * 角色类型,字典group_id=ROLE_TYPE
 */
public enum RoleTypeEnum implements BaseEnum<String> {

    SYSTEM_ADMIN("SYSTEM_ADMIN","系统管理员"),
    OTHER("OTHER","其它角色"),
    ;

    private String key;

    private String title;

    RoleTypeEnum(String key, String title) {
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

    public static RoleTypeEnum query(String key){
        if (key != null) {
            RoleTypeEnum[] values = RoleTypeEnum.values();
            for (RoleTypeEnum result : values) {
                if (result.getKey().equals(key)) {
                    return result;
                }
            }
        }
        return null;
    }
}
