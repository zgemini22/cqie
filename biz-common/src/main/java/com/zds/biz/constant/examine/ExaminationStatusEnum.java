package com.zds.biz.constant.examine;

import com.zds.biz.constant.BaseEnum;

public enum ExaminationStatusEnum implements BaseEnum<Integer> {
    //1=未发布，2=待报名，3=报名中，4=待考试，5=考试中，6=已结束
    UNPUBLISHED(1, "未发布"),
    PENDING(2, "待报名"),
    PROGRESS(3, "报名中"),
    TESTED(4, "待考试"),
    EXAM(5, "考试中"),
    END(6, "已结束"),
            ;

    private Integer key;

    private String title;

    ExaminationStatusEnum(Integer key, String title) {
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

    public static ExaminationStatusEnum query(Integer key){
        if (key != null) {
            ExaminationStatusEnum[] values = ExaminationStatusEnum.values();
            for (ExaminationStatusEnum result : values) {
                if (result.getKey().equals(key)) {
                    return result;
                }
            }
        }
        return null;
    }
}
