package com.zds.biz.constant.dispose;

import com.zds.biz.constant.BaseEnum;

/**
 * 级别枚举
 */
public enum AccidentLevelEnum implements BaseEnum<Integer> {

    I(1, "I级"),
    II(2, "II级"),
    III(3, "III级"),
    IV(4, "IV级"),
    ;

    private int key;

    private String msg;

    AccidentLevelEnum(int key, String msg) {
        this.key = key;
        this.msg = msg;
    }

    @Override
    public Integer getKey() {
        return this.key;
    }

    @Override
    public String getTitle() {
        return this.msg;
    }

    public static AccidentLevelEnum query(Integer key) {
        if (key != null) {
            AccidentLevelEnum[] values = AccidentLevelEnum.values();
            for (AccidentLevelEnum result : values) {
                if (result.getKey().equals(key)) {
                    return result;
                }
            }
        }
        return null;
    }
}
