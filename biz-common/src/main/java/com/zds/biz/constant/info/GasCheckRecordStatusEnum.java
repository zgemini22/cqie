package com.zds.biz.constant.info;


import com.zds.biz.constant.BaseEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * 检修计划类型枚举
 */
public enum GasCheckRecordStatusEnum implements BaseEnum<Integer> {
    OVERTIME(0, "已逾期"),
    WAIT(1, "待检修"),
    GOING(2, "进行中"),
    COMPLETE(3, "已检修");

    private int key;

    private String msg;

    GasCheckRecordStatusEnum(int key, String msg){
        this.key = key;
        this.msg = msg;
    }

    @Override
    public Integer getKey() {
        return this.key;
    }

    @Override
    public String getTitle() {
        return this.msg;
    }

    public static GasCheckRecordStatusEnum query(Integer key){
        if (key != null) {
            GasCheckRecordStatusEnum[] values = GasCheckRecordStatusEnum.values();
            for (GasCheckRecordStatusEnum result : values) {
                if (result.getKey().equals(key)) {
                    return result;
                }
            }
        }
        return null;
    }

    public static Map<Integer,String> getMap(){
        Map<Integer,String> map = new HashMap<>();
        GasCheckRecordStatusEnum[] values = GasCheckRecordStatusEnum.values();
        for (GasCheckRecordStatusEnum result : values) {
            if (!map.containsKey(result.getKey())) {
                map.put(result.getKey(), result.getTitle());
            }
        }
        return map;
    }
}
