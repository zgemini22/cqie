package com.zds.biz.constant.dispose;

import com.zds.biz.constant.BaseEnum;

/**
 * 预案管理类型枚举
 */
public enum PlanManagerTypeEnum implements BaseEnum<String> {

    CP_PLAN_TYPE("CP_PLAN_TYPE", "综合应急预案"),
    SP_PLAN_TYPE("SP_PLAN_TYPE", "专项应急预案"),
    SCENE_PLAN_TYPE("SCENE_PLAN_TYPE", "现场处置方案"),
    ;

    private String key;

    private String title;

    PlanManagerTypeEnum(String key, String title) {
        this.key = key;
        this.title = title;
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public String getTitle() {
        return title;
    }

    public static PlanManagerTypeEnum query(String key) {
        if (key != null) {
            PlanManagerTypeEnum[] values = PlanManagerTypeEnum.values();
            for (PlanManagerTypeEnum result : values) {
                if (result.getKey().equals(key)) {
                    return result;
                }
            }
        }
        return null;
    }
}
