package com.zds.biz.constant.info;

import com.zds.biz.constant.BaseEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * 风险点位关联实体枚举
 */
public enum RiskEntityTypeEnum implements BaseEnum<String> {
    STATION("STATION", "站场"),
    PIPE("PIPE", "燃气管道"),
    USER("USER", "用户"),
    CONSTRUCTION("CONSTRUCTION", "三方施工"),
    OTHER("OTHER", "其他"),
    DEVICE("DEVICE", "设备设施"),
    MONITOR("MONITOR","燃气泄漏监测/位移监测/震动监测")
    ;

    private String key;
    private String msg;

    RiskEntityTypeEnum(String key, String msg) {
        this.key = key;
        this.msg = msg;
    }

    public String getKey() {
        return key;
    }

    public String getMsg() {
        return msg;
   }

    @Override
    public String getTitle() {
        return this.msg;
    }

    public static RiskEntityTypeEnum query(String key){
        if (key != null) {
            RiskEntityTypeEnum[] values = RiskEntityTypeEnum.values();
            for (RiskEntityTypeEnum result : values) {
                if (result.getKey().equals(key)) {
                    return result;
                }
            }
        }
        return null;
    }

    public static Map<String,String> getMap(){
        Map<String,String> map = new HashMap<>();
        RiskEntityTypeEnum[] values = RiskEntityTypeEnum.values();
        for (RiskEntityTypeEnum result : values) {
            if (!map.containsKey(result.getKey())) {
                map.put(result.getKey(), result.getTitle());
            }
        }
        return map;
    }
}
