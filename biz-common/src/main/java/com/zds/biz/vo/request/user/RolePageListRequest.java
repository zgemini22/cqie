package com.zds.biz.vo.request.user;


import com.zds.biz.constant.BaseException;
import com.zds.biz.constant.user.RoleStatusEnum;
import com.zds.biz.vo.PageRequest;
import io.swagger.annotations.ApiModel;
import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

@ApiModel(description = "角色分页列表请求")
@Data
public class RolePageListRequest extends PageRequest {

    @ApiModelPropertyCheck(value = "组织ID")
    private Long organizationId;

    @ApiModelPropertyCheck(value = "角色名称", example = "name")
    private String roleName;

    @ApiModelPropertyCheck(value = "角色描述", example = "name")
    private String remarks;

    @ApiModelPropertyCheck(value = "角色状态,字典group_id=ROLE_STATUS", example = "ENABLE")
    private String roleStatus;

    @Override
    public void toRequestCheck() {
        if (StringUtils.isNotEmpty(roleStatus) && RoleStatusEnum.query(roleStatus) == null) {
            throw new BaseException("角色状态值不在允许范围");
        }
    }
}
