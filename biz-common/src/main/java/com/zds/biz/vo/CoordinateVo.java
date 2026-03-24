package com.zds.biz.vo;

import io.swagger.annotations.ApiModel;
import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@ApiModel(description = "经纬度通用类")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CoordinateVo {

    @ApiModelPropertyCheck("经度")
    private BigDecimal lng;

    @ApiModelPropertyCheck("纬度")
    private BigDecimal lat;
}
