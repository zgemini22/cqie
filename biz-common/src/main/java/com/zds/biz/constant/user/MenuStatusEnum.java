package com.zds.biz.constant.user;

import com.zds.biz.constant.BaseEnum;

/**
 * 菜单状态,字典group_id=MENU_STATUS
 */
public enum MenuStatusEnum implements BaseEnum<String> {

    ENABLE("ENABLE", "启用"),
    DISABLE("DISABLE", "禁用"),
    ;

    private String key;

    private String title;

    MenuStatusEnum(String key, String title) {
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

    public static MenuStatusEnum query(String key){
        if (key != null) {
            MenuStatusEnum[] values = MenuStatusEnum.values();
            for (MenuStatusEnum result : values) {
                if (result.getKey().equals(key)) {
                    return result;
                }
            }
        }
        return null;
    }
}
