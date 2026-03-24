package com.zds.biz.constant.info;

import com.zds.biz.constant.BaseEnum;

/**
 * 协同文件枚举
 */
public enum CooperateFileEnum implements BaseEnum<Integer> {
    CLUE_INFORMATION(1, "线索资料"),
    RECTIFICATION_NOTICE(2, "整改通知"),
    PENALTY_NOTICE(3, "处罚通知"),
    COMPLETION(4, "办结"),
    ;

    private int key;

    private String msg;

    CooperateFileEnum(int key, String msg){
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

    public static CooperateFileEnum query(Integer key){
        if (key != null) {
            CooperateFileEnum[] values = CooperateFileEnum.values();
            for (CooperateFileEnum result : values) {
                if (result.getKey().equals(key)) {
                    return result;
                }
            }
        }
        return null;
    }
}
