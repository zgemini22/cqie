package com.zds.biz.constant.info;


import com.zds.biz.constant.BaseEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * 巡检类别枚举
 */
public enum PatrolCategoryEnum implements BaseEnum<String> {

    PERIODICAL_PATROL("PERIODICAL_PATROL", "定期巡检"),
    TEMPORARY_PATROL("TEMPORARY_PATROL", "临时巡检");

    private String key;

    private String msg;

    PatrolCategoryEnum(String key, String msg){
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

    public static PatrolCategoryEnum query(String key){
        if (key != null) {
            PatrolCategoryEnum[] values = PatrolCategoryEnum.values();
            for (PatrolCategoryEnum result : values) {
                if (result.getKey().equals(key)) {
                    return result;
                }
            }
        }
        return null;
    }


    public static Map<String,String> getMap(){
        Map<String,String> map = new HashMap<>();
        PatrolCategoryEnum[] values = PatrolCategoryEnum.values();
        for (PatrolCategoryEnum result : values) {
            if (!map.containsKey(result.getKey())) {
                map.put(result.getKey(), result.getTitle());
            }
        }
        return map;
    }
}
