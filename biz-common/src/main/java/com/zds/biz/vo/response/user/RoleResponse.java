package com.zds.biz.vo.response.user;

import io.swagger.annotations.ApiModel;
import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(description = "角色列表返回")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleResponse {

    @ApiModelPropertyCheck(value="角色ID")
    private Long id;

    @ApiModelPropertyCheck(value="角色名称")
    private String roleName;

    @ApiModelPropertyCheck(value="角色描述")
    private String remarks;

    @ApiModelPropertyCheck(value="角色状态,字典group_id=ROLE_STATUS")
    private String roleStatus;

    @ApiModelPropertyCheck(value="角色类型,字典group_id=ROLE_TYPE")
    private String roleType;

    @ApiModelPropertyCheck(value = "组织ID")
    private Long organizationId;

    @ApiModelPropertyCheck(value = "组织名称")
    private String organizationName;
}
