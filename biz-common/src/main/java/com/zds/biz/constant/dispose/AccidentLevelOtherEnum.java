package com.zds.biz.constant.dispose;

import com.zds.biz.constant.BaseEnum;

/**
 * 级别枚举
 */
public enum AccidentLevelOtherEnum implements BaseEnum<Integer> {

    I(1, "重大"),
    II(2, "较大"),
    III(3, "一般"),
    IV(4, "轻微"),
    ;

    private int key;

    private String msg;

    AccidentLevelOtherEnum(int key, String msg) {
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

    public static AccidentLevelOtherEnum query(Integer key) {
        if (key != null) {
            AccidentLevelOtherEnum[] values = AccidentLevelOtherEnum.values();
            for (AccidentLevelOtherEnum result : values) {
                if (result.getKey().equals(key)) {
                    return result;
                }
            }
        }
        return null;
    }
}
