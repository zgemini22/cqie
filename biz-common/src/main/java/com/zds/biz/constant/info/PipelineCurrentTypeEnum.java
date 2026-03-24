package com.zds.biz.constant.info;

import com.zds.biz.constant.BaseEnum;

public enum PipelineCurrentTypeEnum implements BaseEnum<String> {
    CZ("CZ", "材质"),
    YL("YL", "压力"),
    LX("LX", "类型");

    private String key;

    private String msg;

    PipelineCurrentTypeEnum(String key, String msg){
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

    public static PipelineCurrentTypeEnum query(String key){
        if (key != null) {
            PipelineCurrentTypeEnum[] values = PipelineCurrentTypeEnum.values();
            for (PipelineCurrentTypeEnum result : values) {
                if (result.getKey().equals(key)) {
                    return result;
                }
            }
        }
        return null;
    }
}
