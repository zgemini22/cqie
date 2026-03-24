package com.zds.biz.constant.device;

import com.zds.biz.constant.BaseEnum;

import java.util.HashMap;
import java.util.Map;

public enum DeviceTypeUnitEnum implements BaseEnum<String> {

    CONCENTRATION_UNIT("CONCENTRATION_UNIT", "ppm"),
    TEMPERATURE_UNIT("TEMPERATURE_UNIT", "°C"),
    PRESSURE_UNIT("PRESSURE_UNIT", "kPa"),
    FLOW_UNIT("FLOW_UNIT", "Nm³/h"),
    CONCENTRATION("CONCENTRATION", "%vol"),
    SMOKE_UNIT("SMOKE_UNIT", "db/m"),
    VOLUME_UNIT("VOLUME_UNIT", "m³");

    private String key;

    private String msg;

    DeviceTypeUnitEnum(String key, String msg) {
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

    public static DeviceTypeUnitEnum query(String key) {
        if (key != null) {
            DeviceTypeUnitEnum[] values = DeviceTypeUnitEnum.values();
            for (DeviceTypeUnitEnum result : values) {
                if (result.getKey().equals(key)) {
                    return result;
                }
            }
        }
        return null;
    }

    public static Map<String, String> getMap() {
        Map<String, String> map = new HashMap<>();
        DeviceTypeUnitEnum[] values = DeviceTypeUnitEnum.values();
        for (DeviceTypeUnitEnum result : values) {
            if (!map.containsKey(result.getKey())) {
                map.put(result.getKey(), result.getTitle());
            }
        }
        return map;
    }
}
