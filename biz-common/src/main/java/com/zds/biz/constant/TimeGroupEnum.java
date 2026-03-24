package com.zds.biz.constant;

/**
 * 时间分组类型枚举
 */
public enum TimeGroupEnum implements BaseEnum<Integer> {

    DAY("日",1),
    MONTH("月",2),
    HOUR("小时",3),
    YEAR("年",4),
    WEEK("周",5),
    ;

    private String title;

    private int key;

    TimeGroupEnum(String title, int key) {
        this.key = key;
        this.title = title;
    }

    @Override
    public Integer getKey() {
        return this.key;
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    public static TimeGroupEnum queryByKey(Integer key){
        TimeGroupEnum result = query(key);
        if (null == result) {
            result = TimeGroupEnum.DAY;
        }
        return result;
    }

    private static TimeGroupEnum query(Integer key){
        if (key != null) {
            TimeGroupEnum[] values = TimeGroupEnum.values();
            for (TimeGroupEnum result : values) {
                if (result.getKey().equals(key)) {
                    return result;
                }
            }
        }
        return null;
    }
}
