package com.zds.biz.constant.info;


import com.zds.biz.constant.BaseEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * 隐患等级类型枚举
 */
public enum GasWarningDangerLevelEnum implements BaseEnum<String> {
    GENERAL("GENERAL", "三级"),
    MORE("MORE", "二级"),
    MAJOR("MAJOR", "一级");

    private String key;

    private String msg;

    GasWarningDangerLevelEnum(String key, String msg) {
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

    public static GasWarningDangerLevelEnum query(String key) {
        if (key != null) {
            GasWarningDangerLevelEnum[] values = GasWarningDangerLevelEnum.values();
            for (GasWarningDangerLevelEnum result : values) {
                if (result.getKey().equals(key)) {
                    return result;
                }
            }
        }
        return null;
    }

    public static Map<String, String> getMap() {
        Map<String, String> map = new HashMap<>();
        GasWarningDangerLevelEnum[] values = GasWarningDangerLevelEnum.values();
        for (GasWarningDangerLevelEnum result : values) {
            if (!map.containsKey(result.getKey())) {
                map.put(result.getKey(), result.getTitle());
            }
        }
        return map;
    }
}
