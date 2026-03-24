package com.zds.biz.constant.info;

import com.zds.biz.constant.BaseEnum;

public enum ProjectProcessTypeEnum implements BaseEnum<Integer> {
    //1=工程报监 2=压力管道监督，3=风险作业管理，4=材料抽检，5=管沟质量监管，6=焊接质量监管
    REPORTING(1, "工程报监"),
    RISKWORK(3, "风险作业管理"),
    MATERIAL(4, "材料抽验"),
    PIPELINE(5, "管沟质量监管"),
    PRESSURE(2, "压力管道监督"),
    WELDQUALITY(6, "焊接质量监管"),
    ;

    private int key;

    private String msg;

    ProjectProcessTypeEnum(int key, String msg){
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

    public static ProjectProcessTypeEnum query(Integer key){
        if (key != null) {
            ProjectProcessTypeEnum[] values = ProjectProcessTypeEnum.values();
            for (ProjectProcessTypeEnum result : values) {
                if (result.getKey().equals(key)) {
                    return result;
                }
            }
        }
        return null;
    }
}
