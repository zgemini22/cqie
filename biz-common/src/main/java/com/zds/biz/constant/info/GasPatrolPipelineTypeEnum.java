package com.zds.biz.constant.info;

import com.zds.biz.constant.BaseEnum;

import java.util.HashMap;
import java.util.Map;

public enum GasPatrolPipelineTypeEnum implements BaseEnum<String> {

    INSPECTIONLINE("INSPECTIONLINE", "巡检线"),
    LOWPRESSURE("LOWPRESSURE", "低压管线"),
    MEDIUMVOLTAGE("MEDIUMVOLTAGE", "中压管线"),
    INTERMEDIATEPRESSURE("INTERMEDIATEPRESSURE", "次高压管线"),
    HIGHPRESSURE("HIGHPRESSURE", "高压管线"),
            ;
    private String key;

    private String title;

    GasPatrolPipelineTypeEnum(String key, String title) {
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



    public static String getKey(String title) {
        GasPatrolPipelineTypeEnum[] values = values();
        for (GasPatrolPipelineTypeEnum value : values) {
            if (value.getTitle().equals(title)) {
                return value.getKey();
            }
        }
        return null;
    }

    public static String getTitle(String key) {
        GasPatrolPipelineTypeEnum[] values = values();
        for (GasPatrolPipelineTypeEnum value : values) {
            if (value.getKey().equals(key)) {
                return value.getTitle();
            }
        }
        return null;
    }

    public static Map<String,String> getMap(){
        Map<String,String> map = new HashMap<>();
        GasPatrolPipelineTypeEnum[] values = GasPatrolPipelineTypeEnum.values();
        for (GasPatrolPipelineTypeEnum result : values) {
            if (!map.containsKey(result.getKey())) {
                map.put(result.getKey(), result.getTitle());
            }
        }
        return map;
    }
}