package com.zds.biz.vo.response.user;

import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@ApiModel(description = "指标数据源信息")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminKpiNormFormalResponse {

    @ApiModelPropertyCheck(value = "数据源ID")
    private Long id;

    @ApiModelPropertyCheck(value = "数据源名称")
    private String dataName;

    @ApiModelPropertyCheck(value = "排序")
    private Integer sort;

    @ApiModelPropertyCheck(value = "数值")
    private BigDecimal number;
}
