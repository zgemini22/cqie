package com.zds.biz.constant.info;


import com.zds.biz.constant.BaseEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * 隐患类型枚举
 */
public enum PipePatrolTypeEnum implements BaseEnum<String> {

    PIPE("PIPE", "管线"),
    MACHINE("MACHINE", "设备"),
    ;

    private String key;

    private String msg;

    PipePatrolTypeEnum(String key, String msg){
        this.key = key;
        this.msg = msg;
    }

    @Override
    public String getKey() {
        return this.key;
    }

    @Override
    public String getTitle() {
        return this.msg;
    }

    public static PipePatrolTypeEnum query(String key){
        if (key != null) {
            PipePatrolTypeEnum[] values = PipePatrolTypeEnum.values();
            for (PipePatrolTypeEnum result : values) {
                if (result.getKey().equals(key)) {
                    return result;
                }
            }
        }
        return null;
    }

    public static Map<String,String> getMap(){
        Map<String,String> map = new HashMap<>();
        PipePatrolTypeEnum[] values = PipePatrolTypeEnum.values();
        for (PipePatrolTypeEnum result : values) {
            if (!map.containsKey(result.getKey())) {
                map.put(result.getKey(), result.getTitle());
            }
        }
        return map;
    }
}
