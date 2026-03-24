package com.zds.biz.constant.info;

import com.zds.biz.constant.BaseEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * 风险点位等级枚举
 */
public enum RiskPointLevelEnum implements BaseEnum<String> {
    HIGH("HIGH", "高风险"),
    MIDEUM("MIDEUM", "中风险"),
    LOW("LOW", "低风险"),
    ;

    private String key;

    private String msg;

    RiskPointLevelEnum(String key, String msg){
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

    public static RiskPointLevelEnum query(String key){
        if (key != null) {
            RiskPointLevelEnum[] values = RiskPointLevelEnum.values();
            for (RiskPointLevelEnum result : values) {
                if (result.getKey().equals(key)) {
                    return result;
                }
            }
        }
        return null;
    }

    public static Map<String,String> getMap(){
        Map<String,String> map = new HashMap<>();
        RiskPointLevelEnum[] values = RiskPointLevelEnum.values();
        for (RiskPointLevelEnum result : values) {
            if (!map.containsKey(result.getKey())) {
                map.put(result.getKey(), result.getTitle());
            }
        }
        return map;
    }
}
