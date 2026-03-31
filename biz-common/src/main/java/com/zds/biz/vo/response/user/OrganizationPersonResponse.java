package com.zds.biz.vo.response.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@ApiModel(description = "组织人员列表返回")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationPersonResponse {

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

    @ApiModelPropertyCheck(value="联系电话")
    private String phone;

    @ApiModelPropertyCheck(value="所在部门")
    private String department;

    @ApiModelPropertyCheck(value="岗位类型")
    private String postType;

    @ApiModelPropertyCheck(value="职称")
    private String professional;

    @ApiModelPropertyCheck(value="工作岗位")
    private String jobPosition;

    @ApiModelPropertyCheck(value="所属城燃企业ID")
    private Long orgId;

    // ================== 以下为补全新增字段（和数据库对齐） ==================
    @ApiModelPropertyCheck(value="是否持证 1=是 0=否")
    private Integer hasCertified;

    @ApiModelPropertyCheck(value="证书编号")
    private String certifiedNo;

    @ApiModelPropertyCheck(value="取证时间")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date getCertifiedTime;

    @ApiModelPropertyCheck(value="证书有效期至")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date certifiedValidity;

    @ApiModelPropertyCheck(value="发证部门")
    private String issuingUnit;

    @ApiModelPropertyCheck(value="创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createdAt;
}