package com.zds.biz.constant.info;

import com.zds.biz.constant.BaseEnum;

public enum GasStationStatusEnum implements BaseEnum<String> {

    ENABLE("ENABLE", "启用"),
    DISABLE("DEACTIVATE", "停用"),
    ;
    private String key;

    private String title;

    GasStationStatusEnum(String key, String title) {
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

    public static GasStationStatusEnum query(Integer key){
        if (key != null) {
            GasStationStatusEnum[] values = GasStationStatusEnum.values();
            for (GasStationStatusEnum result : values) {
                if (result.getKey().equals(key)) {
                    return result;
                }
            }
        }
        return null;
    }
}
