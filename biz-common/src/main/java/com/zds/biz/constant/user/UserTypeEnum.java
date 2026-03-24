package com.zds.biz.constant.user;

import com.zds.biz.constant.BaseEnum;

/**
 * 用户类型,字典group_id=USER_TYPE
 */
public enum UserTypeEnum implements BaseEnum<String> {

    SYSTEM_ADMIN("SYSTEM_ADMIN", "系统管理员用户"),
    ORG_USER("ORG_USER", "单位用户"),
    ;

    private String key;

    private String title;

    UserTypeEnum(String key, String title) {
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

    public static UserTypeEnum query(String key){
        if (key != null) {
            UserTypeEnum[] values = UserTypeEnum.values();
            for (UserTypeEnum result : values) {
                if (result.getKey().equals(key)) {
                    return result;
                }
            }
        }
        return null;
    }
}
