package com.zds.biz.constant.info;

import com.zds.biz.constant.BaseEnum;

public enum GasStationPointTypeEnum implements BaseEnum<String> {

    INPUT("INPUT", "进站"),
    OUTPUT("OUTPUT", "出站"),

    NOT_HAVA("NOT_HAVA", "无")
    ;
    private String key;

    private String title;

    GasStationPointTypeEnum(String key, String title) {
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
        GasStationPointTypeEnum[] values = values();
        for (GasStationPointTypeEnum value : values) {
            if (value.getTitle().equals(title)) {
                return value.getKey();
            }
        }
        return null;
    }

    public static String getTitle(String key) {
        GasStationPointTypeEnum[] values = values();
        for (GasStationPointTypeEnum value : values) {
            if (value.getKey().equals(key)) {
                return value.getTitle();
            }
        }
        return null;
    }
}
