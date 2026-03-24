package com.zds.biz.constant.info;


import com.zds.biz.constant.BaseEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * 用气安全巡检-客户类型枚举
 */
public enum GasSecurityPatrolPlanTypeEnum implements BaseEnum<String> {
    SAFETYINSPECTION("SAFETYINSPECTION", "安全巡检"),
    DAILYINSPECTION("DAILYINSPECTION", "日常巡检");

    private String key;

    private String msg;

    GasSecurityPatrolPlanTypeEnum(String key, String msg) {
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

    public static GasSecurityPatrolPlanTypeEnum query(String key) {
        if (key != null) {
            GasSecurityPatrolPlanTypeEnum[] values = GasSecurityPatrolPlanTypeEnum.values();
            for (GasSecurityPatrolPlanTypeEnum result : values) {
                if (result.getKey().equals(key)) {
                    return result;
                }
            }
        }
        return null;
    }

    public static Map<String, String> getMap() {
        Map<String, String> map = new HashMap<>();
        GasSecurityPatrolPlanTypeEnum[] values = GasSecurityPatrolPlanTypeEnum.values();
        for (GasSecurityPatrolPlanTypeEnum result : values) {
            if (!map.containsKey(result.getKey())) {
                map.put(result.getKey(), result.getTitle());
            }
        }
        return map;
    }
}
