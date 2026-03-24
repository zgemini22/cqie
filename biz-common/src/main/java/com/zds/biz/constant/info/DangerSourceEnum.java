package com.zds.biz.constant.info;


import com.zds.biz.constant.BaseEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * 隐患来源,字典group_id=DANGER_SOURCE
 */
public enum DangerSourceEnum implements BaseEnum<String> {

    PATROL("PATROL", "巡检"),
    WARNING("WARNING", "设备预警"),
    COOPERATE_EQUIPMENT("COOPERATE_EQUIPMENT", "协同设备监测"),
    COOPERATE_POLICE("COOPERATE_POLICE", "协同外部接警"),
    COOPERATE_PUBLIC("COOPERATE_PUBLIC", "协同群众上报"),
    COOPERATE_OTHER("COOPERATE_OTHER", "协同其他"),
    CHECK("CHECK", "检修"),
    ;

    private String key;

    private String msg;

    DangerSourceEnum(String key, String msg){
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

    public static DangerSourceEnum query(String key){
        if (key != null) {
            DangerSourceEnum[] values = DangerSourceEnum.values();
            for (DangerSourceEnum result : values) {
                if (result.getKey().equals(key)) {
                    return result;
                }
            }
        }
        return null;
    }


    public static Map<String,String> getMap(){
        Map<String,String> map = new HashMap<>();
        DangerSourceEnum[] values = DangerSourceEnum.values();
        for (DangerSourceEnum result : values) {
            if (!map.containsKey(result.getKey())) {
                map.put(result.getKey(), result.getTitle());
            }
        }
        return map;
    }

}
