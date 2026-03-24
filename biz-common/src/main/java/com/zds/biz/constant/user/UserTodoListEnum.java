package com.zds.biz.constant.user;

import com.zds.biz.constant.BaseEnum;
import com.zds.biz.constant.info.DangerBusinessTypeEnum;

import java.util.ArrayList;
import java.util.List;

/**
 * 待办类型枚举,字典group_id=TODO_LIST
 */
public enum UserTodoListEnum implements BaseEnum<String> {

    //流程待办
    PROJECT_APPROVAL_NEW_PIPE_FLOW("PROJECT_APPROVAL_NEW_PIPE_FLOW", "管网新建立项流程", 1, 2),
    PROJECT_APPROVAL_PIPE_FLOW("PROJECT_APPROVAL_PIPE_FLOW", "管网改建立项流程", 1, 2),
    PROJECT_APPROVAL_TRANSMISSION_FLOW("PROJECT_APPROVAL_TRANSMISSION_FLOW", "输配站新建立项流程", 1, 2),
    PROJECT_APPROVAL_GASSTATION_FLOW("PROJECT_APPROVAL_GASSTATION_FLOW", "加气站新建立项流程", 1, 2),

    PROJECT_INVEST_NEW_PIPE_FLOW("PROJECT_INVEST_NEW_PIPE_FLOW", "管网新建投资备案流程", 1, 2),
    PROJECT_INVEST_PIPE_FLOW("PROJECT_INVEST_PIPE_FLOW", "管网改建投资备案流程", 1, 2),
    PROJECT_INVEST_TRANSMISSION_FLOW("PROJECT_INVEST_TRANSMISSION_FLOW", "输配站新建投资备案流程", 1, 2),
    PROJECT_INVEST_GASSTATION_FLOW("PROJECT_INVEST_GASSTATION_FLOW", "加气站新建投资备案流程", 1, 2),

    PROJECT_LICENSE_NEW_PIPE_FLOW("PROJECT_LICENSE_NEW_PIPE_FLOW", "管网新建规划许可流程", 1, 2),
    PROJECT_LICENSE_PIPE_FLOW("PROJECT_LICENSE_PIPE_FLOW", "管网改建规划许可流程", 1, 2),
    PROJECT_LICENSE_TRANSMISSION_FLOW("PROJECT_LICENSE_TRANSMISSION_FLOW", "输配站新建规划许可流程", 1, 2),
    PROJECT_LICENSE_GASSTATION_FLOW("PROJECT_LICENSE_GASSTATION_FLOW", "加气站新建规划许可流程", 1, 2),

    PROJECT_EXCAVATE_NEW_PIPE_FLOW("PROJECT_EXCAVATE_NEW_PIPE_FLOW", "管网新建开挖许可流程", 1, 2),
    PROJECT_EXCAVATE_PIPE_FLOW("PROJECT_EXCAVATE_PIPE_FLOW", "管网改建开挖许可流程", 1, 2),
    PROJECT_EXCAVATE_TRANSMISSION_FLOW("PROJECT_EXCAVATE_TRANSMISSION_FLOW", "输配站新建开挖许可流程", 1, 2),
    PROJECT_EXCAVATE_GASSTATION_FLOW("PROJECT_EXCAVATE_GASSTATION_FLOW", "加气站新建开挖许可流程", 1, 2),

    ACCIDENT_LINKAGE_FLOW("ACCIDENT_LINKAGE_FLOW", "事故处置", 1, 3),

    //非流程待办
    ACCIDENT_JUDGE_FLOW("ACCIDENT_JUDGE_FLOW", "事故研判", 2, 1),
    //预警
    GAS_STATION_WARNING_FLOW("GAS_STATION_WARNING_FLOW", "加气站预警处理", 2, 2),
    GAS_SECURITY_WARNING_FLOW("GAS_SECURITY_WARNING_FLOW", "用气安全预警处理", 2, 2),
    TRANS_STATION_WARNING_FLOW("TRANS_STATION_WARNING_FLOW", "输配站预警处理", 2, 2),
    PIPE_WARNING_FLOW("PIPE_WARNING_FLOW", "管网预警处理", 2, 2),
    //隐患
    TRANS_STATION_SELF_HIDDEN_DANGER_FLOW("TRANS_STATION_SELF_HIDDEN_DANGER_FLOW", "输配站企业自查隐患整改", 2, 2),
    TRANS_STATION_GOV_HIDDEN_DANGER_FLOW("TRANS_STATION_GOV_HIDDEN_DANGER_FLOW", "输配站政府检查隐患整改", 2, 2),
    GAS_STATION_HIDDEN_DANGER_FLOW("GAS_STATION_HIDDEN_DANGER_FLOW", "加气站隐患整改", 2, 2),
    PIPE_HIDDEN_DANGER_FLOW("PIPE_HIDDEN_DANGER_FLOW", "管网隐患整改", 2, 2),
    PROJECT_BUILD_SELF_HIDDEN_DANGER_FLOW("PROJECT_BUILD_SELF_HIDDEN_DANGER_FLOW", "工程建设企业自查隐患整改", 2, 2),
    PROJECT_BUILD_GOV_HIDDEN_DANGER_FLOW("PROJECT_BUILD_GOV_HIDDEN_DANGER_FLOW", "工程建设政府检查隐患整改", 2, 2),
    GAS_SECURITY_HIDDEN_DANGER_FLOW("GAS_SECURITY_HIDDEN_DANGER_FLOW", "用气安全隐患整改", 2, 2),
    THIRD_PARTY_PREVENTION_HIDDEN_DANGER_FLOW("THIRD_PARTY_PREVENTION_HIDDEN_DANGER_FLOW", "三方施工预防企业自查隐患整改", 2, 2),
    //协同隐患
    COOPERATE_DANGER_ASSIGN("COOPERATE_DANGER_ASSIGN", "协同执法隐患指派", 2, 3),
    COOPERATE_DANGER_REFORM("COOPERATE_DANGER_REFORM", "协同执法隐患整改", 2, 3),
    COOPERATE_DANGER_CHECK("COOPERATE_DANGER_CHECK", "协同执法隐患复核", 2, 3),
    //协同执法
    COOPERATE_ENFORCE_ASSIGN("COOPERATE_ENFORCE_ASSIGN", "协同执法指派", 2, 3),
    COOPERATE_ENFORCE_CHECK("COOPERATE_ENFORCE_CHECK", "协同执法核查", 2, 3),
    COOPERATE_ENFORCE_COMPLETED("COOPERATE_ENFORCE_COMPLETED", "协同执法办结", 2, 3),

    //协同预警
    COOPERATE_WARNING_ENFORCE_ASSIGN("COOPERATE_WARNING_ENFORCE_ASSIGN", "协同预警执法指派", 2, 3),

    COOPERATE_WARNING_ENFORCE_HANDLE("COOPERATE_WARNING_ENFORCE_HANDLE", "协同预警执法处理", 2, 3),

    GAS_SUPPLY("GAS_SUPPLY", "供气管理流程" , 1 ,2),
    GAS_STOP("GAS_STOP", "停气管理流程", 1 ,2),
    GAS_SUPPLY_PLAN("GAS_SUPPLY_PLAN", "供气管理计划流程", 1 ,2),
    GAS_STOP_PLAN("GAS_STOP_PLAN", "停气管理计划流程", 1 ,2)
    ;

    private String key;

    private String title;

    /**
     * 1:流程待办,2:非流程待办
     */
    private Integer type;

    /**
     * 1:政府处理,2:企业处理,3:政府企业均可处理
     */
    private Integer limits;

    UserTodoListEnum(String key, String title, Integer type, Integer limits) {
        this.key = key;
        this.title = title;
        this.type = type;
        this.limits = limits;
    }

    @Override
    public String getKey() {
        return this.key;
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    public Integer getType() {
        return type;
    }

    public Integer getLimits() {
        return limits;
    }

    public static UserTodoListEnum query(String key) {
        if (key != null) {
            UserTodoListEnum[] values = UserTodoListEnum.values();
            for (UserTodoListEnum result : values) {
                if (result.getKey().equals(key)) {
                    return result;
                }
            }
        }
        return null;
    }

    public static List<String> getProjectTypes() {
        List<String> list = new ArrayList<>();
        list.add(PROJECT_APPROVAL_NEW_PIPE_FLOW.getKey());
        list.add(PROJECT_APPROVAL_PIPE_FLOW.getKey());
        list.add(PROJECT_APPROVAL_TRANSMISSION_FLOW.getKey());
        list.add(PROJECT_APPROVAL_GASSTATION_FLOW.getKey());
        list.add(PROJECT_INVEST_NEW_PIPE_FLOW.getKey());
        list.add(PROJECT_INVEST_PIPE_FLOW.getKey());
        list.add(PROJECT_INVEST_TRANSMISSION_FLOW.getKey());
        list.add(PROJECT_INVEST_GASSTATION_FLOW.getKey());
        list.add(PROJECT_LICENSE_NEW_PIPE_FLOW.getKey());
        list.add(PROJECT_LICENSE_PIPE_FLOW.getKey());
        list.add(PROJECT_LICENSE_TRANSMISSION_FLOW.getKey());
        list.add(PROJECT_LICENSE_GASSTATION_FLOW.getKey());
        list.add(PROJECT_EXCAVATE_NEW_PIPE_FLOW.getKey());
        list.add(PROJECT_EXCAVATE_PIPE_FLOW.getKey());
        list.add(PROJECT_EXCAVATE_TRANSMISSION_FLOW.getKey());
        list.add(PROJECT_EXCAVATE_GASSTATION_FLOW.getKey());
        return list;
    }

    public static List<String> getProjectApprovalTypes() {
        List<String> list = new ArrayList<>();
        list.add(PROJECT_APPROVAL_NEW_PIPE_FLOW.getKey());
        list.add(PROJECT_APPROVAL_PIPE_FLOW.getKey());
        list.add(PROJECT_APPROVAL_TRANSMISSION_FLOW.getKey());
        list.add(PROJECT_APPROVAL_GASSTATION_FLOW.getKey());
        return list;
    }

    public static List<String> getProjectInvestTypes() {
        List<String> list = new ArrayList<>();
        list.add(PROJECT_INVEST_NEW_PIPE_FLOW.getKey());
        list.add(PROJECT_INVEST_PIPE_FLOW.getKey());
        list.add(PROJECT_INVEST_TRANSMISSION_FLOW.getKey());
        list.add(PROJECT_INVEST_GASSTATION_FLOW.getKey());
        return list;
    }

    public static List<String> getProjectLicenseTypes() {
        List<String> list = new ArrayList<>();
        list.add(PROJECT_LICENSE_NEW_PIPE_FLOW.getKey());
        list.add(PROJECT_LICENSE_PIPE_FLOW.getKey());
        list.add(PROJECT_LICENSE_TRANSMISSION_FLOW.getKey());
        list.add(PROJECT_LICENSE_GASSTATION_FLOW.getKey());
        return list;
    }

    public static List<String> getProjectExcavateTypes() {
        List<String> list = new ArrayList<>();
        list.add(PROJECT_EXCAVATE_NEW_PIPE_FLOW.getKey());
        list.add(PROJECT_EXCAVATE_PIPE_FLOW.getKey());
        list.add(PROJECT_EXCAVATE_TRANSMISSION_FLOW.getKey());
        list.add(PROJECT_EXCAVATE_GASSTATION_FLOW.getKey());
        return list;
    }


    public static UserTodoListEnum getTodoEnumByDanger(String businessType, String signType) {
        UserTodoListEnum todoListEnum = null;
        if (businessType.equals(DangerBusinessTypeEnum.GAS.getKey())) {
            todoListEnum = UserTodoListEnum.GAS_SECURITY_HIDDEN_DANGER_FLOW;
        } else if (businessType.equals(DangerBusinessTypeEnum.TRIPARTITE.getKey())) {
            todoListEnum = UserTodoListEnum.THIRD_PARTY_PREVENTION_HIDDEN_DANGER_FLOW;
        } else if (businessType.equals(DangerBusinessTypeEnum.PROJECT.getKey())) {
            todoListEnum = signType.equals(OrganizationTypeEnum.COMPANY.getKey())
                    ? UserTodoListEnum.PROJECT_BUILD_SELF_HIDDEN_DANGER_FLOW
                    : UserTodoListEnum.PROJECT_BUILD_GOV_HIDDEN_DANGER_FLOW;
        } else if (businessType.equals(DangerBusinessTypeEnum.STATION.getKey())) {
            todoListEnum = signType.equals(OrganizationTypeEnum.COMPANY.getKey())
                    ? UserTodoListEnum.TRANS_STATION_SELF_HIDDEN_DANGER_FLOW
                    : UserTodoListEnum.TRANS_STATION_GOV_HIDDEN_DANGER_FLOW;
        } else if (businessType.equals(DangerBusinessTypeEnum.FILL.getKey())) {
            todoListEnum = UserTodoListEnum.GAS_STATION_HIDDEN_DANGER_FLOW;
        } else if (businessType.equals(DangerBusinessTypeEnum.PIPE.getKey())) {
            todoListEnum = UserTodoListEnum.PIPE_HIDDEN_DANGER_FLOW;
        }
        return todoListEnum;
    }
}
