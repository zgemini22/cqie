package com.zds.biz.vo.request.user;

import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import com.zds.biz.vo.BaseRequest;
import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel(description = "用户登录请求")
public class UserClientLoginRequest extends BaseRequest {

    @ApiModelPropertyCheck(value = "账号", required = true, example = "admin")
    private String loginName;

    @ApiModelPropertyCheck(value = "密码", required = true, example = "admin")
    private String password;
}
