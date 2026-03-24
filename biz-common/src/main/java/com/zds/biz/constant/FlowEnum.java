package com.zds.biz.constant;

/**
 * 流程定义枚举
 * */
public enum FlowEnum implements BaseEnum<String>  {
    PROJECT_APPROVAL_FLOW("APPROVAL", "立项"),
    PROJECT_INVEST_FLOW("INVEST", "投资备案"),
    PROJECT_LICENSE_FLOW("LICENSE", "规划许可"),
    PROJECT_EXCAVATE_FLOW("EXCAVATE", "开挖许可"),
    ACCIDENT_LINKAGE_FLOW("ACCIDENT_LINKAGE_FLOW","事故处置")
    ;

    private String key;

    private String title;

    FlowEnum(String key, String title) {
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

    public static FlowEnum query(String key){
        if (key != null) {
            FlowEnum[] values = FlowEnum.values();
            for (FlowEnum result : values) {
                if (result.getKey().equals(key)) {
                    return result;
                }
            }
        }
        return null;
    }
}
