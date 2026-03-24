package com.zds.biz.vo.request.user;


import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import com.zds.biz.vo.BaseRequest;
import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel(description = "查询可见角色权限请求")
public class VisibleMenuRequest extends BaseRequest {

    @ApiModelPropertyCheck(value="单位ID")
    private Long organizationId;
}
