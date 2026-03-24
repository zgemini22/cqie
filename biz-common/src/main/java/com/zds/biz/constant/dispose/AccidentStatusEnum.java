package com.zds.biz.constant.dispose;

import com.zds.biz.constant.BaseEnum;

/**
 * 事故状态枚举
 */
public enum AccidentStatusEnum implements BaseEnum<Integer> {

    TO_BE_JUDGED(1, "待研判"),
    PROCESSING(2, "处理中"),
    PROCESSED(3, "已处理");

    private int key;

    private String msg;

    AccidentStatusEnum(int key, String msg) {
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

    public static AccidentStatusEnum query(Integer key) {
        if (key != null) {
            AccidentStatusEnum[] values = AccidentStatusEnum.values();
            for (AccidentStatusEnum result : values) {
                if (result.getKey().equals(key)) {
                    return result;
                }
            }
        }
        return null;
    }
}
