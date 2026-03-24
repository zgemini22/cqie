package com.zds.biz.constant.flow;

import com.zds.biz.constant.BaseEnum;

/**
 * 流程状态枚举
 */
public enum ProcessStatusEnum implements BaseEnum<String> {

    PUBLISHED("1","已发布"),
    UNPUBLISHED("2","未发布");

    private String key;

    private String title;

    ProcessStatusEnum(String key, String title) {
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

    public static ProcessStatusEnum query(String key){
        if (key != null) {
            ProcessStatusEnum[] values = ProcessStatusEnum.values();
            for (ProcessStatusEnum result : values) {
                if (result.getKey().equals(key)) {
                    return result;
                }
            }
        }
        return null;
    }
}
