package com.zds.biz.vo.response.user;

import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.math.BigDecimal;

@ApiModel(description = "kpi标杆管理警阈值公式查看")
@Data
public class AdminKpiBenchmarkAlarmDetailResponse {
    @ApiModelPropertyCheck(value = "数据源ID")
    private Long dataId;

    @ApiModelPropertyCheck(value = "数据源名称")
    private String dataName;

    @ApiModelPropertyCheck(value = "排序")
    private Integer sort;

    @ApiModelPropertyCheck(value = "数值")
    private BigDecimal number;
}
