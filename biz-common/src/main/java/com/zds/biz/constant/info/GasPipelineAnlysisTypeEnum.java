package com.zds.biz.constant.info;

import com.zds.biz.constant.BaseEnum;

import java.util.HashMap;
import java.util.Map;

public enum GasPipelineAnlysisTypeEnum implements BaseEnum<String> {

    DENSITY("DENSITY", "浓度"),
    WATERDEP("WATERDEP", "水深"),
    DRIFT("DRIFT","位移"),
    VIBRATE("VIBRATE","震动"),
    ;
    private String key;

    private String title;

    GasPipelineAnlysisTypeEnum(String key, String title) {
        this.key = key;
        this.title = title;
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public String getTitle() {
        return title;
    }

    public static GasPipelineAnlysisTypeEnum query(String key){
        if (key != null) {
            GasPipelineAnlysisTypeEnum[] values = GasPipelineAnlysisTypeEnum.values();
            for (GasPipelineAnlysisTypeEnum result : values) {
                if (result.getKey().equals(key)) {
                    return result;
                }
            }
        }
        return null;
    }

    public static Map<String,String> getMap(){
        Map<String,String> map = new HashMap<>();
        GasPipelineAnlysisTypeEnum[] values = GasPipelineAnlysisTypeEnum.values();
        for (GasPipelineAnlysisTypeEnum result : values) {
            if (!map.containsKey(result.getKey())) {
                map.put(result.getKey(), result.getTitle());
            }
        }
        return map;
    }
}
