package com.zds.biz.constant.device;


import com.zds.biz.constant.BaseEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * 设备使用点类型枚举
 */
public enum DeviceDropTypeEnum implements BaseEnum<String> {

    STATION("STATION", "输配站"),
    FILL("FILL", "加气站"),
    USER("USER", "用户"),
    PIPELINE("PIPELINE", "管网");

    private String key;

    private String msg;

    DeviceDropTypeEnum(String key, String msg) {
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

    public static DeviceDropTypeEnum query(String key) {
        if (key != null) {
            DeviceDropTypeEnum[] values = DeviceDropTypeEnum.values();
            for (DeviceDropTypeEnum result : values) {
                if (result.getKey().equals(key)) {
                    return result;
                }
            }
        }
        return null;
    }

    public static Map<String, String> getMap() {
        Map<String, String> map = new HashMap<>();
        DeviceDropTypeEnum[] values = DeviceDropTypeEnum.values();
        for (DeviceDropTypeEnum result : values) {
            if (!map.containsKey(result.getKey())) {
                map.put(result.getKey(), result.getTitle());
            }
        }
        return map;
    }
}
