package com.zds.biz.constant.meter;

import com.zds.biz.constant.BaseEnum;

/**
 * 抄表状态枚举
 */
public enum MeterReadStatusEnum implements BaseEnum<Integer> {

    ABNORMAL(2, "异常"),
    COPIED(3, "已抄"),
    TO_BE_COPIED(4, "待抄"),;

    private int key;

    private String msg;

    MeterReadStatusEnum(int key, String msg) {
        this.key = key;
        this.msg = msg;
    }

    @Override
    public Integer getKey() {
        return this.key;
    }

    @Override
    public String getTitle() {
        return this.msg;
    }

    public static MeterReadStatusEnum query(Integer key) {
        if (key != null) {
            MeterReadStatusEnum[] values = MeterReadStatusEnum.values();
            for (MeterReadStatusEnum result : values) {
                if (result.getKey().equals(key)) {
                    return result;
                }
            }
        }
        return null;
    }
}
