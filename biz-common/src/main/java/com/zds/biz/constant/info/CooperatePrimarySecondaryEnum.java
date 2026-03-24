package com.zds.biz.constant.info;

import com.zds.biz.constant.BaseEnum;

public enum CooperatePrimarySecondaryEnum implements BaseEnum<Integer> {
    HOST(1, "主"),
    ORDER(2, "次"),
            ;

    private int key;

    private String msg;

    CooperatePrimarySecondaryEnum(int key, String msg){
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

    public static CooperatePrimarySecondaryEnum query(Integer key){
        if (key != null) {
            CooperatePrimarySecondaryEnum[] values = CooperatePrimarySecondaryEnum.values();
            for (CooperatePrimarySecondaryEnum result : values) {
                if (result.getKey().equals(key)) {
                    return result;
                }
            }
        }
        return null;
    }
}
