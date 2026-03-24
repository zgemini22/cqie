package com.zds.biz.constant.device;


import com.zds.biz.constant.BaseEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * 设备PQ2平台对外接口操作方法枚举
 */
public enum DeviceForeignMethodEnum implements BaseEnum<String> {

    ADD("add", "添加"),
    DELETE("delete", "删除");

    private String key;

    private String msg;

    DeviceForeignMethodEnum(String key, String msg) {
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
