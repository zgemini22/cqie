package com.zds.biz.constant.dispose;

import com.zds.biz.constant.BaseEnum;

/**
 * 事故来源枚举
 */
public enum AccidentSourceEnum implements BaseEnum<String> {

    PERSONAL_REPORTING("PERSONAL_REPORTING", "个人上报"),
    WARNING_REPORTING("WARNING_REPORTING", "预警上报");

    private String key;

    private String msg;

    AccidentSourceEnum(String key, String msg) {
        this.key = key;
        this.msg = msg;
    }

    @Override
    public String getKey() {
        return this.key;
    }

    @Override
    public String getTitle() {
        return this.msg;
    }

    public static AccidentSourceEnum query(String key) {
        if (key != null) {
            AccidentSourceEnum[] values = AccidentSourceEnum.values();
            for (AccidentSourceEnum result : values) {
                if (result.getKey().equals(key)) {
                    return result;
                }
            }
        }
        return null;
    }
}
