package com.zds.biz.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.util.Date;

/**
 * 供气计划响应VO
 */
@Data
public class GasSupplyResponse {

    /**
     * 主键ID
     */
    private Integer id;

    // ======================
    // 气源类型相关字段
    // ======================
    /**
     * 气源类型代码
     */
    private String gasType;

    /**
     * 气源类型名称
     */
    private String gasTypeName;

    // ======================
    // 供气类型相关字段
    // ======================
    /**
     * 供气类型代码（供气原因）
     */
    private String supplyType;

    /**
     * 供气类型名称
     */
    private String supplyTypeName;

    // ======================
    // 计划时间相关字段
    // ======================
    /**
     * 计划开始时间
     * 前端必须用这个名字：planStartTime
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date planStartTime;

    /**
     * 计划结束时间
     * 前端必须用这个名字：planEndTime
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date planEndTime;

    // ======================
    // 供气信息相关字段
    // ======================
    /**
     * 供气量
     */
    private Integer supplyVolume;

    /**
     * 供气区域
     */
    private String supplyArea;

    /**
     * 备注
     */
    private String remarks;

    /**
     * 影响户数
     */
    private Integer affectHouseholds;

    // ======================
    // 状态相关字段
    // ======================
    /**
     * 供气状态代码
     * 前端必须用这个名字：supplyStatus
     */
    private String supplyStatus;

    /**
     * 供气状态名称
     */
    private String supplyStatusName;

    // ======================
    // 企业信息相关字段
    // ======================
    /**
     * 城燃企业名称
     */
    private String enterpriseName;

    // ======================
    // 审计字段
    // ======================
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    /**
     * 创建人ID
     * 前端必须用这个名字：createUser
     */
    private Integer createUser;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    /**
     * 更新人ID
     * 前端必须用这个名字：updateUser
     */
    private Integer updateUser;

    // 添加单独的地址字段用于调试
    private String districtCode;
    private String streetCode;
    private String detailAddress;
}