package com.zds.biz.vo.response.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.math.BigDecimal;

@Data
@ApiModel(description = "设备详情响应")
public class DeviceDetailResponse {
    @ApiModelProperty(value = "设备编码")
    private String deviceCode;

    @ApiModelProperty(value = "设备名称")
    private String deviceName;

    @ApiModelProperty(value = "监测类型")
    private String monitorType;

    @ApiModelProperty(value = "设备型号")
    private String deviceModel;

    @ApiModelProperty(value = "生产厂商")
    private String manufacturer;

    @ApiModelProperty(value = "量程范围")
    private String range;

    @ApiModelProperty(value = "阈值上限")
    private BigDecimal thresholdUpper;

    @ApiModelProperty(value = "安装地址")
    private String installAddress;

    @ApiModelProperty(value = "监测点")
    private String monitoringPoint;

    @ApiModelProperty(value = "实时电压(V)")
    private BigDecimal realTimeVoltage;

    @ApiModelProperty(value = "实时电量(kWh)")
    private BigDecimal realTimePower;

    @ApiModelProperty(value = "运行状态")
    private String runningStatus;
}