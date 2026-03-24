package com.zds.biz.constant.meter;

import com.zds.biz.constant.BaseEnum;

/**
 * 抄表类型枚举
 */
public enum MeterConfigTypeEnum implements BaseEnum<Integer> {

    SMART_METER_READING(1, "智能抄表"),
    MANUAL_METER_READING(2, "人工抄表"),;

    private int key;

    private String msg;

    MeterConfigTypeEnum(int key, String msg) {
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

    public static MeterConfigTypeEnum query(Integer key) {
        if (key != null) {
            MeterConfigTypeEnum[] values = MeterConfigTypeEnum.values();
            for (MeterConfigTypeEnum result : values) {
                if (result.getKey().equals(key)) {
                    return result;
                }
            }
        }
        return null;
    }
}
