package com.zds.biz.constant.device;

import com.zds.biz.constant.BaseEnum;

/**
 * 设备紧急枚举
 */
public enum DeviceRepairUrgencyEnum implements BaseEnum<Integer> {

    HIGH(1, "高"),
    MIDDLE(2, "中"),
    LOW(3, "低"),
    ;

    private int key;

    private String msg;

    DeviceRepairUrgencyEnum(int key, String msg) {
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

    public static DeviceRepairUrgencyEnum query(Integer key) {
        if (key != null) {
            DeviceRepairUrgencyEnum[] values = DeviceRepairUrgencyEnum.values();
            for (DeviceRepairUrgencyEnum result : values) {
                if (result.getKey().equals(key)) {
                    return result;
                }
            }
        }
        return null;
    }
}
