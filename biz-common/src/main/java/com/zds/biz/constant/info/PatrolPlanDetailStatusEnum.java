package com.zds.biz.constant.info;


import com.zds.biz.constant.BaseEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * 巡检计划详情状态枚举
 */
public enum PatrolPlanDetailStatusEnum implements BaseEnum<Integer> {

    NOT_START(1, "待巡检"),
    HAS_END(2, "已巡检"),
    OVER_TIME(3, "已超期");

    private int key;

    private String msg;

    PatrolPlanDetailStatusEnum(int key, String msg){
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

    public static PatrolPlanDetailStatusEnum query(Integer key){
        if (key != null) {
            PatrolPlanDetailStatusEnum[] values = PatrolPlanDetailStatusEnum.values();
            for (PatrolPlanDetailStatusEnum result : values) {
                if (result.getKey().equals(key)) {
                    return result;
                }
            }
        }
        return null;
    }

    public static Map<Integer,String> getMap(){
        Map<Integer,String> map = new HashMap<>();
        PatrolPlanDetailStatusEnum[] values = PatrolPlanDetailStatusEnum.values();
        for (PatrolPlanDetailStatusEnum result : values) {
            if (!map.containsKey(result.getKey())) {
                map.put(result.getKey(), result.getTitle());
            }
        }
        return map;
    }
}
