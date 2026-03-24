package com.zds.biz.vo.request.user;

import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.math.BigDecimal;

@ApiModel(description = "kpi指标管理公式请求")
@Data
public class AdminKpiNormInfoFormulaRequest {
    @ApiModelPropertyCheck(value = "数据源ID")
    private Long dataId;

    @ApiModelPropertyCheck(value = "排序")
    private Integer sort;

    @ApiModelPropertyCheck(value = "数值")
    private BigDecimal number;
}
