package com.zds.biz.vo.request.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class StationDeviceRequest {
    @ApiModelProperty("组织ID")
    private Long orgId;
}