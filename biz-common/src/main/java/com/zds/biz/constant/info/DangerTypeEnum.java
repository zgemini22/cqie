package com.zds.biz.constant.info;


import com.zds.biz.constant.BaseEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * 隐患类型枚举
 */
public enum DangerTypeEnum implements BaseEnum<Integer> {

    ACCIDENT(1, "事故"),
    DANGER(2, "隐患"),
    NORMAL(3, "正常");

    private int key;

    private String msg;

    DangerTypeEnum(int key, String msg){
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

    public static DangerTypeEnum query(Integer key){
        if (key != null) {
            DangerTypeEnum[] values = DangerTypeEnum.values();
            for (DangerTypeEnum result : values) {
                if (result.getKey().equals(key)) {
                    return result;
                }
            }
        }
        return null;
    }

    public static Map<Integer,String> getMap(){
        Map<Integer,String> map = new HashMap<>();
        DangerTypeEnum[] values = DangerTypeEnum.values();
        for (DangerTypeEnum result : values) {
            if (!map.containsKey(result.getKey())) {
                map.put(result.getKey(), result.getTitle());
            }
        }
        return map;
    }
}
