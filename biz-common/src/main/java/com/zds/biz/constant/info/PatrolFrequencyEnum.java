package com.zds.biz.constant.info;


import com.zds.biz.constant.BaseEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * 巡检频次枚举
 */
public enum PatrolFrequencyEnum implements BaseEnum<String> {

    YEAR("YEAR", "每年"),
    QUARTER("QUARTER", "每季度"),
    MONTH("MONTH", "每月"),
    WEEK("WEEK", "每周"),
    DAY("DAY", "每日");

    private String key;

    private String msg;

    PatrolFrequencyEnum(String key, String msg){
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

    public static PatrolFrequencyEnum query(String key){
        if (key != null) {
            PatrolFrequencyEnum[] values = PatrolFrequencyEnum.values();
            for (PatrolFrequencyEnum result : values) {
                if (result.getKey().equals(key)) {
                    return result;
                }
            }
        }
        return null;
    }

    public static Map<String,String> getMap(){
        Map<String,String> map = new HashMap<>();
        PatrolFrequencyEnum[] values = PatrolFrequencyEnum.values();
        for (PatrolFrequencyEnum result : values) {
            if (!map.containsKey(result.getKey())) {
                map.put(result.getKey(), result.getTitle());
            }
        }
        return map;
    }
}
