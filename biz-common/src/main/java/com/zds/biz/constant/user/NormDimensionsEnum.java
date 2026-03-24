package com.zds.biz.constant.user;

import com.zds.biz.constant.BaseEnum;

/**
 * 指标细化纬度,字典group_id=GAS_NORM_DIMENSIONS
 */
public enum NormDimensionsEnum implements BaseEnum<String> {


    NORM_URBAN_COMPANY("NORM_URBAN_COMPANY", "城燃企业"),
    NORM_STREET("NORM_STREET", "镇街"),
    ;

    private String key;

    private String title;

    NormDimensionsEnum(String key, String title) {
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

    public static NormDimensionsEnum query(String key){
        if (key != null) {
            NormDimensionsEnum[] values = NormDimensionsEnum.values();
            for (NormDimensionsEnum result : values) {
                if (result.getKey().equals(key)) {
                    return result;
                }
            }
        }
        return null;
    }

}
