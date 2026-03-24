package com.zds.biz.constant.dispose;

import com.zds.biz.constant.BaseEnum;

public enum SzCqFlowEnum implements BaseEnum<String> {

    SZCQFLOW_1("ACCIDENT_SZCQ_FLOW_1", "数字重庆一般事故处置流程"),
    SZCQFLOW_2("ACCIDENT_SZCQ_FLOW_2", "数字重庆较大事故处置流程"),
    ;

    private String key;

    private String msg;

    SzCqFlowEnum(String key, String msg) {
        this.key = key;
        this.msg = msg;
    }

    @Override
    public String getKey() {
        return this.key;
    }

    @Override
    public String getTitle() {
        return this.msg;
    }

    public static SzCqFlowEnum query(String key) {
        if (key != null) {
            SzCqFlowEnum[] values = SzCqFlowEnum.values();
            for (SzCqFlowEnum result : values) {
                if (result.getKey().equals(key)) {
                    return result;
                }
            }
        }
        return null;
    }
}
