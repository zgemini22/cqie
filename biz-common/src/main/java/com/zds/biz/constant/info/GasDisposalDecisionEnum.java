package com.zds.biz.constant.info;


import com.zds.biz.constant.BaseEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * 处置决策类型枚举 DISPOSAL_TYPE
 */
public enum GasDisposalDecisionEnum implements BaseEnum<String> {
    MAINTENANCE_WORK("MAINTENANCE_WORK", "检修工作延期"),
    DANGER_OVERDUE("DANGER_OVERDUE", "隐患逾期处置开展"),
    SECURITY_PATROL("SECURITY_PATROL", "入户安检延期开展"),
//    THIRD_PARTY_DELAY("THIRD_PARTY_DELAY", "第三方施工巡检延期开展"),
//    PIPELINE_DELAY("PIPELINE_DELAY", "管网巡检延期开展"),
//    ENGINEERING_DELAY("ENGINEERING_DELAY", "工程巡检延期开展"),
//    STATION_DELAY("STATION_DELAY", "站场巡检延期开展"),

    INSPECTION_DELAY("INSPECTION_DELAY", "巡检延期开展"),
    ;

    private String key;

    private String msg;

    GasDisposalDecisionEnum(String key, String msg) {
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

    public static GasDisposalDecisionEnum query(String key) {
        if (key != null) {
            GasDisposalDecisionEnum[] values = GasDisposalDecisionEnum.values();
            for (GasDisposalDecisionEnum result : values) {
                if (result.getKey().equals(key)) {
                    return result;
                }
            }
        }
        return null;
    }

    public static Map<String, String> getMap() {
        Map<String, String> map = new HashMap<>();
        GasDisposalDecisionEnum[] values = GasDisposalDecisionEnum.values();
        for (GasDisposalDecisionEnum result : values) {
            if (!map.containsKey(result.getKey())) {
                map.put(result.getKey(), result.getTitle());
            }
        }
        return map;
    }
}
