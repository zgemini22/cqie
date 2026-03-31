package com.zds.biz.vo.response.user;

import io.swagger.annotations.ApiModel;
import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@ApiModel(description = "企业详情返回")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrgCompanyDetailResponse {

    @ApiModelPropertyCheck(value = "企业ID")
    private Long id;

    @ApiModelPropertyCheck(value = "企业名称")
    private String name;

    @ApiModelPropertyCheck(value = "简称")
    private String shortName;

    @ApiModelPropertyCheck(value = "统一社会信用代码")
    private String creditCode;

    @ApiModelPropertyCheck(value = "企业性质")
    private String businessNature;

    @ApiModelPropertyCheck(value = "法人")
    private String legalPerson;

    @ApiModelPropertyCheck(value = "注册资本")
    private BigDecimal registeredCapital;

    @ApiModelPropertyCheck(value = "注册时间")
    private Date registrationDate;

    @ApiModelPropertyCheck(value = "企业规模")
    private String scale;

    @ApiModelPropertyCheck(value = "行政区域")
    private String code;

    @ApiModelPropertyCheck(value = "企业详细地址")
    private String address;

    @ApiModelPropertyCheck(value = "经营许可证发证单位")
    private String licenseIssuingAuthority;

    @ApiModelPropertyCheck(value = "经营许可证编号")
    private String licenseNumber;

    @ApiModelPropertyCheck(value = "经营许可证有效期至")
    private Date licenseExpiryDate;

    @ApiModelPropertyCheck(value = "公司经营范围")
    private String businessScope;

    @ApiModelPropertyCheck(value = "销售范围")
    private String salesScope;

    @ApiModelPropertyCheck(value = "燃气经营许可供气范围")
    private String gasSupplyScope;

    @ApiModelPropertyCheck(value = "联系人")
    private String contactPerson;

    @ApiModelPropertyCheck(value = "联系电话")
    private String contactPhone;
}