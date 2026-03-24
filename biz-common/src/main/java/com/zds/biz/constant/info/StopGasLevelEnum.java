package com.zds.biz.constant.info;

import com.zds.biz.constant.BaseEnum;

import java.util.HashMap;
import java.util.Map;

public enum StopGasLevelEnum  implements BaseEnum<String> {

    ONE_LEVEL("ONE_LEVEL", "一级"),
    TWO_LEVEL("TWO_LEVEL", "二级"),
    THREE_LEVEL("THREE_LEVEL", "三级");


    private String key;

    private String msg;

    StopGasLevelEnum(String key, String msg) {
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

    public static StopGasLevelEnum query(String key) {
        if (key != null) {
            StopGasLevelEnum[] values = StopGasLevelEnum.values();
            for (StopGasLevelEnum result : values) {
                if (result.getKey().equals(key)) {
                    return result;
                }
            }
        }
        return null;
    }

    public static Map<String, String> getMap() {
        Map<String, String> map = new HashMap<>();
        StopGasLevelEnum[] values = StopGasLevelEnum.values();
        for (StopGasLevelEnum result : values) {
            if (!map.containsKey(result.getKey())) {
                map.put(result.getKey(), result.getTitle());
            }
        }
        return map;
    }

}
