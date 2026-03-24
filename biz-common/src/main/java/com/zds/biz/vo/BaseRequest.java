package com.zds.biz.vo;

import io.swagger.annotations.ApiModel;
import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import lombok.Data;

@ApiModel(description = "不公开基类请求对象")
@Data
public class BaseRequest implements RequestCheck {

    @ApiModelPropertyCheck(value = "用户token", hidden = true)
    private String token;

    @Override
    public void toRequestCheck() {

    }
}
