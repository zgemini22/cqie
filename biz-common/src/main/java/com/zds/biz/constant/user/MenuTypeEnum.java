package com.zds.biz.constant.user;

import com.zds.biz.constant.BaseEnum;

/**
 * 菜单类型,字典group_id=MENU_TYPE
 */
public enum MenuTypeEnum implements BaseEnum<String> {

    CATALOGUE("CATALOGUE", "目录"),
    MENU("MENU", "菜单"),
    BUTTON("BUTTON", "按钮"),
    ;

    private String key;

    private String title;

    MenuTypeEnum(String key, String title) {
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

    public static MenuTypeEnum query(String key){
        if (key != null) {
            MenuTypeEnum[] values = MenuTypeEnum.values();
            for (MenuTypeEnum result : values) {
                if (result.getKey().equals(key)) {
                    return result;
                }
            }
        }
        return null;
    }
}
