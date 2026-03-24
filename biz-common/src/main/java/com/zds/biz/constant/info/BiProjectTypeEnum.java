package com.zds.biz.constant.info;


import com.zds.biz.constant.BaseEnum;

public enum BiProjectTypeEnum implements BaseEnum<Integer> {
    APPROVEPROJECT(1, "审批项目"),
    CONSTRUCTIONPROJECT(2, "在建项目"),
    COMPLETEDPROJECT(3, "竣工项目"),
    ;

    private int key;

    private String msg;

    BiProjectTypeEnum(int key, String msg){
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

    public static BiProjectTypeEnum query(Integer key){
        if (key != null) {
            BiProjectTypeEnum[] values = BiProjectTypeEnum.values();
            for (BiProjectTypeEnum result : values) {
                if (result.getKey().equals(key)) {
                    return result;
                }
            }
        }
        return null;
    }
}
