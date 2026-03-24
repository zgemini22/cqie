package com.zds.biz.vo.response.user;

import io.swagger.annotations.ApiModel;
import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@ApiModel(description = "角色详情返回")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleDetailResponse {

    @ApiModelPropertyCheck(value = "角色ID")
    private Long id;

    @ApiModelPropertyCheck(value = "角色名称")
    private String roleName;

    @ApiModelPropertyCheck(value = "角色状态,字典group_id=ROLE_STATUS")
    private String roleStatus;

    @ApiModelPropertyCheck(value = "组织ID")
    private Long organizationId;

    @ApiModelPropertyCheck(value = "组织名称")
    private String organizationName;

    @ApiModelPropertyCheck(value = "菜单集合")
    private List<MenuListResponse> menus;

    @ApiModelPropertyCheck("角色描述")
    private String remarks;
}
