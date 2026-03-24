package com.zds.biz.constant;

/**
 * 图表轴设置-类型枚举
 */
public enum AxisTypeEnum implements BaseEnum<String> {

    CATEGORY("类目","category"),
    VALUE("数值","value"),
    ;

    private String title;

    private String key;

    AxisTypeEnum(String title, String key) {
        this.key = key;
        this.title = title;
    }

    @Override
    public String getKey() {
        return this.key;
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    public static AxisTypeEnum queryByKey(String key){
        AxisTypeEnum result = query(key);
        if (null == result) {
            result = AxisTypeEnum.CATEGORY;
        }
        return result;
    }

    private static AxisTypeEnum query(String key){
        if (key != null && !"".equals(key)) {
            AxisTypeEnum[] values = AxisTypeEnum.values();
            for (AxisTypeEnum result : values) {
                if (result.getKey().equals(key)) {
                    return result;
                }
            }
        }
        return null;
    }
}
