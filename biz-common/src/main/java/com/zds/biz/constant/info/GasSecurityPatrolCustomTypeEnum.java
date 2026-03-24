package com.zds.biz.constant.info;


import com.zds.biz.constant.BaseEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * 用气安全巡检-客户类型枚举
 */
public enum GasSecurityPatrolCustomTypeEnum implements BaseEnum<String> {
    RESIDENT("RESIDENT", "居民"),
    NO_RESIDE("NO_RESIDE", "非居");

    private String key;

    private String msg;

    GasSecurityPatrolCustomTypeEnum(String key, String msg) {
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

    public static GasSecurityPatrolCustomTypeEnum query(String key) {
        if (key != null) {
            GasSecurityPatrolCustomTypeEnum[] values = GasSecurityPatrolCustomTypeEnum.values();
            for (GasSecurityPatrolCustomTypeEnum result : values) {
                if (result.getKey().equals(key)) {
                    return result;
                }
            }
        }
        return null;
    }

    public static Map<String, String> getMap() {
        Map<String, String> map = new HashMap<>();
        GasSecurityPatrolCustomTypeEnum[] values = GasSecurityPatrolCustomTypeEnum.values();
        for (GasSecurityPatrolCustomTypeEnum result : values) {
            if (!map.containsKey(result.getKey())) {
                map.put(result.getKey(), result.getTitle());
            }
        }
        return map;
    }
}
