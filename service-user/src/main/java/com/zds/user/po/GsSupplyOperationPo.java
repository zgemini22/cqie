// GsSupplyOperationPo.java
package com.zds.user.po;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;

@Data
@TableName("gs_supply_operation")
public class GsSupplyOperationPo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("org_id")
    private Integer orgId;  // 外键，关联 tbl_organization.id

    @TableField("plan_id")
    private Integer planId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField("start_time")
    private Date startTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField("end_time")
    private Date endTime;

    @TableField("supply_reason")
    private String supplyReason;

    @TableField("affected_households")
    private Integer affectedHouseholds;

    @TableField("remark")
    private String remark;

    @TableField("status")
    private String status;

    @TableField("supply_type")
    private String supplyType;

    @TableField("enterprise_id")
    private Integer enterpriseId;

    @TableField("area_node_id")
    private Long areaNodeId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(value = "create_id", fill = FieldFill.INSERT)
    private Integer createId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @TableField(value = "update_id", fill = FieldFill.INSERT_UPDATE)
    private Integer updateId;

    @TableField("deleted")
    private Integer deleted;

    // 非表字段，用于接收关联查询结果
    @TableField(exist = false)
    private String enterpriseName;

    @TableField(exist = false)
    private String supplyArea;
}