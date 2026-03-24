package com.zds.biz.vo.request.user;

import com.zds.biz.vo.PageRequest;
import io.swagger.annotations.ApiModel;
import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import lombok.Data;

@ApiModel(description = "用户列表请求")
@Data
public class UserFindRequest extends PageRequest {

    @ApiModelPropertyCheck(value = "所属单位")
    private Long organizationId;

    @ApiModelPropertyCheck(value = "用户名")
    private String name;

    @ApiModelPropertyCheck(value = "所属角色")
    private Long roleId;

    @ApiModelPropertyCheck(value = "用户状态,字典group_id=USER_STATUS")
    private String userStatus;
}
