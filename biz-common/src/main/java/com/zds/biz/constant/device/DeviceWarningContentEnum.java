package com.zds.biz.constant.device;


import com.zds.biz.constant.BaseEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * 设备预警数据描述枚举
 */
public enum DeviceWarningContentEnum implements BaseEnum<String> {

    JY0("JY0", "正常"),
    JY1("JY1", "燃气泄漏报警恢复"),
    JY3("JY3", "燃气重度泄漏"),
    JY6("JY6", "机械手故障"),
    JY7("JY7", "其他故障"),
    JY8("JY8", "自检完成"),
    JY9("JY9", "设备断电"),
    JY10("JY10", "寿命到期"),
    YG0("YG0", "正常"),
    YG1("YG1", "烟雾报警"),
    YG2("YG2", "传感器故障"),
    YG3("YG3", "迷宫故障"),
    YG4("YG4", "测试报警"),
    YT0("YT0", "正常"),
    YT1("YT1", "高报"),
    YT2("YT2", "低报"),
    GW0("GW0", "正常"),
    GW4("GW4", "泄漏报警"),
    GW20("GW20", "位移预警"),
    GW21("GW21", "震动预警"),
    GW60("GW60", "液位超限"),
    PRESSURE_FLOW_E1_0000("PRESSURE_FLOW_E1_0000", "正常"),
    PRESSURE_FLOW_E1_0003("PRESSURE_FLOW_E1_0003", "温度错误"),
    PRESSURE_FLOW_E1_0030("PRESSURE_FLOW_E1_0030", "压力错误"),
    PRESSURE_FLOW_E1_0300("PRESSURE_FLOW_E1_0300", "电池电量低"),
    PRESSURE_FLOW_E1_3000("PRESSURE_FLOW_E1_3000", "工况流量超最大工况流量"),
    PRESSURE_FLOW_E2_0003("PRESSURE_FLOW_E2_0003", "存储器异常"),
    PRESSURE7("PRESSURE7", "除钝报警"),
    PRESSURE8("PRESSURE8", "温度上限报警"),
    PRESSURE9("PRESSURE9", "温度下限报警"),
    PRESSURE10("PRESSURE10", "高度上限报警"),
    PRESSURE11("PRESSURE11", "高度下限报警")
    ;

    private String key;

    private String msg;

    DeviceWarningContentEnum(String key, String msg) {
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

    public static DeviceWarningContentEnum query(String key) {
        if (key != null) {
            DeviceWarningContentEnum[] values = DeviceWarningContentEnum.values();
            for (DeviceWarningContentEnum result : values) {
                if (result.getKey().equals(key)) {
                    return result;
                }
            }
        }
        return null;
    }

    public static Map<String, String> getMap() {
        Map<String, String> map = new HashMap<>();
        DeviceWarningContentEnum[] values = DeviceWarningContentEnum.values();
        for (DeviceWarningContentEnum result : values) {
            if (!map.containsKey(result.getKey())) {
                map.put(result.getKey(), result.getTitle());
            }
        }
        return map;
    }
}
