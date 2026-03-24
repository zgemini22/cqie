package com.zds.biz.vo.response.user;

import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(description = "用户详情返回")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailResponse {

    @ApiModelPropertyCheck(value = "用户ID")
    private Long id;

    @ApiModelPropertyCheck(value = "用户名称")
    private String name;

    @ApiModelPropertyCheck(value = "角色ID")
    private Long roleId;

    @ApiModelPropertyCheck(value = "角色名称")
    private String roleName;

    @ApiModelPropertyCheck(value = "手机号")
    private String phone;

    @ApiModelPropertyCheck(value = "身份证")
    private String idCard;

    @ApiModelPropertyCheck(value = "单位ID")
    private Long organizationId;

    @ApiModelPropertyCheck(value = "单位名称")
    private String organizationName;

    @ApiModelPropertyCheck(value = "单位类别,字典group_id=ORGANIZATION_TYPE")
    private String organizationType;

    @ApiModelPropertyCheck(value = "单位类别名称")
    private String organizationTypeName;

    @ApiModelPropertyCheck(value = "用户状态,字典group_id=USER_STATUS")
    private String userStatus;

    @ApiModelPropertyCheck(value = "邮箱")
    private String email;

    @ApiModelPropertyCheck(value = "备注")
    private String remark;

    @ApiModelPropertyCheck(value="用户头像文件")
    private String fileHead;
}
