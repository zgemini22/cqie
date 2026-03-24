package com.zds.biz.constant.device;


import com.zds.biz.constant.BaseEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * 设备使用点类型枚举
 */
public enum DeviceMaintenanceCategoryEnum implements BaseEnum<String> {

    MAINTENANCE_DAILY("MAINTENANCE_DAILY", "日常保养"),
    MAINTENANCE_ONE("MAINTENANCE_ONE", "一级检修保养"),
    MAINTENANCE_TWO("MAINTENANCE_TWO", "二级检修保养"),
    MAINTENANCE_THREE("MAINTENANCE_THREE", "三级检修保养"),
    MAINTENANCE_ANNUAL("MAINTENANCE_ANNUAL", "年度检修保养");;

    private String key;

    private String msg;

    DeviceMaintenanceCategoryEnum(String key, String msg) {
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

    public static DeviceMaintenanceCategoryEnum query(String key) {
        if (key != null) {
            DeviceMaintenanceCategoryEnum[] values = DeviceMaintenanceCategoryEnum.values();
            for (DeviceMaintenanceCategoryEnum result : values) {
                if (result.getKey().equals(key)) {
                    return result;
                }
            }
        }
        return null;
    }

    public static Map<String, String> getMap() {
        Map<String, String> map = new HashMap<>();
        DeviceMaintenanceCategoryEnum[] values = DeviceMaintenanceCategoryEnum.values();
        for (DeviceMaintenanceCategoryEnum result : values) {
            if (!map.containsKey(result.getKey())) {
                map.put(result.getKey(), result.getTitle());
            }
        }
        return map;
    }
}
