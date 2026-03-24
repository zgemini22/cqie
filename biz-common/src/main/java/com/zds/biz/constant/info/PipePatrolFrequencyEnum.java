package com.zds.biz.constant.info;


import com.zds.biz.constant.BaseEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * 隐患类型枚举
 */
public enum PipePatrolFrequencyEnum implements BaseEnum<String> {

    ONEDAY("ONEDAY", "一天一巡"),
    TWODAY("TWODAY", "两天一巡"),
    ONEWEEK("ONEWEEK", "一周一巡"),
    HALFMONTH("HALFMONTH", "半月一巡"),
    ONEMONTH("ONEMONTH", "一月一巡"),
    TWOMONTH("TWOMONTH", "两月一巡"),
    OTHERS("OTHERS", "自定义"),
    ;

    private String key;

    private String msg;

    PipePatrolFrequencyEnum(String key, String msg){
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

    public static PipePatrolFrequencyEnum query(String key){
        if (key != null) {
            PipePatrolFrequencyEnum[] values = PipePatrolFrequencyEnum.values();
            for (PipePatrolFrequencyEnum result : values) {
                if (result.getKey().equals(key)) {
                    return result;
                }
            }
        }
        return null;
    }

    public static Map<String,String> getMap(){
        Map<String,String> map = new HashMap<>();
        PipePatrolFrequencyEnum[] values = PipePatrolFrequencyEnum.values();
        for (PipePatrolFrequencyEnum result : values) {
            if (!map.containsKey(result.getKey())) {
                map.put(result.getKey(), result.getTitle());
            }
        }
        return map;
    }
}
