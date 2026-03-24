package com.zds.biz.vo.request.user;

import com.zds.biz.constant.BaseException;
import com.zds.biz.constant.user.RoleStatusEnum;
import com.zds.biz.vo.BaseRequest;
import io.swagger.annotations.ApiModel;
import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(description = "角色保存请求")
public class RoleSaveRequest extends BaseRequest {

    @ApiModelPropertyCheck(value = "角色ID,传为修改,不传为新增", example = "1")
    private Long id;

    @ApiModelPropertyCheck(value = "组织ID", required = true)
    private Long organizationId;

    @ApiModelPropertyCheck(value = "角色名称", required = true, example = "角色1")
    private String roleName;

    @ApiModelPropertyCheck(value = "角色描述", example = "角色1")
    private String remarks;

    @ApiModelPropertyCheck(value = "角色状态,字典group_id=ROLE_STATUS", required = true)
    private String roleStatus;

    @ApiModelPropertyCheck(value = "菜单ID集合")
    private List<Long> menuIds;

    @Override
    public void toRequestCheck() {
        if (RoleStatusEnum.query(roleStatus) == null) {
            throw new BaseException("角色状态值不在允许范围");
        }
    }
}
