package com.zds.user.po.risk;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@ApiModel("风险点查询DTO")
public class RiskPointQueryDTO {

    @ApiModelProperty("页码")
    private Integer pageNum = 1;

    @ApiModelProperty("每页条数")
    private Integer pageSize = 10;

    @ApiModelProperty("风险点名称")
    private String riskName;

    @ApiModelProperty("风险点编号")
    private String riskCode;

    @ApiModelProperty("单位（台/套）")
    private String unit;

    @ApiModelProperty("城燃企业名称")
    private String organizationName;

    @ApiModelProperty("开始日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate planInstallStartDate;

    @ApiModelProperty("结束日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate planInstallEndDate;
}