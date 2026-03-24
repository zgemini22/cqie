package com.zds.biz.constant.info;


import com.zds.biz.constant.BaseEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * 用气安全-隐患等级类型枚举
 */
public enum SecurityFillDangerLevelEnum implements BaseEnum<String> {
    ONE("FALSE_ALARM", "一级"),
    TWO("ACCIDENT", "二级"),
    THREE("HIDDEN_DANGER", "三级");

    private String key;

    private String msg;

    SecurityFillDangerLevelEnum(String key, String msg) {
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

    public static SecurityFillDangerLevelEnum query(String key) {
        if (key != null) {
            SecurityFillDangerLevelEnum[] values = SecurityFillDangerLevelEnum.values();
            for (SecurityFillDangerLevelEnum result : values) {
                if (result.getKey().equals(key)) {
                    return result;
                }
            }
        }
        return null;
    }

    public static Map<String, String> getMap() {
        Map<String, String> map = new HashMap<>();
        SecurityFillDangerLevelEnum[] values = SecurityFillDangerLevelEnum.values();
        for (SecurityFillDangerLevelEnum result : values) {
            if (!map.containsKey(result.getKey())) {
                map.put(result.getKey(), result.getTitle());
            }
        }
        return map;
    }
}
