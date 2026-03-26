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
@TableName("gs_cutoff_plan")
@ApiModel(value = "GsCutoffPlan对象", description = "保供管理-停气计划主表")
public class GsCutoffPlan implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelPropertyCheck(value = "自增主键，KPI数据源管理ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelPropertyCheck(value = "组织机构ID，关联tbl_organization.id")
    private Long orgId;

    @ApiModelPropertyCheck(value = "关联的停气作业ID（作业模块选择此计划后回填，用于双向追溯）")
    private Long operationId;

    @ApiModelPropertyCheck(value = "计划停气时间 (YYYY-MM-DD HH:MM:SS)")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date startTime;

    @ApiModelPropertyCheck(value = "计划恢复时间 (需晚于停气时间)")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date endTime;

    @ApiModelPropertyCheck(value = "停气作业等级 (存数据字典编码)")
    private String operationLevel;

    @ApiModelPropertyCheck(value = "预计影响户数 (长度≤10位)")
    private Integer affectedHouseholds;

    @ApiModelPropertyCheck(value = "停气原因描述 (长度≤200)")
    private String reason;

    @ApiModelPropertyCheck(value = "备注说明 (长度≤500)")
    private String remark;

    @ApiModelPropertyCheck(value = "状态 (UNSUBMITTED:未提交, AUDITING:审核中, REJECTED:驳回, PASSED:已通过)")
    private String status;

    @ApiModelPropertyCheck(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;

    @ApiModelPropertyCheck(value = "创建人ID")
    private Long createId;

    @ApiModelPropertyCheck(value = "最后修改时间 (用于逆序排列)")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date updateTime;

    @ApiModelPropertyCheck(value = "修改人ID")
    private Long updateId;

    @ApiModelPropertyCheck(value = "逻辑删除 (0:否, 1:是)")
    private Boolean deleted;

    public static LambdaQueryWrapper<GsCutoffPlan> getWrapper() {
        return new LambdaQueryWrapper<>();
    }
}
