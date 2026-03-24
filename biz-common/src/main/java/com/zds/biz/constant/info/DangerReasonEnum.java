package com.zds.biz.constant.info;


import com.zds.biz.constant.BaseEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * 预警-隐患原因类型枚举
 */
public enum DangerReasonEnum implements BaseEnum<String> {
    PIPELINE_AGING("PIPELINE_AGING", "管道老化"),
    VIOLATION_OPERATE("VIOLATION_OPERATE", "违规操作"),
    PIPELINE_OVERPRESSURE("PIPELINE_OVERPRESSURE", "管路超压"),
    PIPELINE_REMOVAL("PIPELINE_REMOVAL", "管道除锈"),
    MISSING_COATING("MISSING_COATING", "防腐层缺失"),
    THIRD_CONSTRUCT("THIRD_CONSTRUCT", "第三方施工"),
    OTHER("OTHER", "其他"),
    ;

    private String key;

    private String msg;

    DangerReasonEnum(String key, String msg) {
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

    public static DangerReasonEnum query(String key) {
        if (key != null) {
            DangerReasonEnum[] values = DangerReasonEnum.values();
            for (DangerReasonEnum result : values) {
                if (result.getKey().equals(key)) {
                    return result;
                }
            }
        }
        return null;
    }

    public static Map<String, String> getMap() {
        Map<String, String> map = new HashMap<>();
        DangerReasonEnum[] values = DangerReasonEnum.values();
        for (DangerReasonEnum result : values) {
            if (!map.containsKey(result.getKey())) {
                map.put(result.getKey(), result.getTitle());
            }
        }
        return map;
    }
}
