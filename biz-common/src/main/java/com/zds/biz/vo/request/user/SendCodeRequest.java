package com.zds.biz.vo.request.user;

import com.zds.biz.constant.BaseException;
import com.zds.biz.vo.BaseRequest;
import io.swagger.annotations.ApiModel;
import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

@ApiModel(description = "发送验证码请求")
@Data
public class SendCodeRequest extends BaseRequest {

    @ApiModelPropertyCheck(value = "手机号", required = true, example = "138xxxxxxxx")
    private String phone;

    @Override
    public void toRequestCheck() {
        if (StringUtils.isEmpty(phone)) {
            throw new BaseException("手机号不能为空");
        }
    }
}
