package com.zds.biz.constant.dispose;

import com.zds.biz.constant.BaseEnum;

/**
 * 预案管理状态
 */
public enum PlanManagerStatusEnum implements BaseEnum<Integer> {

    VALID(1, "有效"),
    INVALID(2, "无效");

    private int key;

    private String msg;

    PlanManagerStatusEnum(int key, String msg){
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

    public static PlanManagerStatusEnum query(Integer key) {
        if (key != null) {
            PlanManagerStatusEnum[] values = PlanManagerStatusEnum.values();
            for (PlanManagerStatusEnum result : values) {
                if (result.getKey().equals(key)) {
                    return result;
                }
            }
        }
        return null;
    }
}
