package com.zds.biz.vo.response.user;

import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@ApiModel(description = "指标标杆计算结果")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KpiNormBenchmarkVo {

    @ApiModelPropertyCheck(value = "指标ID")
    private Long normId;

    @ApiModelPropertyCheck(value = "标杆值")
    private BigDecimal value;

    @ApiModelPropertyCheck(value = "符号（1=(<=),2=(>=),3=(>),4=(<)）")
    private Integer symbol;

    @ApiModelPropertyCheck(value = "告警阈值集合")
    private List<KpiNormBenchmarkAlarmVo> alarmList;
}
