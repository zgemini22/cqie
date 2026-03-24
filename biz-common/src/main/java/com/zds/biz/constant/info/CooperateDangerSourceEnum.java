package com.zds.biz.constant.info;

import com.zds.biz.constant.BaseEnum;

/**
 * 协同隐患来源枚举，字典group_id=COOPERATE_SOURCE
 */
public enum CooperateDangerSourceEnum implements BaseEnum<String> {
    POLICE119("POLICE119", "119报警"),
    POLICE110("POLICE110", "110报警"),
    POLICE12345("POLICE12345", "12345热线"),
    POLICE12319("POLICE12319", "12319热线"),
    DEVICEDETECTION("DEVICEDETECTION", "设备检测"),
    PUBLICREPORTING("PUBLICREPORTING", "群众上报"),
    OTHER("OTHER", "其他"),

    COOPERATE_ENFORCE("COOPERATE_ENFORCE" , "协同执法"),

    COOPERATE_WARNING("COOPERATE_WARNING" , "协同预警")
    ;
    private String key;

    private String msg;

    CooperateDangerSourceEnum(String key, String msg){
        this.key = key;
        this.msg = msg;
    }

    @Override
    public String getKey() {
        return this.key;
    }

    @Override
    public String getTitle() {
        return this.msg;
    }

    public static CooperateDangerSourceEnum query(String key){
        if (key != null) {
            CooperateDangerSourceEnum[] values = CooperateDangerSourceEnum.values();
            for (CooperateDangerSourceEnum result : values) {
                if (result.getKey().equals(key)) {
                    return result;
                }
            }
        }
        return null;
    }
}
