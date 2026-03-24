package com.zds.biz.constant.info;


import com.zds.biz.constant.BaseEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * 改建老旧管网类型枚举 ，字典:group_id=PROJECT_OLD_TYPE
 */
public enum GasProjectOldTypeEnum implements BaseEnum<String> {
    MUNICIPAL("MUNICIPAL", "市政"),
    RISER("RISER", "立管"),
    YARD("YARD", "庭院");

    private String key;

    private String msg;

    GasProjectOldTypeEnum(String key, String msg) {
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

    public static GasProjectOldTypeEnum query(String key) {
        if (key != null) {
            GasProjectOldTypeEnum[] values = GasProjectOldTypeEnum.values();
            for (GasProjectOldTypeEnum result : values) {
                if (result.getKey().equals(key)) {
                    return result;
                }
            }
        }
        return null;
    }

    public static Map<String, String> getMap() {
        Map<String, String> map = new HashMap<>();
        GasProjectOldTypeEnum[] values = GasProjectOldTypeEnum.values();
        for (GasProjectOldTypeEnum result : values) {
            if (!map.containsKey(result.getKey())) {
                map.put(result.getKey(), result.getTitle());
            }
        }
        return map;
    }
}
