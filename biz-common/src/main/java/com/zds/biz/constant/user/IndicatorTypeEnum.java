package com.zds.biz.constant.user;

import com.zds.biz.constant.BaseEnum;

/**
 * 指标标识,字典group_id=GAS_INDICATOR_TYPE
 */
public enum IndicatorTypeEnum implements BaseEnum<String> {

    BASIC_NORM("BASIC_NORM", "基础指标"),
    COMPOSITE_NORM("COMPOSITE_NORM", "复合指标"),
    ;

    private String key;

    private String title;

    IndicatorTypeEnum(String key, String title) {
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

    public static IndicatorTypeEnum query(String key){
        if (key != null) {
            IndicatorTypeEnum[] values = IndicatorTypeEnum.values();
            for (IndicatorTypeEnum result : values) {
                if (result.getKey().equals(key)) {
                    return result;
                }
            }
        }
        return null;
    }

}
