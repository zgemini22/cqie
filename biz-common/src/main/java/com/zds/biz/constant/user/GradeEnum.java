package com.zds.biz.constant.user;

import com.zds.biz.constant.BaseEnum;

public enum GradeEnum implements BaseEnum<Integer> {
    ONE(1, "1级"),
    TWO(2, "2级"),
    THREE(3, "3级"),
    FOUR(4, "标杆值"),
    ;

    private Integer key;

    private String title;

    GradeEnum(Integer key, String title) {
        this.key = key;
        this.title = title;
    }

    @Override
    public Integer getKey() {
        return key;
    }

    @Override
    public String getTitle() {
        return title;
    }

    public static GradeEnum query(Integer key){
        if (key != null) {
            GradeEnum[] values = GradeEnum.values();
            for (GradeEnum result : values) {
                if (result.getKey().equals(key)) {
                    return result;
                }
            }
        }
        return null;
    }
}
