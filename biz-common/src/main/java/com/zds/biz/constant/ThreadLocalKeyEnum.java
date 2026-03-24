package com.zds.biz.constant;

/**
 * 线程内部数据key枚举
 */
public enum ThreadLocalKeyEnum {

    USERID("userId"),
    TOKEN("token"),
    ORGANIZATIONID("organizationId"),
    ORGANIZATIONTYPE("organizationType"),
    ROLEID("roleId"),
    IPADDR("ipAddr"),
    IPPORT("ipPort"),
    USERTYPE("userType"),
    ROLETYPE("roleType"),
    HOST("host"),
    FeignClient("FeignClient"),
    ;

    private String key;

    ThreadLocalKeyEnum(String key) {
        this.key = key;
    }

    public String getKey() {
        return this.key;
    }
}
