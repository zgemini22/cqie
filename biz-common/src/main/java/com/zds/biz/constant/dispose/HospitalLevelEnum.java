package com.zds.biz.constant.dispose;

import com.zds.biz.constant.BaseEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * 字典key=HOSPITAL_TYPE)
 */
public enum HospitalLevelEnum implements BaseEnum<String> {
    FIRST_GRADE_A("FIRST_GRADE_A", "甲等, 三级"),
    TWO_GRADE_A("TWO_GRADE_A", "未定等, 二级"),
    THREE_GRADE_A("THREE_GRADE_A", "未定等, 一级"),

    THREE_GRADE("THREE_GRADE", "未定等, 三级"),
    ;

    HospitalLevelEnum(String key, String msg) {
        this.key = key;
        this.msg = msg;
    }
    private String key;
    private String msg;
    @Override
    public String getKey() {
        return this.key;
    }
    @Override
    public String getTitle() {
        return this.msg;
    }

    public static HospitalLevelEnum query(String key) {
        if (key != null) {
            HospitalLevelEnum[] values = HospitalLevelEnum.values();
            for (HospitalLevelEnum result : values) {
                if (result.getKey().equals(key)) {
                    return result;
                }
            }
        }
        return null;
    }


    public static Map<String, String> getMap() {
        Map<String, String> map = new HashMap<>();
        HospitalLevelEnum[] values = HospitalLevelEnum.values();
        for (HospitalLevelEnum result : values) {
            if (!map.containsKey(result.getKey())) {
                map.put(result.getKey(), result.getTitle());
            }
        }
        return map;
    }

}
