package com.zds.biz.vo.request.user;

import com.zds.biz.vo.BaseRequest;
import io.swagger.annotations.ApiModel;
import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import lombok.Data;

@ApiModel(description = "角色下拉列表请求")
@Data
public class RoleSelectRequest extends BaseRequest {

    @ApiModelPropertyCheck(value = "组织ID")
    private Long organizationId;
}
