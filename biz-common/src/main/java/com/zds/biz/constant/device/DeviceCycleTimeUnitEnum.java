package com.zds.biz.constant.device;

import com.zds.biz.constant.BaseEnum;

import java.util.HashMap;
import java.util.Map;

public enum DeviceCycleTimeUnitEnum implements BaseEnum<String> {
    YEAR("YEAR", "年"),
    MONTH("MONTH", "月"),
    DAY("DAY", "日");


    private String key;

    private String msg;

    DeviceCycleTimeUnitEnum(String key, String msg) {
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

    public static DeviceCycleTimeUnitEnum query(Integer key) {
        if (key != null) {
            DeviceCycleTimeUnitEnum[] values = DeviceCycleTimeUnitEnum.values();
            for (DeviceCycleTimeUnitEnum result : values) {
                if (result.getKey().equals(key)) {
                    return result;
                }
            }
        }
        return null;
    }

    public static Map<String, String> getMap() {
        Map<String, String> map = new HashMap<>();
        DeviceCycleTimeUnitEnum[] values = DeviceCycleTimeUnitEnum.values();
        for (DeviceCycleTimeUnitEnum result : values) {
            if (!map.containsKey(result.getKey())) {
                map.put(result.getKey(), result.getTitle());
            }
        }
        return map;
    }
}
