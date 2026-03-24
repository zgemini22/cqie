package com.zds.biz.constant.device;


import com.zds.biz.constant.BaseEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * 设备调拨申请状态枚举
 */
public enum DeviceApplyStatusEnum implements BaseEnum<Integer> {

    DRAFT(1, "草稿"),
    AUDIT(2, "待审核"),
    PASS(3, "通过"),
    FAIL(4, "未通过");

    private int key;

    private String msg;

    DeviceApplyStatusEnum(int key, String msg) {
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

    public static DeviceApplyStatusEnum query(Integer key) {
        if (key != null) {
            DeviceApplyStatusEnum[] values = DeviceApplyStatusEnum.values();
            for (DeviceApplyStatusEnum result : values) {
                if (result.getKey().equals(key)) {
                    return result;
                }
            }
        }
        return null;
    }

    public static Map<Integer, String> getMap() {
        Map<Integer, String> map = new HashMap<>();
        DeviceApplyStatusEnum[] values = DeviceApplyStatusEnum.values();
        for (DeviceApplyStatusEnum result : values) {
            if (!map.containsKey(result.getKey())) {
                map.put(result.getKey(), result.getTitle());
            }
        }
        return map;
    }
}
