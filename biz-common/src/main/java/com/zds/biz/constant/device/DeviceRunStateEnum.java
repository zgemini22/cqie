package com.zds.biz.constant.device;


import com.zds.biz.constant.BaseEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * 设备运行时状态类型枚举
 */
public enum DeviceRunStateEnum implements BaseEnum<String> {

    JY_NORMAL("JY0", "正常"),
    JY_GAS_LEAK("JY3", "燃气重度泄漏"),
    JY_MANIPULATOR_FAILURE("JY6", "机械手故障"),
    JY_OTHER_FAILURE("JY7", "其他故障"),
    JY_DEVICE_LOSES_POWER("JY9", "设备断电"),
    JY_DEVICE_DEATH("JY10", "寿命到期"),
    YG_GAS_LEAK("YG0", "正常"),
    YG_SMOKE_WARNING("YG1", "烟雾报警"),
    YG_SENSOR_FAILURE("YG2", "传感器故障"),
    YG_MAZE_FAILURE("YG3", "迷宫故障");

    private String key;

    private String msg;

    DeviceRunStateEnum(String key, String msg) {
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

    public static DeviceRunStateEnum query(String key) {
        if (key != null) {
            DeviceRunStateEnum[] values = DeviceRunStateEnum.values();
            for (DeviceRunStateEnum result : values) {
                if (result.getKey().equals(key)) {
                    return result;
                }
            }
        }
        return null;
    }

    public static Map<String, String> getMap() {
        Map<String, String> map = new HashMap<>();
        DeviceRunStateEnum[] values = DeviceRunStateEnum.values();
        for (DeviceRunStateEnum result : values) {
            if (!map.containsKey(result.getKey())) {
                map.put(result.getKey(), result.getTitle());
            }
        }
        return map;
    }
}
