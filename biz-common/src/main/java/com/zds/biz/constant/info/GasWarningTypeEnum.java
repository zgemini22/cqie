package com.zds.biz.constant.info;


import com.zds.biz.constant.BaseEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * 预警类型枚举
 */
public enum GasWarningTypeEnum implements BaseEnum<String> {
    FALSE_ALARM("FALSE_ALARM", "误报"),
    ACCIDENT("ACCIDENT", "事故"),
    HIDDEN_DANGER("HIDDEN_DANGER", "隐患");

    private String key;

    private String msg;

    GasWarningTypeEnum(String key, String msg) {
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

    public static GasWarningTypeEnum query(String key) {
        if (key != null) {
            GasWarningTypeEnum[] values = GasWarningTypeEnum.values();
            for (GasWarningTypeEnum result : values) {
                if (result.getKey().equals(key)) {
                    return result;
                }
            }
        }
        return null;
    }

    public static Map<String, String> getMap() {
        Map<String, String> map = new HashMap<>();
        GasWarningTypeEnum[] values = GasWarningTypeEnum.values();
        for (GasWarningTypeEnum result : values) {
            if (!map.containsKey(result.getKey())) {
                map.put(result.getKey(), result.getTitle());
            }
        }
        return map;
    }
}
