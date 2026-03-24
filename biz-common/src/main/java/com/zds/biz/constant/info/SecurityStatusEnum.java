package com.zds.biz.constant.info;


import com.zds.biz.constant.BaseEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * 用气安全巡检记录-巡检状态枚举,字典group_id=SECURITY_STATUS
 */
public enum SecurityStatusEnum implements BaseEnum<String> {
    WAITINSPECTION("WAITINSPECTION", "待巡检"),
    BEOUT("BEOUT", "不在"),
    HIDDENDANGER("HIDDENDANGER", "隐患"),
    TIMEOUT("TIMEOUT", "超时"),
    NORMAL("NORMAL", "正常");

    /*EXPIRED("EXPIRED", "超期");*/

    private String key;

    private String msg;

    SecurityStatusEnum(String key, String msg) {
        this.key = key;
        this.msg = msg;
    }

    @Override
    public String getKey() {
        return this.key;
    }

    @Override
    public String getTitle() {
        return this.msg;
    }

    public static SecurityStatusEnum query(String key) {
        if (key != null) {
            SecurityStatusEnum[] values = SecurityStatusEnum.values();
            for (SecurityStatusEnum result : values) {
                if (result.getKey().equals(key)) {
                    return result;
                }
            }
        }
        return null;
    }

    public static Map<String, String> getMap() {
        Map<String, String> map = new HashMap<>();
        SecurityStatusEnum[] values = SecurityStatusEnum.values();
        for (SecurityStatusEnum result : values) {
            if (!map.containsKey(result.getKey())) {
                map.put(result.getKey(), result.getTitle());
            }
        }
        return map;
    }
}
