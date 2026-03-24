package com.zds.biz.vo.request.user;

import com.zds.biz.constant.BaseException;
import com.zds.biz.vo.BaseRequest;
import io.swagger.annotations.ApiModel;
import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

@ApiModel(description = "修改绑定手机号请求")
@Data
public class PhoneUpdateRequest extends BaseRequest {

    @ApiModelPropertyCheck(value = "手机验证码", required = true, example = "1234")
    private String code;

    @ApiModelPropertyCheck(value = "新手机号", required = true, example = "138xxxxxxxx")
    private String newPhone;

    @Override
    public void toRequestCheck() {
        if (StringUtils.isEmpty(code)) {
            throw new BaseException("手机验证码不能为空");
        }
        if (StringUtils.isEmpty(newPhone)) {
            throw new BaseException("新手机号不能为空");
        }
    }
}
