package com.zds.biz.vo.response.user;

import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@ApiModel(description = "指标计算结果")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KpiNormResultVo {

    @ApiModelPropertyCheck(value = "指标ID")
    private Long normId;

    @ApiModelPropertyCheck(value = "指标结果")
    private BigDecimal normResult;

    @ApiModelPropertyCheck(value = "按企业分组指标结果")
    private List<KpiNormCompanyResultVo> normResultByCompany;

    @ApiModelPropertyCheck(value = "按街道分组指标结果")
    private List<KpiNormStreetResultVo> normResultByStreet;

//    @ApiModelPropertyCheck(value = "按企业街道分组指标结果")
//    private List<KpiNormCASResultVo> normResultByCAS;
}
