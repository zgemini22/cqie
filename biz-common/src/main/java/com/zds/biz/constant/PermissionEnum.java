package com.zds.biz.constant;

/**
 * 权限枚举
 */
public enum PermissionEnum implements BaseEnum<String>  {

    USER_SAVE_UPDATE("USER_SAVE_UPDATE", "manage:settingManage:user:edit"),
    ROLE_SAVE_UPDATE("ROLE_SAVE_UPDATE", "manage:settingManage:role:edit")
    ;

    private String key;

    private String title;

    PermissionEnum(String key, String title) {
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

    public static PermissionEnum query(String key) {
        if (key != null) {
            PermissionEnum[] values = PermissionEnum.values();
            for (PermissionEnum result : values) {
                if (result.getKey().equals(key)) {
                    return result;
                }
            }
        }
        return null;
    }
}