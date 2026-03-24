package com.zds.biz.vo;

import io.swagger.annotations.ApiModel;
import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@ApiModel(description = "燃气公司外部数据通用类")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class XataCoordinateVo {

    @ApiModelPropertyCheck("唯一编号")
    private String sid;

    @ApiModelPropertyCheck("类型(1:管线,2调压箱,3:气表箱,4:阀门)")
    private Integer pipelineType;

    @ApiModelPropertyCheck("经度")
    private BigDecimal lng;

    @ApiModelPropertyCheck("纬度")
    private BigDecimal lat;

    @ApiModelPropertyCheck("经度2(管线)")
    private BigDecimal lng2;

    @ApiModelPropertyCheck("纬度2(管线)")
    private BigDecimal lat2;

    @ApiModelPropertyCheck("长度(管线)")
    private String length;
}
