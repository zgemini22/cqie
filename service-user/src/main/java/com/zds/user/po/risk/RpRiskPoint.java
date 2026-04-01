package com.zds.user.po.risk;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel("风险点表")
@TableName("rp_risk_point")
public class RpRiskPoint implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty("主键ID")
    private Long id;

    @ApiModelProperty("风险点名称")
    @TableField("risk_name")
    private String riskName;

    @ApiModelProperty("风险点编号")
    @TableField("risk_code")
    private String riskCode;

    @ApiModelProperty("行政区域/镇街名称")
    @TableField("town_street_name")
    private String townStreetName;

    @ApiModelProperty("风险点位置")
    @TableField("address")
    private String address;

    @ApiModelProperty("风险点状态")
    @TableField("prevent_status")
    private String preventStatus;

    @ApiModelProperty("创建人")
    @TableField(value = "create_by", fill = FieldFill.INSERT)
    private String createBy;

    @ApiModelProperty("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;

    @ApiModelProperty("更新人")
    @TableField(value = "update_by", fill = FieldFill.INSERT_UPDATE)
    private String updateBy;

    @ApiModelProperty("更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @ApiModelProperty("逻辑删除 0-未删除 1-已删除")
    @TableLogic
    @TableField("deleted")
    private Integer deleted;
}