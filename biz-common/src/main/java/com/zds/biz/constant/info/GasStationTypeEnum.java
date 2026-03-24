package com.zds.biz.constant.info;

import com.zds.biz.constant.BaseEnum;

public enum GasStationTypeEnum implements BaseEnum<String> {

    HAVEPEOPLE("HAVEPEOPLE", "有人值守"),
    NOPEOPLE("NOPEOPLE", "无人值守"),
    ;
    private String key;

    private String title;

    GasStationTypeEnum(String key, String title) {
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

    public static GasStationTypeEnum query(String key){
        if (key != null) {
            GasStationTypeEnum[] values = GasStationTypeEnum.values();
            for (GasStationTypeEnum result : values) {
                if (result.getKey().equals(key)) {
                    return result;
                }
            }
        }
        return null;
    }

    public static String getKey(String title) {
        GasStationTypeEnum[] values = values();
        for (GasStationTypeEnum value : values) {
            if (value.getTitle().equals(title)) {
                return value.getKey();
            }
        }
        return null;
    }

    public static String getTitle(String key) {
        GasStationTypeEnum[] values = values();
        for (GasStationTypeEnum value : values) {
            if (value.getKey().equals(key)) {
                return value.getTitle();
            }
        }
        return null;
    }
}
