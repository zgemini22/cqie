package com.zds.biz.vo.response.user;

import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(description = "指标下拉列表返回")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KpiNormSelectResponse {

    @ApiModelPropertyCheck(value="ID")
    private Long id;

    @ApiModelPropertyCheck(value="名称")
    private String name;

    @ApiModelPropertyCheck(value="状态,1:启用,0:禁用")
    private Integer status = 1;

    @ApiModelPropertyCheck(value = "指标单位,字典group_id=GAS_NORM_UNIT")
    private String normUnit;
}
