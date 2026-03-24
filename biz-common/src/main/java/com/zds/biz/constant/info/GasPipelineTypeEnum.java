package com.zds.biz.constant.info;

import com.zds.biz.constant.BaseEnum;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

public enum GasPipelineTypeEnum implements BaseEnum<String> {

    HIGH("HIGH", "高压管"),
    INTERME("INTERME", "次高压"),
    MEDIUM("MEDIUM","中压"),
    LOW("LOW","低压"),
    WAIT("WAIT","待定"),
    ;
    private String key;

    private String title;

    GasPipelineTypeEnum(String key, String title) {
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

    public static GasPipelineTypeEnum query(String key){
        if (key != null) {
            GasPipelineTypeEnum[] values = GasPipelineTypeEnum.values();
            for (GasPipelineTypeEnum result : values) {
                if (result.getKey().equals(key)) {
                    return result;
                }
            }
        }
        return null;
    }

    public static GasPipelineTypeEnum queryName(String title){
        if (StringUtils.isNotBlank(title)) {
            GasPipelineTypeEnum[] values = GasPipelineTypeEnum.values();
            for (GasPipelineTypeEnum result : values) {
                if (result.getTitle().equals(title.trim())) {
                    return result;
                }
            }
        }
        return null;
    }

    public static Map<String,String> getMap(){
        Map<String,String> map = new HashMap<>();
        GasPipelineTypeEnum[] values = GasPipelineTypeEnum.values();
        for (GasPipelineTypeEnum result : values) {
            if (!map.containsKey(result.getKey())) {
                map.put(result.getKey(), result.getTitle());
            }
        }
        return map;
    }
}
