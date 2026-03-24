package com.zds.biz.constant.info;

import com.zds.biz.constant.BaseEnum;

public enum GasProjectFlowEnum implements BaseEnum<String> {

    AUDIT("1", "待发起"),
    REVIEW("2", "审核中"),
    COMPLETED("3", "已完结"),
    TERMINATION("4", "未通过"),
    ;
    private String key;

    private String title;

    GasProjectFlowEnum(String key, String title) {
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

    public static GasProjectFlowEnum query(String key){
        if (key != null) {
            GasProjectFlowEnum[] values = GasProjectFlowEnum.values();
            for (GasProjectFlowEnum result : values) {
                if (result.getKey().equals(key)) {
                    return result;
                }
            }
        }
        return null;
    }
}
