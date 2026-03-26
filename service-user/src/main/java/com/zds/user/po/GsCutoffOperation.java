package com.zds.user.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import io.swagger.annotations.ApiModel;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonFormat;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("gs_cutoff_operation")
@ApiModel(value = "GsCutoffOperation对象", description = "保供管理-停气作业执行表")
public class GsCutoffOperation implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelPropertyCheck(value = "自增主键，KPI数据源管理ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelPropertyCheck(value = "组织机构ID")
    private Long orgId;

    @ApiModelPropertyCheck(value = "类型 (PLANNED:计划停气, UNPLANNED:非计划停气)")
    private String operationType;

    @ApiModelPropertyCheck(value = "关联计划ID (计划停气时必选)")
    private Long planId;

    @ApiModelPropertyCheck(value = "实际停气时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date startTime;

    @ApiModelPropertyCheck(value = "预计恢复时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date endTime;

    @ApiModelPropertyCheck(value = "作业等级")
    private String operationLevel;

    @ApiModelPropertyCheck(value = "实际影响户数")
    private Integer affectedHouseholds;

    @ApiModelPropertyCheck(value = "停气原因")
    private String reason;

    @ApiModelPropertyCheck(value = "作业过程备注")
    private String remark;

    @ApiModelPropertyCheck(value = "状态 (包含: 已完成 FINISHED)")
    private String status;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;

    private Long createId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date updateTime;

    private Long updateId;

    private Boolean deleted;

    public static LambdaQueryWrapper<GsCutoffOperation> getWrapper() {
        return new LambdaQueryWrapper<>();
    }
}
