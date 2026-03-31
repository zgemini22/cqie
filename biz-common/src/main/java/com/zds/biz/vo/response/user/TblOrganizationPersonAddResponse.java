package com.zds.biz.vo.response.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel(value = "TblOrganizationPersonAddResponse", description = "从业人员信息响应")
public class TblOrganizationPersonAddResponse {

    @ApiModelProperty(value = "主键ID")
    private Long id;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "性别")
    private String gender;

    @ApiModelProperty(value = "年龄")
    private Integer age;

    @ApiModelProperty(value = "文化水平")
    private String education;

    @ApiModelProperty(value = "身份证号")
    private String idCard;

    @ApiModelProperty(value = "联系电话")
    private String phone;

    @ApiModelProperty(value = "所在部门")
    private String department;

    @ApiModelProperty(value = "岗位类型")
    private String postType;

    @ApiModelProperty(value = "职称")
    private String professional;

    @ApiModelProperty(value = "工作岗位")
    private String jobPosition;

    @ApiModelProperty(value = "专业技能")
    private String skills;

    @ApiModelProperty(value = "工作年限")
    private Integer workingYears;

    @ApiModelProperty(value = "是否持证 0否 1是")
    private Integer hasCertified;

    @ApiModelProperty(value = "证书编号")
    private String certifiedNo;

    @ApiModelProperty(value = "取证时间")
    private Date getCertifiedTime;

    @ApiModelProperty(value = "证书有效期至")
    private Date certifiedValidity;

    @ApiModelProperty(value = "发证单位")
    private String issuingUnit;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "所属组织ID")
    private Long orgId;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;
}