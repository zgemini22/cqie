package com.zds.biz.vo.request.user;

import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.math.BigDecimal;


@ApiModel(description = "kpi标杆管理警阈值公式请求")
@Data
public class AdminKpiBenchmarkAlarmDetailRequest {
    @ApiModelPropertyCheck(value = "数据源ID", required = true)
    private Long dataId;

    @ApiModelPropertyCheck(value = "排序", required = true)
    private Integer sort;

    @ApiModelPropertyCheck(value = "数值")
    private BigDecimal number;

}
