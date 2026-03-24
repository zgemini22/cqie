package com.zds.biz.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.Date;

@ApiModel(value = "WebSocketVo类")
@Data
public class WebSocketVo {

    @ApiModelPropertyCheck(value = "type")
    private Integer type;

    @ApiModelPropertyCheck(value = "设备ID,事故ID")
    private Long data;

    @ApiModelPropertyCheck(value = "事故来源(字典group_id=ACCIDENT_SOURCE)")
    private String accidentSource;

    @ApiModelPropertyCheck(value = "事故地点")
    private String address;

    @ApiModelPropertyCheck(value = "发生时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date occurrenceTime;

    @ApiModelPropertyCheck(value = "事故详情")
    private String accidentContent;
}
