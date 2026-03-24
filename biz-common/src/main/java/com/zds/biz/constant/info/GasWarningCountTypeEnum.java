package com.zds.biz.constant.info;


import com.zds.biz.constant.BaseEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * 预警统计类型枚举
 */
public enum GasWarningCountTypeEnum implements BaseEnum<String> {

    STATION_WARNING("STATION_WARNING", "输配站"),
    FILL_STATION_WARNING("FILL_STATION_WARNING", "加气站"),
    SECURITY_WARNING("SECURITY_WARNING", "用气安全"),
    PIPELINE_WARNING("PIPELINE_WARNING", "管网"),
    PIPELINE_SIMULATION_WARNING("PIPELINE_SIMULATION_WARNING", "管网仿真"),
    POLICE119("POLICE119", "119报警"),
    OTHER("OTHER", "其他");

    private String key;

    private String msg;

    GasWarningCountTypeEnum(String key, String msg) {
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

    public static GasWarningCountTypeEnum query(String key) {
        if (key != null) {
            GasWarningCountTypeEnum[] values = GasWarningCountTypeEnum.values();
            for (GasWarningCountTypeEnum result : values) {
                if (result.getKey().equals(key)) {
                    return result;
                }
            }
        }
        return null;
    }

    public static Map<String, String> getMap() {
        Map<String, String> map = new HashMap<>();
        GasWarningCountTypeEnum[] values = GasWarningCountTypeEnum.values();
        for (GasWarningCountTypeEnum result : values) {
            if (!map.containsKey(result.getKey())) {
                map.put(result.getKey(), result.getTitle());
            }
        }
        return map;
    }
}
