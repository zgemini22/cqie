package com.zds.biz.vo.response.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class StationDeviceResponse {
    @ApiModelProperty("站点ID")
    private Long id;

    @ApiModelProperty("站点名称")
    private String stationName;

    @ApiModelProperty("是否启用")
    private Integer isEnabled;

    @ApiModelProperty("设备状态")
    private String deviceStatus;
}