package com.zds.biz.constant.info;

import com.zds.biz.constant.BaseEnum;

/**
 * 资料配置项目类别枚举
 */
public enum GasProjectTypeEnum implements BaseEnum<String> {
    NEW_PIPE_NETWORD("NEW_PIPE_NETWORD","管网新建"),
    PIPE_REBUILD("PIPE_REBUILD","管网改建"),
    NEW_GASSTATION("NEW_GASSTATION","加气站新建"),
    NEW_TRANSMISSION("NEW_TRANSMISSION","输配站新建")

    ;
    private String key;

    private String title;

    GasProjectTypeEnum(String key, String title) {
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

    public static GasProjectTypeEnum query(Integer key){
        if (key != null) {
            GasProjectTypeEnum[] values = GasProjectTypeEnum.values();
            for (GasProjectTypeEnum result : values) {
                if (result.getKey().equals(key)) {
                    return result;
                }
            }
        }
        return null;
    }
}
