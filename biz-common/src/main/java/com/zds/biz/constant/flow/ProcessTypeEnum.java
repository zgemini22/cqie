package com.zds.biz.constant.flow;

import com.zds.biz.constant.BaseEnum;

/**
 * 流程类型枚举
 */
public enum ProcessTypeEnum implements BaseEnum<String> {
    PROJECT_APPROVAL_NEW_PIPE_FLOW("PROJECT_APPROVAL_NEW_PIPE_FLOW", "管网新建立项流程"),
    PROJECT_APPROVAL_PIPE_FLOW("PROJECT_APPROVAL_PIPE_FLOW", "管网改建立项流程"),
    PROJECT_APPROVAL_TRANSMISSION_FLOW("PROJECT_APPROVAL_TRANSMISSION_FLOW", "输配站新建立项流程"),
    PROJECT_APPROVAL_GASSTATION_FLOW("PROJECT_APPROVAL_GASSTATION_FLOW", "加气站新建立项流程"),

    PROJECT_INVEST_NEW_PIPE_FLOW("PROJECT_INVEST_NEW_PIPE_FLOW", "管网新建投资备案流程"),
    PROJECT_INVEST_PIPE_FLOW("PROJECT_INVEST_PIPE_FLOW", "管网改建投资备案流程"),
    PROJECT_INVEST_TRANSMISSION_FLOW("PROJECT_INVEST_TRANSMISSION_FLOW", "输配站新建投资备案流程"),
    PROJECT_INVEST_GASSTATION_FLOW("PROJECT_INVEST_GASSTATION_FLOW", "加气站新建投资备案流程"),

    PROJECT_LICENSE_NEW_PIPE_FLOW("PROJECT_LICENSE_NEW_PIPE_FLOW", "管网新建规划许可流程"),
    PROJECT_LICENSE_PIPE_FLOW("PROJECT_LICENSE_PIPE_FLOW", "管网改建规划许可流程"),
    PROJECT_LICENSE_TRANSMISSION_FLOW("PROJECT_LICENSE_TRANSMISSION_FLOW", "输配站新建规划许可流程"),
    PROJECT_LICENSE_GASSTATION_FLOW("PROJECT_LICENSE_GASSTATION_FLOW", "加气站新建规划许可流程"),

    PROJECT_EXCAVATE_NEW_PIPE_FLOW("PROJECT_EXCAVATE_NEW_PIPE_FLOW", "管网新建开挖许可流程"),
    PROJECT_EXCAVATE_PIPE_FLOW("PROJECT_EXCAVATE_PIPE_FLOW", "管网改建开挖许可流程"),
    PROJECT_EXCAVATE_TRANSMISSION_FLOW("PROJECT_EXCAVATE_TRANSMISSION_FLOW", "输配站新建开挖许可流程"),
    PROJECT_EXCAVATE_GASSTATION_FLOW("PROJECT_EXCAVATE_GASSTATION_FLOW", "加气站新建开挖许可流程"),
    GAS_SUPPLY("GAS_SUPPLY", "供气管理流程"),
    GAS_STOP("GAS_STOP", "停气管理流程"),
    GAS_SUPPLY_PLAN("GAS_SUPPLY_PLAN", "供气管理计划流程"),
    GAS_STOP_PLAN("GAS_STOP_PLAN", "停气管理计划流程"),

    ACCIDENT_LINKAGE_FLOW("ACCIDENT_LINKAGE_FLOW","事故处置");

    private String key;

    private String title;

    ProcessTypeEnum(String key, String title) {
        this.key = key;
        this.title = title;
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public String getTitle() {
        return title;
    }

    public static ProcessTypeEnum query(String key){
        if (key != null) {
            ProcessTypeEnum[] values = ProcessTypeEnum.values();
            for (ProcessTypeEnum result : values) {
                if (result.getKey().equals(key)) {
                    return result;
                }
            }
        }
        return null;
    }
}
