package com.zds.biz.vo.request.user;

import com.zds.biz.vo.BaseRequest;
import io.swagger.annotations.ApiModel;
import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import lombok.Data;

@Data
@ApiModel(description = "用户登录请求")
public class UserLoginRequest extends BaseRequest {

    @ApiModelPropertyCheck(value = "账号", required = true, example = "admin")
    private String loginName;

    @ApiModelPropertyCheck(value = "密码", required = true, example = "admin")
    private String password;

    @ApiModelPropertyCheck(value = "验证码", required = true, example = "1234")
    private String code;

    @ApiModelPropertyCheck(value = "验证码key", required = true, example = "9b63e675d5e2f40610fd9d750439bdbddbee7dc19aac18d863bf88684f98791f")
    private String rightCode;
}
