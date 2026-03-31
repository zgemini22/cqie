package com.zds.user.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import io.swagger.annotations.ApiModel;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonFormat;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("tbl_organization_person")
@ApiModel(value = "TblOrganizationPerson对象", description = "组织人员信息")
public class TblOrganizationPerson implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelPropertyCheck(value = "主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    @TableField("id")
    private Long id;

    @ApiModelPropertyCheck(value = "姓名")
    @TableField("name")
    private String name;

    @ApiModelPropertyCheck(value = "性别")
    @TableField("gender")
    private String gender;

    @ApiModelPropertyCheck(value = "年龄")
    @TableField("age")
    private Integer age;

    @ApiModelPropertyCheck(value = "文化水平(字典值)")
    @TableField("education")
    private String education;

    @ApiModelPropertyCheck(value = "身份证号")
    @TableField("id_card")
    private String idCard;

    @ApiModelPropertyCheck(value = "联系电话")
    @TableField("phone")
    private String phone;

    @ApiModelPropertyCheck(value = "所在部门")
    @TableField("department")
    private String department;

    @ApiModelPropertyCheck(value = "岗位类型(字典值)")
    @TableField("post_type")
    private String postType;

    @ApiModelPropertyCheck(value = "职称")
    @TableField("professional")
    private String professional;

    @ApiModelPropertyCheck(value = "工作岗位")
    @TableField("job_position")
    private String jobPosition;

    @ApiModelPropertyCheck(value = "专业技能")
    @TableField("skills")
    private String skills;

    @ApiModelPropertyCheck(value = "工作年限")
    @TableField("working_years")
    private Integer workingYears;

    @ApiModelPropertyCheck(value = "是否持证")
    @TableField("hasCertified")
    private Integer hasCertified;

    @ApiModelPropertyCheck(value = "证书编号")
    @TableField("certifiedNo")
    private String certifiedNo;

    @ApiModelPropertyCheck(value = "领证时间")
    @TableField("getCertifiedTime")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date getCertifiedTime;

    @ApiModelPropertyCheck(value = "证书有效期至")
    @TableField("certifiedValidity")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date certifiedValidity;

    @ApiModelPropertyCheck(value = "发证部门")
    @TableField("issuingUnit")
    private String issuingUnit;

    @ApiModelPropertyCheck(value = "备注")
    @TableField("remark")
    private String remark;

    @ApiModelPropertyCheck(value = "所属城燃企业ID")
    @TableField("org_id")
    private Long orgId;

    @ApiModelPropertyCheck(value = "创建时间")
    @TableField("created_at")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createdAt;

    @ApiModelPropertyCheck(value = "更新时间")
    @TableField("updated_at")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updatedAt;

    @ApiModelPropertyCheck(value = "软删除时间")
    @TableField("deleted_at")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date deletedAt;

    public static LambdaQueryWrapper<TblOrganizationPerson> getWrapper() {
        return new LambdaQueryWrapper<>();
    }
}