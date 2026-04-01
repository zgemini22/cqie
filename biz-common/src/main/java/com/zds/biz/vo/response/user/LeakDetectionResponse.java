package com.zds.biz.vo.response.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel(description = "泄漏监测列表响应")
public class LeakDetectionResponse {
    @ApiModelProperty(value = "设备编码")
    private String deviceCode;

    @ApiModelProperty(value = "设备名称")
    private String deviceName;

    @ApiModelProperty(value = "监测类型")
    private String monitorType;

    @ApiModelProperty(value = "监测值")
    private String monitorValue;

    @ApiModelProperty(value = "监测位置")
    private String installAddress;

    @ApiModelProperty(value = "最后采集时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date lastCollectTime;

    @ApiModelProperty(value = "未处理预警数量")
    private Integer unprocessedWarning;

    @ApiModelProperty(value = "运行状态")
    private String runningStatus;
}
