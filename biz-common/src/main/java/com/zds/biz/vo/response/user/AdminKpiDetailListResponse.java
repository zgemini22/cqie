package com.zds.biz.vo.response.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@ApiModel(description = "数据源明细分页列表返回")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminKpiDetailListResponse {

    @ApiModelPropertyCheck(value = "ID")
    private Long id;

    @ApiModelPropertyCheck(value = "企业名称")
    private String companyName;

    @ApiModelPropertyCheck(value = "街道名称")
    private String street;

    @ApiModelPropertyCheck(value = "数据日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date dataDate;

    @ApiModelPropertyCheck(value = "数据值")
    private BigDecimal value;
}
