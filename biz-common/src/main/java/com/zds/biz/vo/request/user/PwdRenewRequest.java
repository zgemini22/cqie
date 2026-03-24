package com.zds.biz.vo.request.user;

import com.zds.biz.constant.BaseException;
import com.zds.biz.vo.BaseRequest;
import io.swagger.annotations.ApiModel;
import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

@ApiModel(description = "忘记密码请求")
@Data
public class PwdRenewRequest extends BaseRequest {

    @ApiModelPropertyCheck(value = "手机号", required = true, example = "138xxxxxxxx")
    private String phone;

    @ApiModelPropertyCheck(value = "验证码", required = true, example = "1234")
    private String code;

    @ApiModelPropertyCheck(value = "新密码", required = true, example = "3")
    private String newPwd;

    @ApiModelPropertyCheck(value = "确认新密码", required = true, example = "4")
    private String resetNewPwd;

    @Override
    public void toRequestCheck() {
        if (StringUtils.isEmpty(phone)) {
            throw new BaseException("手机号不能为空");
        }
        if (StringUtils.isEmpty(code)) {
            throw new BaseException("手机验证码不能为空");
        }
        if (StringUtils.isEmpty(newPwd)) {
            throw new BaseException("新密码不能为空");
        }
        if (StringUtils.isEmpty(resetNewPwd)) {
            throw new BaseException("确认新密码不能为空");
        }
        if (!newPwd.equals(resetNewPwd)) {
            throw new BaseException("两次输入的密码不一致");
        }
    }
}
