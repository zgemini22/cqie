package com.zds.biz.vo.request.user;

import com.zds.biz.constant.BaseException;
import com.zds.biz.vo.BaseRequest;
import io.swagger.annotations.ApiModel;
import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import lombok.Data;

@Data
@ApiModel(description = "保存组织人员请求")
public class OrganizationPersonSaveRequest extends BaseRequest {

    @ApiModelPropertyCheck(value = "人员ID,无则新增,有则修改")
    private Long id;

    @ApiModelPropertyCheck(value = "姓名", required = true, max = 50)
    private String name;

    @ApiModelPropertyCheck(value = "性别", max = 10)
    private String gender;

    @ApiModelPropertyCheck(value = "年龄")
    private Integer age;

    @ApiModelPropertyCheck(value = "文化水平(字典值)", max = 50)
    private String education;

    @ApiModelPropertyCheck(value = "身份证号", max = 18)
    private String idCard;

    @ApiModelPropertyCheck(value = "联系电话", max = 20)
    private String phone;

    @ApiModelPropertyCheck(value = "所在部门", max = 100)
    private String department;

    @ApiModelPropertyCheck(value = "岗位类型(字典值)", max = 50)
    private String postType;

    @ApiModelPropertyCheck(value = "职称", max = 100)
    private String professional;

    @ApiModelPropertyCheck(value = "工作岗位", max = 100)
    private String jobPosition;

    @ApiModelPropertyCheck(value = "专业技能")
    private String skills;

    @ApiModelPropertyCheck(value = "工作年限")
    private Integer workingYears;

    @ApiModelPropertyCheck(value = "是否持证")
    private Integer hasCertified;

    @ApiModelPropertyCheck(value = "证书编号", max = 50)
    private String certifiedNo;

    @ApiModelPropertyCheck(value = "领证时间")
    private String getCertifiedTime;

    @ApiModelPropertyCheck(value = "证书有效期至")
    private String certifiedValidity;

    @ApiModelPropertyCheck(value = "发证部门", max = 100)
    private String issuingUnit;

    @ApiModelPropertyCheck(value = "备注")
    private String remark;

    @ApiModelPropertyCheck(value = "所属城燃企业ID", required = true)
    private Long orgId;

    @Override
    public void toRequestCheck() {
        // 可以添加自定义的请求参数校验逻辑
    }
}