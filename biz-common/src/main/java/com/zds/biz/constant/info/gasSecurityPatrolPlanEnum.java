package com.zds.biz.constant.info;

import com.zds.biz.constant.BaseEnum;

public enum gasSecurityPatrolPlanEnum implements BaseEnum<String> {
    WAITINSPECTION("WAITINSPECTION", "待巡检"),
    BEOUT("BEOUT", ""),
    HIDDENDANGER("HIDDENDANGER","隐患"),
    NORMAL("NORMAL","正常")
    ;
    private String key;

    private String title;

    gasSecurityPatrolPlanEnum(String key, String title) {
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

    public static GasStationMachineTypeEnum query(String key){
        if (key != null) {
            GasStationMachineTypeEnum[] values = GasStationMachineTypeEnum.values();
            for (GasStationMachineTypeEnum result : values) {
                if (result.getKey().equals(key)) {
                    return result;
                }
            }
        }
        return null;
    }


    public static String getKey(String title) {
        gasSecurityPatrolPlanEnum[] values = values();
        for (gasSecurityPatrolPlanEnum value : values) {
            if (value.getTitle().equals(title)) {
                return value.getKey();
            }
        }
        return null;
    }

    public static String getTitle(String key) {
        gasSecurityPatrolPlanEnum[] values = values();
        for (gasSecurityPatrolPlanEnum value : values) {
            if (value.getKey().equals(key)) {
                return value.getTitle();
            }
        }
        return null;
    }
}
