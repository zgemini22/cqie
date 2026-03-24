package com.zds.biz.constant.info;

import com.zds.biz.constant.BaseEnum;

public enum ProjectCompletedTypeEnum implements BaseEnum<Integer> {

    MEASURE(1, "竣工测量"),
    REVIEW(2, "档案审查"),
    CHECK(3, "组织验收"),
    ACCESS(4, "碰头接入"),
    RECORD(5, "竣工验收备案"),
    ;

    private int key;

    private String msg;

    ProjectCompletedTypeEnum(int key, String msg){
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

    public static ProjectCompletedTypeEnum query(Integer key){
        if (key != null) {
            ProjectCompletedTypeEnum[] values = ProjectCompletedTypeEnum.values();
            for (ProjectCompletedTypeEnum result : values) {
                if (result.getKey().equals(key)) {
                    return result;
                }
            }
        }
        return null;
    }
}
