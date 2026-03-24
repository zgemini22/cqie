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
@TableName("tpc_inspection_plan")
@ApiModel(value = "TpcInspectionPlan对象", description = "巡检计划表")
public class TpcInspectionPlan implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelPropertyCheck(value = "主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelPropertyCheck(value = "巡检计划名称")
    private String planName;

    @ApiModelPropertyCheck(value = "巡检类别")
    private String planCategory;

    @ApiModelPropertyCheck(value = "三方施工ID")
    private Long constructionId;

    @ApiModelPropertyCheck(value = "巡检频次")
    private String inspectionFrequency;

    @ApiModelPropertyCheck(value = "计划有效期开始")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date validStartDate;

    @ApiModelPropertyCheck(value = "计划有效期结束")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date validEndDate;

    @ApiModelPropertyCheck(value = "状态")
    private String status;

    @ApiModelPropertyCheck(value = "巡检状态")
    private String inspectStatus;

    @ApiModelPropertyCheck(value = "巡检时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date inspectTime;

    @ApiModelPropertyCheck(value = "巡检人")
    private Long inspector;

    @ApiModelPropertyCheck(value = "组织ID")
    private Long orgId;

    @ApiModelPropertyCheck(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;

    @ApiModelPropertyCheck(value = "创建人ID")
    private Long createId;

    @ApiModelPropertyCheck(value = "修改时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date updateTime;

    @ApiModelPropertyCheck(value = "修改人ID")
    private Long updateId;

    @ApiModelPropertyCheck(value = "是否删除")
    private Boolean deleted;

    public static LambdaQueryWrapper<TpcInspectionPlan> getWrapper() {
        return new LambdaQueryWrapper<>();
    }
}
