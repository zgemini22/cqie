package com.zds.biz.vo.request.user;

import com.zds.biz.vo.BaseRequest;
import io.swagger.annotations.ApiModel;
import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import lombok.Data;

@Data
@ApiModel(description = "组织人员查询请求")
public class OrganizationPersonSelectRequest extends BaseRequest {

    @ApiModelPropertyCheck(value = "部门")
    private String department;

    @ApiModelPropertyCheck(value = "所属城燃企业ID")
    private Long orgId;

    @ApiModelPropertyCheck(value = "姓名")
    private String name;

    @ApiModelPropertyCheck(value = "岗位类型")
    private String postType;

    @ApiModelPropertyCheck(value = "是否持证 1=是 0=否")
    private Integer hasCertified;

    @ApiModelPropertyCheck(value = "证书编号")
    private String certifiedNo;

    @ApiModelPropertyCheck(value = "取证时间-开始")
    private String getCertifiedTimeStart;

    @ApiModelPropertyCheck(value = "取证时间-结束")
    private String getCertifiedTimeEnd;

    @ApiModelPropertyCheck(value = "证书有效期-开始")
    private String certifiedValidityStart;

    @ApiModelPropertyCheck(value = "证书有效期-结束")
    private String certifiedValidityEnd;

    @Override
    public void toRequestCheck() {
    }
}