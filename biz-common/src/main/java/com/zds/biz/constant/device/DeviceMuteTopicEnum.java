package com.zds.biz.constant.device;

import com.zds.biz.constant.BaseEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * 设备消音主题枚举
 */
public enum DeviceMuteTopicEnum implements BaseEnum<String> {

    RQ_DEVICE_MUTE_JY("家用燃气报警器", "RQ_DEVICE_MUTE_JY"),
    RQ_DEVICE_MUTE_DS("商用燃气报警器", "RQ_DEVICE_MUTE_DS"),
    RQ_DEVICE_MUTE_YG("家用烟感报警器", "RQ_DEVICE_MUTE_YG");

    private String key;

    private String msg;

    DeviceMuteTopicEnum(String key, String msg) {
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

    public static DeviceMuteTopicEnum query(String key) {
        if (key != null) {
            DeviceMuteTopicEnum[] values = DeviceMuteTopicEnum.values();
            for (DeviceMuteTopicEnum result : values) {
                if (result.getKey().equals(key)) {
                    return result;
                }
            }
        }
        return null;
    }

    public static Map<String, String> getMap() {
        Map<String, String> map = new HashMap<>();
        DeviceMuteTopicEnum[] values = DeviceMuteTopicEnum.values();
        for (DeviceMuteTopicEnum result : values) {
            if (!map.containsKey(result.getKey())) {
                map.put(result.getKey(), result.getTitle());
            }
        }
        return map;
    }
}
