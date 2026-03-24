package com.zds.biz.constant.info;


import com.zds.biz.constant.BaseEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * 检修计划类型枚举
 */
public enum GasCheckPlanFrequencyEnum implements BaseEnum<String> {
    DAY("DAY", "每日"),
    WEEK("WEEK", "周"),
    MONTH("MONTH", "月"),
    YEAR("YEAR", "年"),
    ;

    private String key;

    private String msg;

    GasCheckPlanFrequencyEnum(String key, String msg) {
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

    public static GasCheckPlanFrequencyEnum query(String key) {
        if (key != null) {
            GasCheckPlanFrequencyEnum[] values = GasCheckPlanFrequencyEnum.values();
            for (GasCheckPlanFrequencyEnum result : values) {
                if (result.getKey().equals(key)) {
                    return result;
                }
            }
        }
        return null;
    }

    public static Map<String, String> getMap() {
        Map<String, String> map = new HashMap<>();
        GasCheckPlanFrequencyEnum[] values = GasCheckPlanFrequencyEnum.values();
        for (GasCheckPlanFrequencyEnum result : values) {
            if (!map.containsKey(result.getKey())) {
                map.put(result.getKey(), result.getTitle());
            }
        }
        return map;
    }
}
