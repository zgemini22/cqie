package com.zds.biz.vo.request.user;

import com.zds.biz.constant.BaseException;
import com.zds.biz.vo.BaseRequest;
import io.swagger.annotations.ApiModel;
import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import lombok.Data;

@ApiModel(description = "修改密码请求")
@Data
public class PwdChangeOldRequest extends BaseRequest {

    @ApiModelPropertyCheck(value = "旧密码", required = true, example = "1234")
    private String oldPwd;

    @ApiModelPropertyCheck(value = "新密码", required = true, example = "3")
    private String newPwd;

    @ApiModelPropertyCheck(value = "确认新密码", required = true, example = "4")
    private String resetNewPwd;

    @Override
    public void toRequestCheck() {
        if (!newPwd.equals(resetNewPwd)) {
            throw new BaseException("两次输入的密码不一致");
        }
    }
}
