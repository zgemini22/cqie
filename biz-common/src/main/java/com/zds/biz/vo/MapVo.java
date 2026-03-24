package com.zds.biz.vo;

import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(description = "高德地址返回")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MapVo {

    @ApiModelPropertyCheck(value = "地址")
    private String address;

    @ApiModelPropertyCheck(value = "省")
    private String provinceCode;

    @ApiModelPropertyCheck(value = "省名")
    private String provinceCodeName;

    @ApiModelPropertyCheck(value = "市")
    private String cityCode;

    @ApiModelPropertyCheck(value = "市名")
    private String cityCodeName;

    @ApiModelPropertyCheck(value = "区")
    private String areaCode;

    @ApiModelPropertyCheck(value = "区名")
    private String areaCodeName;

    @ApiModelPropertyCheck(value = "街道")
    private String townCode;

    @ApiModelPropertyCheck(value = "街道")
    private String townCodeName;
}
