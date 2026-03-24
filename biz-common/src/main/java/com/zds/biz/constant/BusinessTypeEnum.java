package com.zds.biz.constant;

public enum BusinessTypeEnum implements BaseEnum<Integer> {
    PROJECTAPPROVAL(1, "立项"),
    PROJECTINVEST(2, "投资备案"),
    PROJECTLICENSE(3, "规划许可"),
    PROJECTEXCAVATE(4, "开挖许可"),
    ;

    private Integer key;

    private String title;

    BusinessTypeEnum(Integer key, String title) {
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

    public static BusinessTypeEnum query(Integer key){
        if (key != null) {
            BusinessTypeEnum[] values = BusinessTypeEnum.values();
            for (BusinessTypeEnum result : values) {
                if (result.getKey().equals(key)) {
                    return result;
                }
            }
        }
        return null;
    }
}
