package com.zds.biz.constant.info;

import com.zds.biz.constant.BaseEnum;

public enum CooperateReviewEnum implements BaseEnum<Integer> {
    NOTREVIEW(1, "待复查"),
    REVIEW(2, "已复查"),
            ;

    private int key;

    private String msg;

    CooperateReviewEnum(int key, String msg){
        this.key = key;
        this.msg = msg;
    }

    @Override
    public Integer getKey() {
        return this.key;
    }

    @Override
    public String getTitle() {
        return this.msg;
    }

    public static CooperateReviewEnum query(Integer key){
        if (key != null) {
            CooperateReviewEnum[] values = CooperateReviewEnum.values();
            for (CooperateReviewEnum result : values) {
                if (result.getKey().equals(key)) {
                    return result;
                }
            }
        }
        return null;
    }
}
