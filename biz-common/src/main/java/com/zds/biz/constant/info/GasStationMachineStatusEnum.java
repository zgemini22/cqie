package com.zds.biz.constant.info;

import com.zds.biz.constant.BaseEnum;

public enum GasStationMachineStatusEnum implements BaseEnum<Integer> {

    ONLINE(1, "在线"),
    OFFLINE(2, "离线"),
    ;
    private Integer key;

    private String title;

    GasStationMachineStatusEnum(Integer key, String title) {
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

    public static GasStationMachineStatusEnum query(Integer key){
        if (key != null) {
            GasStationMachineStatusEnum[] values = GasStationMachineStatusEnum.values();
            for (GasStationMachineStatusEnum result : values) {
                if (result.getKey().equals(key)) {
                    return result;
                }
            }
        }
        return null;
    }
}
