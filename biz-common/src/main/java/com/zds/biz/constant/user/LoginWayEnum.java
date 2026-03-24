package com.zds.biz.constant.user;

import com.zds.biz.constant.BaseEnum;

/**
 * 登录渠道枚举
 */
public enum LoginWayEnum implements BaseEnum<Integer> {

    ADMIN_WEB(1, "管理后台web"),
    CLIENT_APP(2, "客户端APP"),
    DEVICE_WEB(3, "设备管理web"),
    EXAMINE_WEB(4, "从业考核web"),
    EXAMINE_APP(5, "从业考核APP"),
    METER_WEB(6, "抄表信息化web"),
    ;

    private Integer key;

    private String title;

    LoginWayEnum(Integer key, String title) {
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

    public static LoginWayEnum query(Integer key){
        if (key != null) {
            LoginWayEnum[] values = LoginWayEnum.values();
            for (LoginWayEnum result : values) {
                if (result.getKey().equals(key)) {
                    return result;
                }
            }
        }
        return null;
    }
}
