package com.zds.biz.constant.info;


import com.zds.biz.constant.BaseEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * 检修计划类型枚举
 */
public enum GasCheckPlanTypeEnum implements BaseEnum<String> {
    TEMPORARY("TEMPORARY", "临时计划"),
    CYCLE("CYCLE", "周期计划");

    private String key;

    private String msg;

    GasCheckPlanTypeEnum(String key, String msg) {
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

    public static GasCheckPlanTypeEnum query(String key) {
        if (key != null) {
            GasCheckPlanTypeEnum[] values = GasCheckPlanTypeEnum.values();
            for (GasCheckPlanTypeEnum result : values) {
                if (result.getKey().equals(key)) {
                    return result;
                }
            }
        }
        return null;
    }

    public static Map<String, String> getMap() {
        Map<String, String> map = new HashMap<>();
        GasCheckPlanTypeEnum[] values = GasCheckPlanTypeEnum.values();
        for (GasCheckPlanTypeEnum result : values) {
            if (!map.containsKey(result.getKey())) {
                map.put(result.getKey(), result.getTitle());
            }
        }
        return map;
    }
}
