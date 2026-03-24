package com.zds.biz.constant.user;

import com.zds.biz.constant.BaseEnum;

/**
 * 用户状态,字典group_id=USER_STATUS
 */
public enum UserStatusEnum implements BaseEnum<String> {

    ENABLE("ENABLE", "启用"),
    DISABLE("DISABLE", "禁用"),
    ;

    private String key;

    private String title;

    UserStatusEnum(String key, String title) {
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

    public static UserStatusEnum query(String key){
        if (key != null) {
            UserStatusEnum[] values = UserStatusEnum.values();
            for (UserStatusEnum result : values) {
                if (result.getKey().equals(key)) {
                    return result;
                }
            }
        }
        return null;
    }
}
