package com.zds.biz.constant.meter;

import com.zds.biz.constant.BaseEnum;

/**
 * 缴费类型
 */
public enum FeeTypeEnum implements BaseEnum<Integer> {

    ALIPAY(1, "支付宝"),
    WECHAT(2, "微信"),
    CASH(3, "现金"),
    CARD(4, "银行卡"),
    OTHER(5, "其它"),
    ;

    private int key;

    private String msg;

    FeeTypeEnum(int key, String msg) {
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

    public static FeeTypeEnum query(Integer key) {
        if (key != null) {
            FeeTypeEnum[] values = FeeTypeEnum.values();
            for (FeeTypeEnum result : values) {
                if (result.getKey().equals(key)) {
                    return result;
                }
            }
        }
        return null;
    }
}
