package com.zds.biz.constant.info;

import com.zds.biz.constant.BaseEnum;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

public enum GasPipelineMaterialEnum implements BaseEnum<String> {

    PE("PE", "PE管"),
    STEEL("STEEL", "钢管"),
    IRON("IRON","铸铁管"),
    WAIT("WAIT","待定")
    ;
    private String key;

    private String title;

    GasPipelineMaterialEnum(String key, String title) {
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

    public static GasPipelineMaterialEnum query(String key){
        if (key != null) {
            GasPipelineMaterialEnum[] values = GasPipelineMaterialEnum.values();
            for (GasPipelineMaterialEnum result : values) {
                if (result.getKey().equals(key)) {
                    return result;
                }
            }
        }
        return null;
    }

    public static GasPipelineMaterialEnum queryName(String title){
        if (StringUtils.isNotBlank(title)) {
            GasPipelineMaterialEnum[] values = GasPipelineMaterialEnum.values();
            for (GasPipelineMaterialEnum result : values) {
                if (result.getTitle().equals(title.trim())) {
                    return result;
                }
            }
        }
        return null;
    }

    public static Map<String,String> getMap(){
        Map<String,String> map = new HashMap<>();
        GasPipelineMaterialEnum[] values = GasPipelineMaterialEnum.values();
        for (GasPipelineMaterialEnum result : values) {
            if (!map.containsKey(result.getKey())) {
                map.put(result.getKey(), result.getTitle());
            }
        }
        return map;
    }
}
