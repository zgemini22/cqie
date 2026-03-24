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
public class ImportCathodeProtectVo {

    @ApiModelPropertyCheck(value = "管护单位名称")
    private String departmentName;

    @ApiModelPropertyCheck(value = "管网编号")
    private String pipelineNo;

    @ApiModelPropertyCheck(value = "设备编号")
    private String deviceNo;

    @ApiModelPropertyCheck(value = "保护长度(m)")
    private String protectLength;

    @ApiModelPropertyCheck(value = "安装时间")
    private String installTime;

    @ApiModelPropertyCheck(value = "安装位置")
    private String installAddress;

    @ApiModelProperty(value="行数")
    private Integer row;

}

