package com.zds.biz.vo.request.user;

import com.zds.biz.constant.BaseException;
import com.zds.biz.constant.user.RoleStatusEnum;
import com.zds.biz.vo.BaseRequest;
import io.swagger.annotations.ApiModel;
import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import lombok.Data;

@Data
@ApiModel(description = "更新角色状态请求")
public class RoleStatusRequest extends BaseRequest {

    @ApiModelPropertyCheck(value = "角色ID", required = true, example = "1")
    private Long id;

    @ApiModelPropertyCheck(value="角色状态,字典group_id=ROLE_STATUS", required = true, example = "ENABLE")
    private String roleStatus;

    @Override
    public void toRequestCheck() {
        if (id == null) {
            throw new BaseException("角色ID不能为空");
        }
        if (RoleStatusEnum.query(roleStatus) == null) {
            throw new BaseException("角色状态值不在允许范围");
        }
    }
}
