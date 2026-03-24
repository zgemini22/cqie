package com.zds.biz.vo.request.user;

import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(description = "指标下拉请求")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminKpiSelectRequest {

    @ApiModelPropertyCheck(value = "指标标识,字典group_id=GAS_NORM_SIGN")
    private String normSign;

    @ApiModelPropertyCheck(value = "指标类型,字典group_id=GAS_INDICATOR_TYPE")
    private String normIndicatorType;
}
