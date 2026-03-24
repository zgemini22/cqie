package com.zds.biz.constant.user;

import com.zds.biz.constant.BaseEnum;

/**
 * 消息分组,字典group_id=MESSAGE_GROUP
 */
public enum MessageGroupEnum implements BaseEnum<String> {

    SYSTEM_MESSAGE("SYSTEM_MESSAGE", "燃气安全一件事管理系统消息"),
    EXAMINE_MESSAGE("EXAMINE_MESSAGE", "燃气从业资格考核管理系统消息"),
    DEVICE_MESSAGE("DEVICE_MESSAGE", "燃气设备全生命周期管理系统消息"),
    METER_MESSAGE("METER_MESSAGE", "抄表信息化管理系统消息"),
    ;

    private String key;

    private String title;

    MessageGroupEnum(String key, String title) {
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

    public static MessageGroupEnum query(String key){
        if (key != null) {
            MessageGroupEnum[] values = MessageGroupEnum.values();
            for (MessageGroupEnum result : values) {
                if (result.getKey().equals(key)) {
                    return result;
                }
            }
        }
        return null;
    }
}
