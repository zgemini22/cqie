package com.zds.biz.constant.device;

import com.zds.biz.constant.BaseEnum;

/**
 * 设备报修状态枚举
 */
public enum DeviceRepairStatusEnum implements BaseEnum<Integer> {

    PENDING(1, "待维修"),
    PROCESSED(2, "已完成"),
    ;

    private int key;

    private String msg;

    DeviceRepairStatusEnum(int key, String msg) {
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

    public static DeviceRepairStatusEnum query(Integer key) {
        if (key != null) {
            DeviceRepairStatusEnum[] values = DeviceRepairStatusEnum.values();
            for (DeviceRepairStatusEnum result : values) {
                if (result.getKey().equals(key)) {
                    return result;
                }
            }
        }
        return null;
    }
}
