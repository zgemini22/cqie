package com.zds.biz.constant.user;

import com.zds.biz.constant.BaseEnum;

/**
 * 菜单分组,字典group_id=MENU_GROUP
 */
public enum MenuGroupEnum implements BaseEnum<String> {

    SYSTEM_MENU("SYSTEM_MENU", "燃气安全一件事管理系统"),
    EXAMINE_MENU("EXAMINE_MENU", "燃气从业资格考核管理系统"),
    DEVICE_MENU("DEVICE_MENU", "燃气设备全生命周期管理系统"),
    METER_MENU("METER_MENU", "抄表信息化管理系统"),

    SYSTEM_APP_MENU("SYSTEM_APP_MENU", "燃气安全监管(APP)"),
    EXAMINE_APP_MENU("EXAMINE_APP_MENU", "设备全生命周期管理(APP)"),
    ;

    private String key;

    private String title;

    MenuGroupEnum(String key, String title) {
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

    public static MenuGroupEnum query(String key){
        if (key != null) {
            MenuGroupEnum[] values = MenuGroupEnum.values();
            for (MenuGroupEnum result : values) {
                if (result.getKey().equals(key)) {
                    return result;
                }
            }
        }
        return null;
    }
}
