package com.zds.biz.vo.request.user;

import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@ApiModel(description = "指标状态修改请求")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminKpiNormStatusRequest {

    @ApiModelPropertyCheck(value = "ID",required = true)
    private Long id;

    @ApiModelPropertyCheck(value = "指标状态(1:停用,2:启用)",required = true)
    private Integer normStatus;
}
