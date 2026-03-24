package com.zds.biz.constant.user;

import com.zds.biz.constant.BaseEnum;

/**
 * 组织状态,字典group_id=ORGANIZATION_STATUS
 */
public enum OrganizationStatusEnum implements BaseEnum<String> {

    ENABLE("ENABLE", "启用"),
    DISABLE("DISABLE", "禁用"),
    FROZEN("FROZEN", "冻结"),
    ;

    private String key;

    private String title;

    OrganizationStatusEnum(String key, String title) {
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

    public static OrganizationStatusEnum query(String key){
        if (key != null) {
            OrganizationStatusEnum[] values = OrganizationStatusEnum.values();
            for (OrganizationStatusEnum result : values) {
                if (result.getKey().equals(key)) {
                    return result;
                }
            }
        }
        return null;
    }
}
