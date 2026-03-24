package com.zds.biz.constant;

/**
 * 图表数据集元素-类型枚举
 */
public enum SeriesTypeEnum implements BaseEnum<String> {

    BAR("柱状图","bar"),
    LINE("折线图","line"),
    ;

    private String title;

    private String key;

    SeriesTypeEnum(String title, String key) {
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

    public static SeriesTypeEnum queryByKey(String key){
        SeriesTypeEnum result = query(key);
        if (null == result) {
            result = SeriesTypeEnum.BAR;
        }
        return result;
    }

    private static SeriesTypeEnum query(String key){
        if (key != null && !"".equals(key)) {
            SeriesTypeEnum[] values = SeriesTypeEnum.values();
            for (SeriesTypeEnum result : values) {
                if (result.getKey().equals(key)) {
                    return result;
                }
            }
        }
        return null;
    }
}
