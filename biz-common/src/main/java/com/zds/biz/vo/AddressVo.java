package com.zds.biz.vo;

import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(description = "高德地址经纬度返回")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressVo {

    //地址
    @ApiModelPropertyCheck(value = "地址")
    private String formatted_address;
    //国家
    @ApiModelPropertyCheck(value = "国家")
    private String country;
    //地址所在的省份名
    @ApiModelPropertyCheck(value = "地址所在的省份名")
    private String province;

    @ApiModelPropertyCheck(value = "地址所在的省份编码")
    private String provinceCode;

    //地址所在的城市名
    @ApiModelPropertyCheck(value = "地址所在的城市名")
    private String city;

    @ApiModelPropertyCheck(value = "地址所在的城市编码")
    private String cityCode;

    //地址所在的区
    @ApiModelPropertyCheck(value = "地址所在的区")
    private String district;

    @ApiModelPropertyCheck(value = "编码")
    private String districtCode;

    @ApiModelPropertyCheck(value = "街道编码")
    private String streetCode;

    //街道
    @ApiModelPropertyCheck(value = "街道名称")
    private String street;

    //街道
    //门牌号
    @ApiModelPropertyCheck(value = "门牌号")
    private String number;
    //经度
    @ApiModelPropertyCheck(value = "经度")
    private String longitude;
    //纬度
    @ApiModelPropertyCheck(value = "纬度")
    private String latitude;
    //级别
    @ApiModelPropertyCheck(value = "级别")
    private String level;
}
