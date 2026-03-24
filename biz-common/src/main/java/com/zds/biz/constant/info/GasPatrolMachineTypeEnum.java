package com.zds.biz.constant.info;

import com.zds.biz.constant.BaseEnum;

import java.util.HashMap;
import java.util.Map;

public enum GasPatrolMachineTypeEnum implements BaseEnum<String> {

    VALVE("VALVE", "阀门"),
    METER("METER", "气表箱"),
    PRESSURE("PRESSURE", "调压设备"),
    INSPECTIONPOINTS("INSPECTIONPOINTS", "巡检点"),
    ;
    private String key;

    private String title;

    GasPatrolMachineTypeEnum(String key, String title) {
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
        GasPatrolMachineTypeEnum[] values = values();
        for (GasPatrolMachineTypeEnum value : values) {
            if (value.getTitle().equals(title)) {
                return value.getKey();
            }
        }
        return null;
    }

    public static String getTitle(String key) {
        GasPatrolMachineTypeEnum[] values = values();
        for (GasPatrolMachineTypeEnum value : values) {
            if (value.getKey().equals(key)) {
                return value.getTitle();
            }
        }
        return null;
    }

    public static Map<String,String> getMap(){
        Map<String,String> map = new HashMap<>();
        GasPatrolMachineTypeEnum[] values = GasPatrolMachineTypeEnum.values();
        for (GasPatrolMachineTypeEnum result : values) {
            if (!map.containsKey(result.getKey())) {
                map.put(result.getKey(), result.getTitle());
            }
        }
        return map;
    }
}
