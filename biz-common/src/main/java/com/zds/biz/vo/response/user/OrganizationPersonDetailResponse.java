package com.zds.biz.vo.response.user;

import io.swagger.annotations.ApiModel;
import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@ApiModel(description = "组织人员详情返回")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationPersonDetailResponse {

    @ApiModelPropertyCheck(value="人员ID")
    private Long id;

    @ApiModelPropertyCheck(value="姓名")
    private String name;

    @ApiModelPropertyCheck(value="性别")
    private String gender;

    @ApiModelPropertyCheck(value="年龄")
    private Integer age;

    @ApiModelPropertyCheck(value="文化水平(字典值)")
    private String education;

    @ApiModelPropertyCheck(value="身份证号")
    private String idCard;

    @ApiModelPropertyCheck(value="联系电话")
    private String phone;

    @ApiModelPropertyCheck(value="所在部门")
    private String department;

    @ApiModelPropertyCheck(value="岗位类型(字典值)")
    private String postType;

    @ApiModelPropertyCheck(value="职称")
    private String professional;

    @ApiModelPropertyCheck(value="工作岗位")
    private String jobPosition;

    @ApiModelPropertyCheck(value="专业技能")
    private String skills;

    @ApiModelPropertyCheck(value="工作年限")
    private Integer workingYears;

    @ApiModelPropertyCheck(value="是否持证")
    private Integer hasCertified;

    @ApiModelPropertyCheck(value="证书编号")
    private String certifiedNo;

    @ApiModelPropertyCheck(value="领证时间")
    private Date getCertifiedTime;

    @ApiModelPropertyCheck(value="证书有效期至")
    private Date certifiedValidity;

    @ApiModelPropertyCheck(value="发证部门")
    private String issuingUnit;

    @ApiModelPropertyCheck(value="备注")
    private String remark;

    @ApiModelPropertyCheck(value="所属城燃企业ID")
    private Long orgId;
}