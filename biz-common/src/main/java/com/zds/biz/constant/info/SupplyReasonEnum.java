package com.zds.biz.constant.info;

import com.zds.biz.constant.BaseEnum;

import java.util.HashMap;
import java.util.Map;

public enum SupplyReasonEnum implements BaseEnum<String> {

    NEW_SUPPLY("NEW_SUPPLY", "新建供气"),
    RECOVER_SUPPLY("RECOVER_SUPPLY", "恢复供气"),
    ;
    private String key;

    private String title;

    SupplyReasonEnum(String key, String title) {
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

    public static SupplyReasonEnum query(String key){
        if (key != null) {
            SupplyReasonEnum[] values = SupplyReasonEnum.values();
            for (SupplyReasonEnum result : values) {
                if (result.getKey().equals(key)) {
                    return result;
                }
            }
        }
        return null;
    }

    public static Map<String,String> getMap(){
        Map<String,String> map = new HashMap<>();
        SupplyReasonEnum[] values = SupplyReasonEnum.values();
        for (SupplyReasonEnum result : values) {
            if (!map.containsKey(result.getKey())) {
                map.put(result.getKey(), result.getTitle());
            }
        }
        return map;
    }
}
