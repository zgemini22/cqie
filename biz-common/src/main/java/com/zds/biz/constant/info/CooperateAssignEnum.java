package com.zds.biz.constant.info;

import com.zds.biz.constant.BaseEnum;

public enum  CooperateAssignEnum implements BaseEnum<Integer> {
    NOTASSIGN(1, "未指派"),
    ASSIGN(2, "指派"),
            ;

    private int key;

    private String msg;

    CooperateAssignEnum(int key, String msg){
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

    public static CooperateAssignEnum query(Integer key){
        if (key != null) {
            CooperateAssignEnum[] values = CooperateAssignEnum.values();
            for (CooperateAssignEnum result : values) {
                if (result.getKey().equals(key)) {
                    return result;
                }
            }
        }
        return null;
    }
}
