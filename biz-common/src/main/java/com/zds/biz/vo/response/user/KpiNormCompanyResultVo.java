package com.zds.biz.vo.response.user;

import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@ApiModel(description = "指标计算结果")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KpiNormCompanyResultVo {

    @ApiModelPropertyCheck(value = "企业名称")
    private String companyName;

    @ApiModelPropertyCheck(value = "指标结果")
    private BigDecimal normResult;

    @ApiModelPropertyCheck(value = "往期指标结果")
    private BigDecimal lastNormResult;

    @ApiModelPropertyCheck(value = "较往期浮动比例")
    private BigDecimal floatRatio;
}
