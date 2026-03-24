package com.zds.biz.vo.response.user;

import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@ApiModel(description = "查询指定指标信息返回")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BiKpiNormVo {

    @ApiModelPropertyCheck(value = "指标ID")
    private Long normId;

    @ApiModelPropertyCheck(value = "指标单位,字典group_id=GAS_NORM_UNIT")
    private String normUnit;

    @ApiModelPropertyCheck(value = "指标单位名称")
    private String normUnitValue;

    @ApiModelPropertyCheck(value = "指标结果")
    private BigDecimal normResult;

    @ApiModelPropertyCheck(value = "往期指标结果")
    private BigDecimal lastNormResult;

    @ApiModelPropertyCheck(value = "较往期浮动比例")
    private BigDecimal floatRatio;

    @ApiModelPropertyCheck(value = "按企业分组指标结果")
    private List<KpiNormCompanyResultVo> normResultByCompany;

    @ApiModelPropertyCheck(value = "按街道分组指标结果")
    private List<KpiNormStreetResultVo> normResultByStreet;

//    @ApiModelPropertyCheck(value = "按企业街道分组指标结果")
//    private List<KpiNormCASResultVo> normResultByCAS;

    @ApiModelPropertyCheck(value = "是否设置标杆")
    private Boolean benchmarkFlag;

    @ApiModelPropertyCheck(value = "标杆值")
    private BigDecimal value;

    @ApiModelPropertyCheck(value = "符号（1=(<=),2=(>=),3=(>),4=(<)）")
    private Integer symbol;

    @ApiModelPropertyCheck(value = "告警阈值集合")
    private List<KpiNormBenchmarkAlarmVo> alarmList;
}
