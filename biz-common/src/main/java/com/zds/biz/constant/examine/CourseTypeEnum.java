package com.zds.biz.constant.examine;

import com.zds.biz.constant.BaseEnum;

/**
 * 课程类型枚举
 */
public enum CourseTypeEnum implements BaseEnum<String> {
    SECURITY("SECURITY" , "安全类"),
    POLICY("POLICY" , "政策类")
    ;

    private String key;

    private String title;

    CourseTypeEnum(String key, String title) {
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

    public static CourseTypeEnum query(String key){
        if (key != null) {
            CourseTypeEnum[] values = CourseTypeEnum.values();
            for (CourseTypeEnum result : values) {
                if (result.getKey().equals(key)) {
                    return result;
                }
            }
        }
        return null;
    }
}
