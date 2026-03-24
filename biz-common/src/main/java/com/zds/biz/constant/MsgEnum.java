package com.zds.biz.constant;

/**
 * 提升语句枚举
 */
public enum MsgEnum {
    ERROR_ACCOUNT_PASSWORD("用户账号或密码错误"),
    ;

    private final String msg;

    MsgEnum(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return this.msg;
    }
}
