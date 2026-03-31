package com.zds.user.po;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 供气计划PO实体类
 * 表名：gs_supply_plan
 */
@Data
@TableName("gs_supply_plan")
public class GasSupplyPo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 组织ID（对应表中org_id）
     */
    @TableField("org_id")
    private Integer orgId;

    /**
     * 供气原因（对应表中supply_reason）
     */
    @TableField("supply_reason")
    private String supplyReason;

    /**
     * 计划开始时间（对应表中start_time）
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField("start_time")
    private Date startTime;

    /**
     * 计划结束时间（对应表中end_time）
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField("end_time")
    private Date endTime;

    /**
     * 影响户数（对应表中affected_households）
     */
    @TableField("affected_households")
    private Integer affectedHouseholds;

    /**
     * 备注（对应表中remark）
     */
    @TableField("remark")
    private String remark;

    /**
     * 状态（对应表中status）
     */
    @TableField("status")
    private String status;

    /**
     * 创建时间（对应表中create_time）
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 创建人ID（对应表中create_id）
     */
    @TableField(value = "create_id", fill = FieldFill.INSERT)
    private Integer createId;

    /**
     * 更新时间（对应表中update_time）
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    /**
     * 更新人ID（对应表中update_id）
     */
    @TableField(value = "update_id", fill = FieldFill.INSERT_UPDATE)
    private Integer updateId;

    /**
     * 删除标志（对应表中deleted）
     * 注意：表中字段名为deleted，不是del_flag
     */
    @TableField("deleted")
    private Integer deleted;

    /**
     * 【新增】关联查询：供气区域（重庆市渝中区）
     */
    @TableField(exist = false)
    private String supplyArea;

    /**
     * 【新增】关联查询：城燃企业名称
     */
    @TableField(exist = false)
    private String enterpriseName;
}