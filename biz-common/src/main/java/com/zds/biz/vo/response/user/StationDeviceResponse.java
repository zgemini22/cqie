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

    @ApiModelProperty("状态：待处理、处理中、已处理")
    private String deviceStatus;
}