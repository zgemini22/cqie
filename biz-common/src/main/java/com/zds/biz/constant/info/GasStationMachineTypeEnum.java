package com.zds.biz.constant.info;

import com.zds.biz.constant.BaseEnum;

public enum GasStationMachineTypeEnum implements BaseEnum<String> {

    PRESSURE("BARESTHESIOMETER", "压力计"),
    FLOWMETER("FLOWMETER", "流量计"),
    THERMOGRAPH("THERMOGRAPH", "温度计"),
    LEAKAGE("LEAKAGE", "泄漏感知设备"),
    CLOUD("CLOUD", "极光云台"),
    ALARM("ALARM","报警器"),
    CAMERA("CAMERA","摄像头"),
    ;
    private String key;

    private String title;

    GasStationMachineTypeEnum(String key, String title) {
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
        GasStationMachineTypeEnum[] values = values();
        for (GasStationMachineTypeEnum value : values) {
            if (value.getTitle().equals(title)) {
                return value.getKey();
            }
        }
        return null;
    }

    public static String getTitle(String key) {
        GasStationMachineTypeEnum[] values = values();
        for (GasStationMachineTypeEnum value : values) {
            if (value.getKey().equals(key)) {
                return value.getTitle();
            }
        }
        return null;
    }
}
