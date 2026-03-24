package com.zds.biz.constant.info;

import com.zds.biz.constant.BaseEnum;

import java.util.HashMap;
import java.util.Map;

public enum GasPipelineMachineTypeEnum implements BaseEnum<String> {

    METER("METER", "气表箱"),
    PRESSURE("PRESSURE", "调压设备"),
    VALVE("VALVE", "阀门"),
    ;
    private String key;

    private String title;

    GasPipelineMachineTypeEnum(String key, String title) {
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

    public static GasPipelineMachineTypeEnum query(String key){
        if (key != null) {
            GasPipelineMachineTypeEnum[] values = GasPipelineMachineTypeEnum.values();
            for (GasPipelineMachineTypeEnum result : values) {
                if (result.getKey().equals(key)) {
                    return result;
                }
            }
        }
        return null;
    }

    public static Map<String,String> getMap(){
        Map<String,String> map = new HashMap<>();
        GasPipelineMachineTypeEnum[] values = GasPipelineMachineTypeEnum.values();
        for (GasPipelineMachineTypeEnum result : values) {
            if (!map.containsKey(result.getKey())) {
                map.put(result.getKey(), result.getTitle());
            }
        }
        return map;
    }
}
