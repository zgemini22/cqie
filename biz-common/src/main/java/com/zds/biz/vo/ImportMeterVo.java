package com.zds.biz.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ImportMeterVo {
    @ApiModelPropertyCheck(value = "气表编号")
    private String meterCode;

    @ApiModelPropertyCheck(value = "气表名称")
    private String meterName;

    @ApiModelPropertyCheck(value = "气表类型,1:普通表,2:智能表")
    private String meterType;

    @ApiModelPropertyCheck(value = "安装时间")
    private String installTime;

    @ApiModelPropertyCheck(value = "详细位置")
    private String address;

    @ApiModelPropertyCheck(value = "初始读数")
    private String initRead;

    @ApiModelPropertyCheck(value = "最大读数")
    private String maxRead;

    @ApiModelPropertyCheck(value = "备注")
    private String remarks;

    @ApiModelProperty(value="行数")
    private Integer row;

}

