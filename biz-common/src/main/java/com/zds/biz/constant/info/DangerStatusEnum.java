package com.zds.biz.constant.info;


import com.zds.biz.constant.BaseEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * 隐患状态枚举
 */
public enum DangerStatusEnum implements BaseEnum<Integer> {

    NOT_START(1, "待整改"),
    HAS_END(2, "已整改"),
    OVER_TIME(0, "已超期"),
    ;

    private int key;

    private String msg;

    DangerStatusEnum(int key, String msg){
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

    public static DangerStatusEnum query(Integer key){
        if (key != null) {
            DangerStatusEnum[] values = DangerStatusEnum.values();
            for (DangerStatusEnum result : values) {
                if (result.getKey().equals(key)) {
                    return result;
                }
            }
        }
        return null;
    }

    public static Map<Integer,String> getMap(){
        Map<Integer,String> map = new HashMap<>();
        DangerStatusEnum[] values = DangerStatusEnum.values();
        for (DangerStatusEnum result : values) {
            if (!map.containsKey(result.getKey())) {
                map.put(result.getKey(), result.getTitle());
            }
        }
        return map;
    }
}
