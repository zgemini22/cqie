package com.zds.biz.constant.info;

import com.zds.biz.constant.BaseEnum;

import java.security.Key;

public enum GasFillStationTypeEnum implements BaseEnum<String> {
    CNG("CNG", "CNG加气站"),
    LNG("LNG", "LNG加气站"),
    ;
    private String key;

    private String title;

    GasFillStationTypeEnum(String key, String title) {
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

    public static GasStationMachineTypeEnum query(String key){
        if (key != null) {
            GasStationMachineTypeEnum[] values = GasStationMachineTypeEnum.values();
            for (GasStationMachineTypeEnum result : values) {
                if (result.getKey().equals(key)) {
                    return result;
                }
            }
        }
        return null;
    }


    public static String getKey(String title) {
        GasFillStationTypeEnum[] values = values();
        for (GasFillStationTypeEnum value : values) {
            if (value.getTitle().equals(title)) {
                return value.getKey();
            }
        }
        return null;
    }

    public static String getTitle(String key) {
        GasFillStationTypeEnum[] values = values();
        for (GasFillStationTypeEnum value : values) {
            if (value.getKey().equals(key)) {
                return value.getTitle();
            }
        }
        return null;
    }
}
