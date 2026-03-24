package com.zds.biz.constant.info;


import com.zds.biz.constant.BaseEnum;

import java.util.HashMap;
import java.util.Map;

/**
 * 隐患类型枚举
 */
public enum AreaLineTypeEnum implements BaseEnum<Integer> {

    PIPE(1, "管线"),
    PRESSURE(2, "调压箱"),
    GASBOX(3, "气表箱"),
    VALUE(4, "阀门"),
    ;

    private int key;

    private String msg;

    AreaLineTypeEnum(int key, String msg){
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

    public static AreaLineTypeEnum query(Integer key){
        if (key != null) {
            AreaLineTypeEnum[] values = AreaLineTypeEnum.values();
            for (AreaLineTypeEnum result : values) {
                if (result.getKey().equals(key)) {
                    return result;
                }
            }
        }
        return null;
    }

    public static Map<Integer,String> getMap(){
        Map<Integer,String> map = new HashMap<>();
        AreaLineTypeEnum[] values = AreaLineTypeEnum.values();
        for (AreaLineTypeEnum result : values) {
            if (!map.containsKey(result.getKey())) {
                map.put(result.getKey(), result.getTitle());
            }
        }
        return map;
    }
}
