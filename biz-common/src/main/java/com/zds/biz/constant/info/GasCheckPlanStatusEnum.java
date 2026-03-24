package com.zds.biz.constant.info;


import com.zds.biz.constant.BaseEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * 检修计划类型枚举
 */
public enum GasCheckPlanStatusEnum implements BaseEnum<Integer> {
    STOP(0, "停用"),
    OPEN(1, "启用"),
    COMPLETE(2, "已完成");

    private int key;

    private String msg;

    GasCheckPlanStatusEnum(int key, String msg){
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

    public static GasCheckPlanStatusEnum query(Integer key){
        if (key != null) {
            GasCheckPlanStatusEnum[] values = GasCheckPlanStatusEnum.values();
            for (GasCheckPlanStatusEnum result : values) {
                if (result.getKey().equals(key)) {
                    return result;
                }
            }
        }
        return null;
    }

    public static Map<Integer,String> getMap(){
        Map<Integer,String> map = new HashMap<>();
        GasCheckPlanStatusEnum[] values = GasCheckPlanStatusEnum.values();
        for (GasCheckPlanStatusEnum result : values) {
            if (!map.containsKey(result.getKey())) {
                map.put(result.getKey(), result.getTitle());
            }
        }
        return map;
    }
}
