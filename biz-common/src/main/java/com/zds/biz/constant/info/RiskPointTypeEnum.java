package com.zds.biz.constant.info;

import com.zds.biz.constant.BaseEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * 风险点位类型枚举
 */
public enum RiskPointTypeEnum implements BaseEnum<String> {
    PIPELINE_LEAK("PIPELINE_LEAK", "管道泄漏"),
    PRESSURE_ABNORMAL("PRESSURE_ABNORMAL", "压力异常"),
    FIRE_ALARM("FIRE_ALARM", "消防报警"),
    ELECTRIC_ABNORMAL("ELECTRIC_ABNORMAL", "电表异常"),
    WATER_ABNORMAL("WATER_ABNORMAL", "水表异常"),
    GAS_ABNORMAL("GAS_ABNORMAL", "燃气表异常"),
    TEST_PILE("TEST_PILE", "测试桩"),
    VALVE_PIT("VALVE_PIT", "燃气阀井"),
    PIPE("PIPE", "燃气管道"),
    UNKNOWN("UNKNOWN", "未知"),
    STATIONS_ARE_FLAMMABLE("STATIONS_ARE_FLAMMABLE", "加油站易燃"),
    GAS_PIPELINE_BODY("GAS_PIPELINE_BODY", "燃气管道本体及阀井"),
    BUILDINGS_AND_PROTECTIVE("BUILDINGS_AND_PROTECTIVE", "燃气管道周边重要建筑物及保护物"),
    GAS_LEAKAGE("GAS_LEAKAGE", "燃气泄漏"),
    OTHER("OTHER", "其他"),
    ADJACENT_UNDERGROUND("ADJACENT_UNDERGROUND","相邻地下密闭空间"),
    PARALLEL_ELECTRIC_WELL("PARALLEL_ELECTRIC_WELL","2米内与电井并行，根据实际情况安装"),
    SURGE_TANK("SURGE_TANK","调压柜"),
    STATION_YARD("STATION_YARD","站场")
    ;

    private String key;

    private String msg;

    RiskPointTypeEnum(String key, String msg){
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

    public static RiskPointTypeEnum query(String key){
        if (key != null) {
            RiskPointTypeEnum[] values = RiskPointTypeEnum.values();
            for (RiskPointTypeEnum result : values) {
                if (result.getKey().equals(key)) {
                    return result;
                }
            }
        }
        return null;
    }

    public static Map<String,String> getMap(){
        Map<String,String> map = new HashMap<>();
        RiskPointTypeEnum[] values = RiskPointTypeEnum.values();
        for (RiskPointTypeEnum result : values) {
            if (!map.containsKey(result.getKey())) {
                map.put(result.getKey(), result.getTitle());
            }
        }
        return map;
    }
}
