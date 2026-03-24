package com.zds.biz.vo.request.user;

import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@ApiModel(description = "指标信息保存修改请求")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminKpiNormInfoSaveRequest {

    @ApiModelPropertyCheck(value = "ID,传为修改,不传为新增")
    private Long id;

    @ApiModelPropertyCheck(value = "指标名称",required = true)
    private String normName;

    @ApiModelPropertyCheck(value = "指标类别ID",required = true)
    private Long normClassifyId;

    @ApiModelPropertyCheck(value = "指标标识,字典group_id=GAS_NORM_SIGN",required = true)
    private String normSign;

    @ApiModelPropertyCheck(value = "指标类型,字典group_id=GAS_INDICATOR_TYPE",required = true)
    private String normIndicatorType;

    @ApiModelPropertyCheck(value = "指标细化纬度,字典group_id=GAS_NORM_DIMENSIONS,多个用逗号拼接")
    private String normDimensions;

    @ApiModelPropertyCheck(value = "指标单位,字典group_id=GAS_NORM_UNIT",required = true)
    private String normUnit;

    @ApiModelPropertyCheck(value = "排序")
    private Integer sort;

    @ApiModelPropertyCheck(value = "指标公式,数据源ID")
    private List<AdminKpiNormInfoFormulaRequest> dataIds;

    @ApiModelPropertyCheck(value = "公式说明")
    private String formulaDesc;

    @ApiModelPropertyCheck(value = "指标描述")
    private String normDesc;

    @ApiModelPropertyCheck(value = "指标小数位",required = true)
    private Integer decimalDigit;

    @ApiModelPropertyCheck(value = "指标细化纬度排序,0:倒序,1:顺序")
    private Integer normDimensionsSort;
}
