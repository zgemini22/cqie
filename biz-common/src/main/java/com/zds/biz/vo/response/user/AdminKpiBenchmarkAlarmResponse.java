package com.zds.biz.vo.response.user;

import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@ApiModel(description = "kpi标杆管理警阈值返回")
@Data
public class AdminKpiBenchmarkAlarmResponse {
    @ApiModelPropertyCheck(value = "符号（1=(<=),2=(>=),3=(>),4=(<)）")
    private Integer symbol;

    @ApiModelPropertyCheck(value = "kpi标杆管理警阈值ID,传为修改,不传为新增")
    private Long id;

    @ApiModelPropertyCheck(value = "告警阈值/标杆值")
    private BigDecimal value;

    @ApiModelPropertyCheck(value = "等级（1=1级，2=2级，3=3级，4=标杆值）")
    private Integer grade;
    @ApiModelPropertyCheck(value = "序号")
    private Integer sort;

    @ApiModelPropertyCheck(value = "公式集合")
    private List<AdminKpiBenchmarkAlarmDetailResponse> benchmarkAlarmDetail;
}
