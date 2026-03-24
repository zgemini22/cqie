package com.zds.biz.vo.request.user;

import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import com.zds.biz.vo.PageRequest;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(description = "指标信息列表请求")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminKpiNormInfoListRequest extends PageRequest {

    @ApiModelPropertyCheck(value = "指标名称")
    private String normName;

    @ApiModelPropertyCheck(value = "指标状态(1:停用,2:启用)")
    private Integer normStatus;

    @ApiModelPropertyCheck(value = "指标类别ID")
    private Long normClassifyId;
}
