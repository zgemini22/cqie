package com.zds.biz.constant.examine;

import com.zds.biz.constant.BaseEnum;

public enum ExamineStatusEnum implements BaseEnum<Integer> {

    ENABLE(1, "启用"),
    DISABLE(2, "禁用"),
    ;

    private Integer key;

    private String title;

    ExamineStatusEnum(Integer key, String title) {
        this.key = key;
        this.title = title;
    }

    @Override
    public Integer getKey() {
        return this.key;
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    public static ExamineStatusEnum query(Integer key){
        if (key != null) {
            ExamineStatusEnum[] values = ExamineStatusEnum.values();
            for (ExamineStatusEnum result : values) {
                if (result.getKey().equals(key)) {
                    return result;
                }
            }
        }
        return null;
    }
}
