package com.zds.user.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import io.swagger.annotations.ApiModel;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("tbl_organization_info")
@ApiModel(value = "TblOrganizationInfo对象", description = "企业详细信息")
public class TblOrganizationInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelPropertyCheck(value = "组织ID")
    private Long orgId;

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
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
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
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
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

    @ApiModelPropertyCheck(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    @ApiModelPropertyCheck(value = "创建人")
    private Long createId;

    @ApiModelPropertyCheck(value = "修改时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    @ApiModelPropertyCheck(value = "修改人")
    private Long updateId;

    @ApiModelPropertyCheck(value = "是否删除")
    private Boolean deleted;
}