package com.zds.biz.vo.request.user;

import com.zds.biz.vo.BaseRequest;
import io.swagger.annotations.ApiModel;
import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@ApiModel(description = "保存企业请求")
public class OrgCompanySaveRequest extends BaseRequest {

    @ApiModelPropertyCheck(value = "企业ID,无则新增,有则修改")
    private Long id;

    @ApiModelPropertyCheck(value = "企业名称", required = true, max = 100)
    private String name;

    @ApiModelPropertyCheck(value = "简称", max = 50)
    private String shortName;

    @ApiModelPropertyCheck(value = "统一社会信用代码", max = 50)
    private String creditCode;

    @ApiModelPropertyCheck(value = "企业性质", max = 50)
    private String businessNature;

    @ApiModelPropertyCheck(value = "法人", max = 50)
    private String legalPerson;

    @ApiModelPropertyCheck(value = "注册资本")
    private BigDecimal registeredCapital;

    @ApiModelPropertyCheck(value = "注册时间")
    private Date registrationDate;

    @ApiModelPropertyCheck(value = "企业规模", max = 50)
    private String scale;

    @ApiModelPropertyCheck(value = "行政区域", max = 100)
    private String code;

    @ApiModelPropertyCheck(value = "企业详细地址", max = 200)
    private String address;

    @ApiModelPropertyCheck(value = "经营许可证发证单位", max = 100)
    private String licenseIssuingAuthority;

    @ApiModelPropertyCheck(value = "经营许可证编号", max = 50)
    private String licenseNumber;

    @ApiModelPropertyCheck(value = "经营许可证有效期至")
    private Date licenseExpiryDate;

    @ApiModelPropertyCheck(value = "公司经营范围")
    private String businessScope;

    @ApiModelPropertyCheck(value = "销售范围")
    private String salesScope;

    @ApiModelPropertyCheck(value = "燃气经营许可供气范围")
    private String gasSupplyScope;

    @ApiModelPropertyCheck(value = "联系人", max = 50)
    private String contactPerson;

    @ApiModelPropertyCheck(value = "联系电话", max = 20)
    private String contactPhone;
}