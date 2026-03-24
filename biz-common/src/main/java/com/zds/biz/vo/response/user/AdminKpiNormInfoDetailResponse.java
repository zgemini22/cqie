package com.zds.biz.vo.response.user;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@ApiModel(description = "指标信息详情")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminKpiNormInfoDetailResponse {

    @ApiModelPropertyCheck(value = "ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelPropertyCheck(value = "指标名称")
    private String normName;

    @ApiModelPropertyCheck(value = "指标编码")
    private String normCode;

    @ApiModelPropertyCheck(value = "指标类别集合ID")
    private List<Long> normClassifyIds;

    /*@ApiModelPropertyCheck(value = "指标类别名称")
    private String normClassifyName;

    @ApiModelPropertyCheck(value = "指标类别父ID")
    private Long normClassifyParentId;

    @ApiModelPropertyCheck(value = "指标类别父级名称")
    private String normClassifyParentName;*/

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

    @ApiModelPropertyCheck(value = "指标公式数据源")
    private List<AdminKpiNormFormalResponse> formalList;

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
}
