package com.zds.biz.constant.info;

import com.zds.biz.constant.BaseEnum;

public enum gasStationGatherEnum implements BaseEnum<String> {
    TEMPERATURE("TEMPERATURE", "温度"),
    PRESSURE("PRESSURE", "压力"),
    FLOW("FLOW", "流量"),
    DISPLACEMENT("DISPLACEMENT", "位移"),
    CONCENTRATION("CONCENTRATION", "浓度"),
    ;
    private String key;

    private String title;
    gasStationGatherEnum(String key, String title) {
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
    public static GasStationPointTypeEnum query(String key){
        if (key != null) {
            GasStationPointTypeEnum[] values = GasStationPointTypeEnum.values();
            for (GasStationPointTypeEnum result : values) {
                if (result.getKey().equals(key)) {
                    return result;
                }
            }
        }
        return null;
    }


    public static String getKey(String title) {
        gasStationGatherEnum[] values = values();
        for (gasStationGatherEnum value : values) {
            if (value.getTitle().equals(title)) {
                return value.getKey();
            }
        }
        return null;
    }

    public static String getTitle(String key) {
        gasStationGatherEnum[] values = values();
        for (gasStationGatherEnum value : values) {
            if (value.getKey().equals(key)) {
                return value.getTitle();
            }
        }
        return null;
    }
}
