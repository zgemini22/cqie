package com.zds.biz.constant.info;


import com.zds.biz.constant.BaseEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * 预警类型枚举
 */
public enum GasStopLevelEnum implements BaseEnum<String> {
    ONE_LEVEL("ONE_LEVEL", "一级"),
    TWO_LEVEL("TWO_LEVEL", "二级"),
    THREE_LEVEL("THREE_LEVEL", "三级");

    private String key;

    private String msg;

    GasStopLevelEnum(String key, String msg) {
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

    public static GasStopLevelEnum query(String key) {
        if (key != null) {
            GasStopLevelEnum[] values = GasStopLevelEnum.values();
            for (GasStopLevelEnum result : values) {
                if (result.getKey().equals(key)) {
                    return result;
                }
            }
        }
        return null;
    }

    public static Map<String, String> getMap() {
        Map<String, String> map = new HashMap<>();
        GasStopLevelEnum[] values = GasStopLevelEnum.values();
        for (GasStopLevelEnum result : values) {
            if (!map.containsKey(result.getKey())) {
                map.put(result.getKey(), result.getTitle());
            }
        }
        return map;
    }
}
