package com.zds.biz.constant.dispose;

import com.zds.biz.constant.BaseEnum;

/**
 * 事故类型枚举
 */
public enum AccidentTypeNameEnum implements BaseEnum<Integer> {

    STATION(1, "输配站"),
    FILL(2, "加气站"),
    PIPE(3, "管网"),
    PROJECT(4, "工程建设"),
    GAS(5, "用气安全"),
    TRIPARTITE(6, "第三方施工"),
    COOPERATE_WARN(7, "协同预警"),
    OTHER(8, "其他"),
    ;

    private int key;

    private String msg;

    AccidentTypeNameEnum(int key, String msg) {
        this.key = key;
        this.msg = msg;
    }

    @Override
    public Integer getKey() {
        return this.key;
    }

    @Override
    public String getTitle() {
        return this.msg;
    }

    public static AccidentTypeNameEnum query(Integer key) {
        if (key != null) {
            AccidentTypeNameEnum[] values = AccidentTypeNameEnum.values();
            for (AccidentTypeNameEnum result : values) {
                if (result.getKey().equals(key)) {
                    return result;
                }
            }
        }
        return null;
    }
}
