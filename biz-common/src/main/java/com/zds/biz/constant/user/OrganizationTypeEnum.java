package com.zds.biz.constant.user;

import com.zds.biz.constant.BaseEnum;

/**
 * 单位类别,字典group_id=ORGANIZATION_TYPE
 */
public enum OrganizationTypeEnum implements BaseEnum<String> {

    SYSTEM_SAAS("SYSTEM_SAAS", "系统"),
    GOVERNMENT("GOVERNMENT", "政府"),
    COMPANY("COMPANY", "企业"),
    ;

    private String key;

    private String title;

    OrganizationTypeEnum(String key, String title) {
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

    public static OrganizationTypeEnum query(String key){
        if (key != null) {
            OrganizationTypeEnum[] values = OrganizationTypeEnum.values();
            for (OrganizationTypeEnum result : values) {
                if (result.getKey().equals(key)) {
                    return result;
                }
            }
        }
        return null;
    }
}
