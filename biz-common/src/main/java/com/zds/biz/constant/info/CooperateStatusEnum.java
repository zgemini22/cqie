package com.zds.biz.constant.info;

import com.zds.biz.constant.BaseEnum;

public enum CooperateStatusEnum implements BaseEnum<Integer> {
    REGISTRATION(1, "登记"),
    REVIEW(2, "复查"),
    COMPLETION(3, "办结"),
            ;

    private int key;

    private String msg;

    CooperateStatusEnum(int key, String msg){
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

    public static CooperateStatusEnum query(Integer key){
        if (key != null) {
            CooperateStatusEnum[] values = CooperateStatusEnum.values();
            for (CooperateStatusEnum result : values) {
                if (result.getKey().equals(key)) {
                    return result;
                }
            }
        }
        return null;
    }
}
