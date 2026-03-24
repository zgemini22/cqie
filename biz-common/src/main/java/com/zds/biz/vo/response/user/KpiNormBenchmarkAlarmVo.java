package com.zds.biz.vo.response.user;

import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@ApiModel(description = "指标标杆计算结果")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KpiNormBenchmarkAlarmVo {

    @ApiModelPropertyCheck(value = "告警阈值")
    private BigDecimal value;

    @ApiModelPropertyCheck(value = "符号（1=(<=),2=(>=),3=(>),4=(<)）")
    private Integer symbol;

    @ApiModelPropertyCheck(value = "等级（1=1级，2=2级，3=3级）")
    private Integer grade;

    @ApiModelPropertyCheck(value = "序号")
    private Integer sort;
}
