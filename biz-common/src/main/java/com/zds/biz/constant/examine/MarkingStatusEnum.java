package com.zds.biz.constant.examine;

import com.zds.biz.constant.BaseEnum;

import java.util.HashMap;
import java.util.Map;

public enum MarkingStatusEnum implements BaseEnum<String> {
    TO_BE_GRADED("TO_BE_GRADED", "待阅卷"),
    IN_GRADING("IN_GRADING", "阅卷中"),
    COMPLETED("COMPLETED", "已完成"),
    PUBLISHED("PUBLISHED", "已发布"),;

    private String key;

    private String msg;

    MarkingStatusEnum(String key, String msg) {
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

    public static MarkingStatusEnum query(String key) {
        if (key != null) {
            MarkingStatusEnum[] values = MarkingStatusEnum.values();
            for (MarkingStatusEnum result : values) {
                if (result.getKey().equals(key)) {
                    return result;
                }
            }
        }
        return null;
    }

    public static Map<String, String> getMap() {
        Map<String, String> map = new HashMap<>();
        MarkingStatusEnum[] values = MarkingStatusEnum.values();
        for (MarkingStatusEnum result : values) {
            if (!map.containsKey(result.getKey())) {
                map.put(result.getKey(), result.getTitle());
            }
        }
        return map;
    }
}
