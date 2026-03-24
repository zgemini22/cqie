package com.zds.biz.constant.user;

import com.zds.biz.constant.BaseEnum;

/**
 * 指标单位,字典group_id=GAS_NORM_UNIT
 */
public enum NormUnitEnum implements BaseEnum<String> {


    NORM_PERCENTAGE("NORM_PERCENTAGE", "%"),
    NORM_INDIVIDUAL("NORM_INDIVIDUAL", "个"),
    NORM_KM("NORM_KM", "KM"),
    NORM_DAY("NORM_DAY", "天"),
    ;

    private String key;

    private String title;

    NormUnitEnum(String key, String title) {
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

    public static NormUnitEnum query(String key){
        if (key != null) {
            NormUnitEnum[] values = NormUnitEnum.values();
            for (NormUnitEnum result : values) {
                if (result.getKey().equals(key)) {
                    return result;
                }
            }
        }
        return null;
    }

}
