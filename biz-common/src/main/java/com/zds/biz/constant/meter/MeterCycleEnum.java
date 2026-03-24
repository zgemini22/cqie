package com.zds.biz.constant.meter;

import com.zds.biz.constant.BaseEnum;

/**
 * 抄表周期枚举
 */
public enum MeterCycleEnum implements BaseEnum<Integer> {

    ONCE_MONTH(1, "每月一次"),
    ONCE_TWO_MONTH(2, "双月一次"),;

    private int key;

    private String msg;

    MeterCycleEnum(int key, String msg) {
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

    public static MeterCycleEnum query(Integer key) {
        if (key != null) {
            MeterCycleEnum[] values = MeterCycleEnum.values();
            for (MeterCycleEnum result : values) {
                if (result.getKey().equals(key)) {
                    return result;
                }
            }
        }
        return null;
    }
}
