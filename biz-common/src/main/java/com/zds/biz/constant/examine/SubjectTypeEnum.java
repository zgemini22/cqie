package com.zds.biz.constant.examine;

import com.zds.biz.constant.BaseEnum;

public enum SubjectTypeEnum implements BaseEnum<Integer> {

    SINGLE(1,"单选题"),
    MULTIPLE(2,"多选题"),
    ANSWER(3,"问答题"),
    ;

    private Integer key;

    private String title;

    SubjectTypeEnum(Integer key, String title) {
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

    public static SubjectTypeEnum query(Integer key){
        if (key != null) {
            SubjectTypeEnum[] values = SubjectTypeEnum.values();
            for (SubjectTypeEnum result : values) {
                if (result.getKey().equals(key)) {
                    return result;
                }
            }
        }
        return null;
    }
}
