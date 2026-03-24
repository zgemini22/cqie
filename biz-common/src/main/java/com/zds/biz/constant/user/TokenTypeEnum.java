package com.zds.biz.constant.user;

import com.zds.biz.constant.BaseEnum;

/**
 * token类型枚举
 */
public enum TokenTypeEnum implements BaseEnum<String> {

    ADMIN("ADMIN", "后台系统"),
    BI("BI", "大屏"),
    APP("APP", "APP"),
    ;

    private String key;

    private String title;

    TokenTypeEnum(String key, String title) {
        this.key = key;
        this.title = title;
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public String getTitle() {
        return title;
    }

    public static TokenTypeEnum query(String key){
        if (key != null) {
            TokenTypeEnum[] values = TokenTypeEnum.values();
            for (TokenTypeEnum result : values) {
                if (result.getKey().equals(key)) {
                    return result;
                }
            }
        }
        return null;
    }
}
