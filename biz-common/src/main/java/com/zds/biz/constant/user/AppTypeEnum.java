package com.zds.biz.constant.user;

import com.zds.biz.constant.BaseEnum;

/**
 * app类型,字典group_id=APP_TYPE
 */
public enum AppTypeEnum implements BaseEnum<String> {

    SYSTEM_APP("SYSTEM_APP", "燃气安全一件事管理系统APP"),
    EXAMINE_APP("EXAMINE_APP", "燃气从业资格考核管理系统APP"),
    ;

    private String key;

    private String title;

    AppTypeEnum(String key, String title) {
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

    public static AppTypeEnum query(String key){
        if (key != null) {
            AppTypeEnum[] values = AppTypeEnum.values();
            for (AppTypeEnum result : values) {
                if (result.getKey().equals(key)) {
                    return result;
                }
            }
        }
        return null;
    }
}
