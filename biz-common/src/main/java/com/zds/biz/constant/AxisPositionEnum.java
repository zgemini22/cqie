package com.zds.biz.constant;

/**
 * 图表轴设置-位置枚举
 */
public enum AxisPositionEnum implements BaseEnum<String> {

    LEFT("左","left"),
    RIGHT("右","right"),
    ;

    private String title;

    private String key;

    AxisPositionEnum(String title, String key) {
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

    public static AxisPositionEnum queryByKey(String key){
        AxisPositionEnum result = query(key);
        if (null == result) {
            result = AxisPositionEnum.LEFT;
        }
        return result;
    }

    private static AxisPositionEnum query(String key){
        if (key != null && !"".equals(key)) {
            AxisPositionEnum[] values = AxisPositionEnum.values();
            for (AxisPositionEnum result : values) {
                if (result.getKey().equals(key)) {
                    return result;
                }
            }
        }
        return null;
    }
}
