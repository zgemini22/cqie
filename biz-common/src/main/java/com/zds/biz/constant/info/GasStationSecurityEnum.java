package com.zds.biz.constant.info;

import com.zds.biz.constant.BaseEnum;


public enum GasStationSecurityEnum implements BaseEnum<String> {
    NORMAL("NORMAL", "正常"),
    MAINTENANCE("MAINTENANCE", "维护"),
    DEACTIVATE("DEACTIVATE", "停用"),
    ;
    private String key;

    private String title;
    GasStationSecurityEnum(String key, String title ) {
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
        GasStationSecurityEnum[] values = values();
        for (GasStationSecurityEnum value : values) {
            if (value.getTitle().equals(title)) {
                return value.getKey();
            }
        }
        return null;
    }

    public static String getTitle(String key) {
        GasStationSecurityEnum[] values = values();
        for (GasStationSecurityEnum value : values) {
            if (value.getKey().equals(key)) {
                return value.getTitle();
            }
        }
        return null;
    }
}