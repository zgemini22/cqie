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
@TableName("kpi_norm_info")
@ApiModel(value = "KpiNormInfo对象", description = "KPI指标信息表")
public class KpiNormInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelPropertyCheck(value = "ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelPropertyCheck(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;

    @ApiModelPropertyCheck(value = "创建人")
    private Long createId;

    @ApiModelPropertyCheck(value = "修改时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date updateTime;

    @ApiModelPropertyCheck(value = "修改人")
    private Long updateId;

    @ApiModelPropertyCheck(value = "是否删除")
    private Boolean deleted;

    @ApiModelPropertyCheck(value = "组织ID")
    private Long organizationId;

    @ApiModelPropertyCheck(value = "指标名称")
    private String normName;

    @ApiModelPropertyCheck(value = "指标编码")
    private String normCode;

    @ApiModelPropertyCheck(value = "指标类别ID")
    private Long normClassifyId;

    @ApiModelPropertyCheck(value = "指标标识,字典group_id=GAS_NORM_SIGN")
    private String normSign;

    @ApiModelPropertyCheck(value = "指标类型,字典group_id=GAS_INDICATOR_TYPE")
    private String normIndicatorType;

    @ApiModelPropertyCheck(value = "指标细化纬度,字典group_id=GAS_NORM_DIMENSIONS")
    private String normDimensions;

    @ApiModelPropertyCheck(value = "指标单位,字典group_id=GAS_NORM_UNIT")
    private String normUnit;

    @ApiModelPropertyCheck(value = "排序")
    private Integer sort;

    @ApiModelPropertyCheck(value = "公式说明")
    private String formulaDesc;

    @ApiModelPropertyCheck(value = "指标描述")
    private String normDesc;

    @ApiModelPropertyCheck(value = "指标状态(1:停用,2:启用)")
    private Integer normStatus;

    @ApiModelPropertyCheck(value = "指标小数位")
    private Integer decimalDigit;

    @ApiModelPropertyCheck(value = "指标细化纬度排序,0:倒序,1:顺序")
    private Integer normDimensionsSort;

    public static LambdaQueryWrapper<KpiNormInfo> getWrapper() {
        return new LambdaQueryWrapper<>();
    }
}
