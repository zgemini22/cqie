package com.zds.biz.constant.info;


import com.zds.biz.constant.BaseEnum;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 巡检类型枚举
 */
public enum PatrolTypeEnum implements BaseEnum<String> {
    STA_PAT_TYPE_GOV("STA_PAT_TYPE_GOV","政府输配站巡检内容类型"),
    STA_PAT_TYPE_COM("STA_PAT_TYPE_COM","企业输配站巡检内容类型"),
    PRO_PAT_TYPE_GOV("PRO_PAT_TYPE_GOV","政府工程巡检内容类型"),
    PRO_PAT_TYPE_COM("PRO_PAT_TYPE_COM","企业工程巡检内容类型"),
    STR_PAT_TYPE_COM("STR_PAT_TYPE_COM","企业三方施工巡检内容类型"),
    PIPE_PAT_TYPE_COM("PIPE_PAT_TYPE_COM","企业管线巡检内容类型"),
    SECU_PAT_TYPE_COM("SECU_PAT_TYPE_COM","企业入户巡检内容类型"),
    ;

    private String key;

    private String msg;

    PatrolTypeEnum(String key, String msg){
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

    public static PatrolTypeEnum query(String key){
        if (key != null) {
            PatrolTypeEnum[] values = PatrolTypeEnum.values();
            for (PatrolTypeEnum result : values) {
                if (result.getKey().equals(key)) {
                    return result;
                }
            }
        }
        return null;
    }

    public static Map<String,String> getMap(){
        Map<String,String> map = new HashMap<>();
        PatrolTypeEnum[] values = PatrolTypeEnum.values();
        for (PatrolTypeEnum result : values) {
            if (!map.containsKey(result.getKey())) {
                map.put(result.getKey(), result.getTitle());
            }
        }
        return map;
    }

    public static List<PatrolTypeEnum> getTypeList() {
        return Arrays.asList(PatrolTypeEnum.values());
    }
}
