package com.zds.biz.constant.info;


import com.zds.biz.constant.BaseEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * 巡检计划状态枚举
 */
public enum SupplePlanStatusEnum implements BaseEnum<String> {

    NOT_START("NOT_START", "未开始"),
    IN_HAVING("IN_HAVING", "进行中"),
    HAS_END("HAS_END", "已结束");

    private String key;

    private String msg;

    SupplePlanStatusEnum(String key, String msg){
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

    public static SupplePlanStatusEnum query(String key){
        if (key != null) {
            SupplePlanStatusEnum[] values = SupplePlanStatusEnum.values();
            for (SupplePlanStatusEnum result : values) {
                if (result.getKey().equals(key)) {
                    return result;
                }
            }
        }
        return null;
    }

    public static Map<String,String> getMap(){
        Map<String,String> map = new HashMap<>();
        SupplePlanStatusEnum[] values = SupplePlanStatusEnum.values();
        for (SupplePlanStatusEnum result : values) {
            if (!map.containsKey(result.getKey())) {
                map.put(result.getKey(), result.getTitle());
            }
        }
        return map;
    }
}
