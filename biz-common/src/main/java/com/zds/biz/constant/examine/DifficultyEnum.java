package com.zds.biz.constant.examine;

import com.zds.biz.constant.BaseEnum;

public enum DifficultyEnum implements BaseEnum<Integer> {

    EASY(1,"简单"),
    GENERAL(2,"一般"),
    DIFFICULT(3,"困难"),
    ;

    private Integer key;

    private String title;

    DifficultyEnum(Integer key, String title) {
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

    public static DifficultyEnum query(Integer key){
        if (key != null) {
            DifficultyEnum[] values = DifficultyEnum.values();
            for (DifficultyEnum result : values) {
                if (result.getKey().equals(key)) {
                    return result;
                }
            }
        }
        return null;
    }
}
