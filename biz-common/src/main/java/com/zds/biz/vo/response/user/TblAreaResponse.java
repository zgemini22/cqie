package com.zds.biz.vo.response.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@ApiModel(description = "城市树")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TblAreaResponse {

    @ApiModelProperty("区域编码")
    private String code;

    @ApiModelProperty("区域名称")
    private String areaName;

    @ApiModelProperty("父级编码")
    private String parentCode;

    @ApiModelProperty("级别")
    private Integer level;

    @ApiModelProperty("经纬度和排序列表")
    private List<AreaRangeInfo> rangeInfoList;

}
