package com.zds.biz.constant.dispose;

import com.zds.biz.constant.BaseEnum;

public enum SzCqApiTypeEnum implements BaseEnum<Integer> {
    //接口类型,1:事件上报,2:事件更新,3:事件查询
    EVENTREPORT(1, "事件上报"),
    EVENTUPDATE(2, "事件更新"),
    EVENTQUERY(3, "事件查询"),
    ;

    private int key;

    private String msg;

    SzCqApiTypeEnum(int key, String msg) {
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

    public static SzCqApiTypeEnum query(Integer key) {
        if (key != null) {
            SzCqApiTypeEnum[] values = SzCqApiTypeEnum.values();
            for (SzCqApiTypeEnum result : values) {
                if (result.getKey().equals(key)) {
                    return result;
                }
            }
        }
        return null;
    }
}
