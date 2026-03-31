package com.zds.biz.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum GasSupplyEnum {

    GAS_TYPE_NATURAL("27", "天然气"),
    GAS_TYPE_LIQUEFIED("28", "液化气"),

    SUPPLY_TYPE_NEW("NEW", "新建供气"),
    SUPPLY_TYPE_RESTORE("RESTORE", "恢复供气"),

    STATUS_UNSUBMITTED("UNSUBMITTED", "未提交"),
    STATUS_PENDING("PENDING", "待审批"),
    STATUS_APPROVED("APPROVED", "已审批"),
    STATUS_REJECTED("REJECTED", "已驳回");

    private final String code;
    private final String name;

    public static String getGasTypeName(String code) {
        if (GAS_TYPE_NATURAL.getCode().equals(code)) return GAS_TYPE_NATURAL.getName();
        if (GAS_TYPE_LIQUEFIED.getCode().equals(code)) return GAS_TYPE_LIQUEFIED.getName();
        return "";
    }

    public static String getSupplyTypeName(String code) {
        if (SUPPLY_TYPE_NEW.getCode().equals(code)) return SUPPLY_TYPE_NEW.getName();
        if (SUPPLY_TYPE_RESTORE.getCode().equals(code)) return SUPPLY_TYPE_RESTORE.getName();
        return "";
    }

    public static String getStatusName(String code) {
        if (STATUS_UNSUBMITTED.getCode().equals(code)) return STATUS_UNSUBMITTED.getName();
        if (STATUS_PENDING.getCode().equals(code)) return STATUS_PENDING.getName();
        if (STATUS_APPROVED.getCode().equals(code)) return STATUS_APPROVED.getName();
        if (STATUS_REJECTED.getCode().equals(code)) return STATUS_REJECTED.getName();
        return "";
    }
}
