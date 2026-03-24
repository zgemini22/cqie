package com.zds.biz.constant.meter;

import com.zds.biz.constant.BaseEnum;

/**
 * 抄表处理方式枚举
 */
public enum MeterHandleEnum implements BaseEnum<Integer> {

    RESUBMIT(1, "重新提交"),
    IGNORE_DATA(2, "忽略数据"),
    MANUAL_ENTRY(3, "人工录入"),;

    private int key;

    private String msg;

    MeterHandleEnum(int key, String msg) {
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

    public static MeterHandleEnum query(Integer key) {
        if (key != null) {
            MeterHandleEnum[] values = MeterHandleEnum.values();
            for (MeterHandleEnum result : values) {
                if (result.getKey().equals(key)) {
                    return result;
                }
            }
        }
        return null;
    }
}
