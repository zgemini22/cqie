package com.zds.biz.constant.info;


import com.zds.biz.constant.BaseEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * 巡检计划状态枚举
 */
public enum PatrolPlanStatusEnum implements BaseEnum<Integer> {

    NOT_START(1, "未开始"),
    IN_HAVING(2, "进行中"),
    HAS_END(3, "已结束");

    private int key;

    private String msg;

    PatrolPlanStatusEnum(int key, String msg){
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

    public static PatrolPlanStatusEnum query(Integer key){
        if (key != null) {
            PatrolPlanStatusEnum[] values = PatrolPlanStatusEnum.values();
            for (PatrolPlanStatusEnum result : values) {
                if (result.getKey().equals(key)) {
                    return result;
                }
            }
        }
        return null;
    }

    public static Map<Integer,String> getMap(){
        Map<Integer,String> map = new HashMap<>();
        PatrolPlanStatusEnum[] values = PatrolPlanStatusEnum.values();
        for (PatrolPlanStatusEnum result : values) {
            if (!map.containsKey(result.getKey())) {
                map.put(result.getKey(), result.getTitle());
            }
        }
        return map;
    }
}
