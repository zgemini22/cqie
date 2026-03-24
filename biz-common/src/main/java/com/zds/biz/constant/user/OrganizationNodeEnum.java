package com.zds.biz.constant.user;

/**
 * 系统管理组组织ID枚举
 */
public enum OrganizationNodeEnum {

    ROOT_COMPANY_CODE(0L,"系统管理组");

    private Long code;

    private String name;

    OrganizationNodeEnum(Long code, String name) {
        this.code = code;
        this.name = name;
    }

    public Long getCode() {
        return code;
    }

    public void setCode(Long code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
