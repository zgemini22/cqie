package com.zds.biz.constant.info;

import com.zds.biz.constant.BaseEnum;

import java.util.HashMap;
import java.util.Map;

public enum SupplyLevelEnum implements BaseEnum<String> {

    ONE_LEVEL("ONE_LEVEL", "一级"),
    TWO_LEVEL("TWO_LEVEL", "二级"),
    THREE_LEVEL("THREE_LEVEL", "三级"),
    ;
    private String key;

    private String title;

    SupplyLevelEnum(String key, String title) {
        this.key = key;
        this.title = title;
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public String getTitle() {
        return title;
    }

    public static SupplyLevelEnum query(String key){
        if (key != null) {
            SupplyLevelEnum[] values = SupplyLevelEnum.values();
            for (SupplyLevelEnum result : values) {
                if (result.getKey().equals(key)) {
                    return result;
                }
            }
        }
        return null;
    }

    public static Map<String,String> getMap(){
        Map<String,String> map = new HashMap<>();
        SupplyLevelEnum[] values = SupplyLevelEnum.values();
        for (SupplyLevelEnum result : values) {
            if (!map.containsKey(result.getKey())) {
                map.put(result.getKey(), result.getTitle());
            }
        }
        return map;
    }
}
