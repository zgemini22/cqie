package com.zds.biz.constant.info;

import com.zds.biz.constant.BaseEnum;

import java.util.HashMap;
import java.util.Map;

//风险点状态（字典值=RISK_POINT_STATUS）
public enum RiskStatusEnum implements BaseEnum<String> {
    UNDERREVIEW("UNDERREVIEW", "审核中"),
    APPROVED("APPROVED", "已通过"),
    RETURNED("RETURNED", "已退回"),
    DELETED("DELETED","已删除");

    private String key;

    private String msg;

    RiskStatusEnum(String key, String msg){
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

    public static RiskStatusEnum query(String key){
        if (key != null) {
            RiskStatusEnum[] values = RiskStatusEnum.values();
            for (RiskStatusEnum result : values) {
                if (result.getKey().equals(key)) {
                    return result;
                }
            }
        }
        return null;
    }

    public static Map<String,String> getMap(){
        Map<String,String> map = new HashMap<>();
        RiskStatusEnum[] values = RiskStatusEnum.values();
        for (RiskStatusEnum result : values) {
            if (!map.containsKey(result.getKey())) {
                map.put(result.getKey(), result.getTitle());
            }
        }
        return map;
    }

}
