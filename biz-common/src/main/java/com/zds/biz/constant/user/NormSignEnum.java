package com.zds.biz.constant.user;

import com.zds.biz.constant.BaseEnum;

/**
 * 指标类型,字典group_id=GAS_NORM_SIGN
 */
public enum NormSignEnum implements BaseEnum<String> {


    KPI_NORM("KPI_NORM", "KPI指标"),
    BUSINESS_NORM("BUSINESS_NORM", "业务指标"),
    ;

    private String key;

    private String title;

    NormSignEnum(String key, String title) {
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

    public static NormSignEnum query(String key){
        if (key != null) {
            NormSignEnum[] values = NormSignEnum.values();
            for (NormSignEnum result : values) {
                if (result.getKey().equals(key)) {
                    return result;
                }
            }
        }
        return null;
    }

}
