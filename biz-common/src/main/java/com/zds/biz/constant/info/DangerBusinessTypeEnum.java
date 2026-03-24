package com.zds.biz.constant.info;


import com.zds.biz.constant.BaseEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * 隐患来源,字典group_id=DANGER_BUSINESS_TYPE
 */
public enum DangerBusinessTypeEnum implements BaseEnum<String> {

    STATION("STATION", "输配站"),
    FILL("FILL", "加气站"),
    PIPE("PIPE", "管网"),
    PROJECT("PROJECT", "工程建设"),
    GAS("GAS", "用气安全"),
    TRIPARTITE("TRIPARTITE", "第三方施工"),
    COOPERATE("COOPERATE", "协同"),
    ;

    private String key;

    private String msg;

    DangerBusinessTypeEnum(String key, String msg){
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

    public static DangerBusinessTypeEnum query(String key){
        if (key != null) {
            DangerBusinessTypeEnum[] values = DangerBusinessTypeEnum.values();
            for (DangerBusinessTypeEnum result : values) {
                if (result.getKey().equals(key)) {
                    return result;
                }
            }
        }
        return null;
    }

    public static Map<String,String> getMap(){
        Map<String,String> map = new HashMap<>();
        DangerBusinessTypeEnum[] values = DangerBusinessTypeEnum.values();
        for (DangerBusinessTypeEnum result : values) {
            if (!map.containsKey(result.getKey())) {
                map.put(result.getKey(), result.getTitle());
            }
        }
        return map;
    }

}
