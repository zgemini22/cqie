package com.zds.biz.constant.info;

import com.zds.biz.constant.BaseEnum;

import java.util.HashMap;
import java.util.Map;

public enum ReportAccidentTypeEnum implements BaseEnum<String> {
    CITIES_GAS_OUT_ACCIDENT("CITIES_GAS_OUT_ACCIDENT", "城镇燃气泄露事故"),
    GAS_PIPELINE_ACCIDENT("GAS_PIPELINE_ACCIDENT", "燃气管道事故"),
    DEVICE_BREAKDOWN_ACCIDENT("DEVICE_BREAKDOWN_ACCIDENT", "设备故障引发事故"),
    MANMADE_MISOPERATION_ACCIDENT("MANMADE_MISOPERATION_ACCIDENT", "人为操作不当事故"),
    TPOS_DESTROY_ACCIDENT("TPOS_DESTROY_ACCIDENT", "第三方破坏事故"),
    DISASTERS_CAUSE_ACCIDENT("DISASTERS_CAUSE_ACCIDENT", "自然灾害引发事故"),
    OTHER("OTHER", "其他");

    private String key;

    private String msg;

    ReportAccidentTypeEnum(String key, String msg) {
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

    public static ReportAccidentTypeEnum query(String key) {
        if (key != null) {
            ReportAccidentTypeEnum[] values = ReportAccidentTypeEnum.values();
            for (ReportAccidentTypeEnum result : values) {
                if (result.getKey().equals(key)) {
                    return result;
                }
            }
        }
        return null;
    }

    public static Map<String, String> getMap() {
        Map<String, String> map = new HashMap<>();
        ReportAccidentTypeEnum[] values = ReportAccidentTypeEnum.values();
        for (ReportAccidentTypeEnum result : values) {
            if (!map.containsKey(result.getKey())) {
                map.put(result.getKey(), result.getTitle());
            }
        }
        return map;
    }

    public static int getCodeByKey(String key) {
        switch (key) {
            case "CITIES_GAS_OUT_ACCIDENT":
                return 1;  // 城镇燃气泄漏
            case "GAS_PIPELINE_ACCIDENT":
                return 2;  // 燃气管道
            case "DEVICE_BREAKDOWN_ACCIDENT":
                return 3;  // 设备故障引发
            case "MANMADE_MISOPERATION_ACCIDENT":
                return 4;  // 人为操作不当
            case "TPOS_DESTROY_ACCIDENT":
                return 5;  // 第三方破坏
            case "DISASTERS_CAUSE_ACCIDENT":
                return 6;  // 自然灾害引发
            case "OTHER":
                return 7;  // 其他
            default:
                throw new IllegalArgumentException("未知的事故类型key: " + key);
        }
    }
}
