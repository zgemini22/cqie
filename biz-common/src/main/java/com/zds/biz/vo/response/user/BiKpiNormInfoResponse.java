package com.zds.biz.vo.response.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@ApiModel(description = "指标信息列表返回")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BiKpiNormInfoResponse {

    @ApiModelPropertyCheck(value = "ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

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

    @ApiModelPropertyCheck(value = "指标小数位")
    private Integer decimalDigit;

}
