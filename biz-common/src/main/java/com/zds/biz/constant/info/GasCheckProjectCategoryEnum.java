package com.zds.biz.constant.info;


import com.zds.biz.constant.BaseEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * 检修类别枚举
 */
public enum GasCheckProjectCategoryEnum implements BaseEnum<String> {
    STATION("STATION", "输配站"),
    FILL("FILL", "加气站"),
    PIPE("PIPE", "管网");

    private String key;

    private String msg;

    GasCheckProjectCategoryEnum(String key, String msg) {
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

    public static GasCheckProjectCategoryEnum query(String key) {
        if (key != null) {
            GasCheckProjectCategoryEnum[] values = GasCheckProjectCategoryEnum.values();
            for (GasCheckProjectCategoryEnum result : values) {
                if (result.getKey().equals(key)) {
                    return result;
                }
            }
        }
        return null;
    }

    public static Map<String, String> getMap() {
        Map<String, String> map = new HashMap<>();
        GasCheckProjectCategoryEnum[] values = GasCheckProjectCategoryEnum.values();
        for (GasCheckProjectCategoryEnum result : values) {
            if (!map.containsKey(result.getKey())) {
                map.put(result.getKey(), result.getTitle());
            }
        }
        return map;
    }
}
