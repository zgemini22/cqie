package com.zds.biz.vo.response.user;

import io.swagger.annotations.ApiModel;
import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@ApiModel(description = "企业列表返回")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrgCompanyResponse {

    @ApiModelPropertyCheck(value = "企业ID")
    private Long id;

    @ApiModelPropertyCheck(value = "企业名称")
    private String name;

    @ApiModelPropertyCheck(value = "简称")
    private String shortName;

    @ApiModelPropertyCheck(value = "统一社会信用代码")
    private String creditCode;

    @ApiModelPropertyCheck(value = "注册地址")
    private String address;

    @ApiModelPropertyCheck(value = "经营许可证编号")
    private String licenseNumber;

    @ApiModelPropertyCheck(value = "经营许可证有效期至")
    private Date licenseExpiryDate;
}