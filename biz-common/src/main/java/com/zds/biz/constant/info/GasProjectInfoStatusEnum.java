package com.zds.biz.constant.info;

import com.zds.biz.constant.BaseEnum;

public enum GasProjectInfoStatusEnum implements BaseEnum<Integer> {
    DRAFT(0, "项目筹划"),
    APPROVAL(1, "立项"),
    INVEST(2, "投资备案"),
    LICENSE(3, "规划许可"),
    EXCAVATE(4, "开挖许可"),
    BREAKGROUND(5, "工程开工"),
    PROCESSQUALITY(6, "过程质量"),
    COMPLETED(7, "竣工验收"),
    COMPLETEDEND(8, "竣工"),
            ;
    private Integer key;

    private String title;

    GasProjectInfoStatusEnum(Integer key, String title) {
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

    public static GasProjectInfoStatusEnum query(Integer key){
        if (key != null) {
            GasProjectInfoStatusEnum[] values = GasProjectInfoStatusEnum.values();
            for (GasProjectInfoStatusEnum result : values) {
                if (result.getKey().equals(key)) {
                    return result;
                }
            }
        }
        return null;
    }
}
