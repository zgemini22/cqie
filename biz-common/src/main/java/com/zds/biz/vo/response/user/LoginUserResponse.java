package com.zds.biz.vo.response.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import com.zds.biz.vo.SystemInfoVo;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@ApiModel(description = "当前登录用户信息返回")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginUserResponse {

    @ApiModelPropertyCheck(value="用户ID")
    private Long id;

    @ApiModelPropertyCheck(value="用户名称")
    private String name;

    @ApiModelPropertyCheck(value="用户头像文件")
    private String fileHead;

    @ApiModelPropertyCheck(value="账号")
    private String loginName;

    @ApiModelPropertyCheck(value="手机号")
    private String phone;

    @ApiModelPropertyCheck(value="邮箱")
    private String email;

    @ApiModelPropertyCheck(value="备注")
    private String remarks;

    @ApiModelPropertyCheck(value="用户状态,字典group_id=USER_STATUS")
    private String userStatus;

    @ApiModelPropertyCheck(value="组织ID")
    private Long organizationId;

    @ApiModelPropertyCheck(value="组织名称")
    private String organizationName;

    @ApiModelPropertyCheck("组织类别,字典group_id=ORGANIZATION_TYPE")
    private String organizationType;

    @ApiModelPropertyCheck(value = "角色ID")
    private Long roleId;

    @ApiModelPropertyCheck(value = "角色名称")
    private String roleName;

    @ApiModelPropertyCheck(value="菜单集合")
    private List<MenuListResponse> menus;

    @ApiModelPropertyCheck("上次登录时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date lastLoginTime;

    @ApiModelPropertyCheck(value = "是否可查看设备管理系统")
    private Boolean deviceFlag = false;

    @ApiModelPropertyCheck(value = "可用系统信息")
    private List<SystemInfoVo> systemInfoList;
}
