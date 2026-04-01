package com.zds.biz.vo.response.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel(description = "运行监测列表响应")
public class RunDetectionResponse {
    @ApiModelProperty(value = "设备编码")
    private String deviceCode;

    @ApiModelProperty(value = "设备名称")
    private String deviceName;

    @ApiModelProperty(value = "监测类型")
    private String monitorType;

    @ApiModelProperty(value = "监测值")
    private String monitorValue;

    @ApiModelProperty(value = "监测位置")
    private String monitorLocation;

    @ApiModelProperty(value = "安装地址")
    private String installAddress;

    @ApiModelProperty(value = "最后采集时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date lastCollectTime;
}