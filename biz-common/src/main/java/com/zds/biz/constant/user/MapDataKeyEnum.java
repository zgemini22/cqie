package com.zds.biz.constant.user;

import com.zds.biz.constant.BaseEnum;

/**
 * 地图配置-地图key
 */
public enum MapDataKeyEnum implements BaseEnum<String> {

    APP("APP", "APP地图"),
    BI_BLUE("BI_BLUE", "大屏蓝色地图"),
    BI_WHITE("BI_WHITE", "大屏白色地图"),
    BI_SATELLITE("BI_SATELLITE", "大屏卫星地图"),
    BI_PIPELINE("BI_PIPELINE", "大屏管网地图"),
    ADMIN_PIPELINE("ADMIN_PIPELINE", "后台管网地图"),
    ADMIN("ADMIN", "后台地图"),
    ;

    private String key;

    private String title;

    MapDataKeyEnum(String key, String title) {
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

    public static MapDataKeyEnum query(String key){
        if (key != null) {
            MapDataKeyEnum[] values = MapDataKeyEnum.values();
            for (MapDataKeyEnum result : values) {
                if (result.getKey().equals(key)) {
                    return result;
                }
            }
        }
        return null;
    }
}
