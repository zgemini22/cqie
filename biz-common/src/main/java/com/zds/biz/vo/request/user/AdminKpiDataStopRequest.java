package com.zds.biz.vo.request.user;

import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(description = "数据管理停用/启用请求")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminKpiDataStopRequest {

    @ApiModelPropertyCheck(value = "数据管理ID", required = true)
    private Long id;

    @ApiModelPropertyCheck(value = "状态（1=启用，2=停用）", required = true)
    private Integer status;
}
