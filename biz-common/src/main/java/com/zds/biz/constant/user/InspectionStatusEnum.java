package com.zds.biz.constant.user;

import com.zds.biz.constant.BaseEnum;

/**
 * 巡检状态枚举
 */
public enum InspectionStatusEnum implements BaseEnum<String> {

    INSPECTED("已巡检", "已巡检"),
    PENDING("待巡检", "待巡检"),
    OVERDUE("已超期", "已超期"),
    ;

    private String key;

    private String title;

    InspectionStatusEnum(String key, String title) {
        this.key = key;
        this.title = title;
    }

    @Override
    public String getKey() {
        return this.key;
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    public static InspectionStatusEnum query(String key) {
        if (key != null) {
            InspectionStatusEnum[] values = InspectionStatusEnum.values();
            for (InspectionStatusEnum result : values) {
                if (result.getKey().equals(key)) {
                    return result;
                }
            }
        }
        return null;
    }
}