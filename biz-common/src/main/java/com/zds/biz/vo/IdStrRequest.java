package com.zds.biz.vo;

import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import io.swagger.annotations.ApiModel;
import lombok.Data;

@ApiModel(description = "业务ID字符串请求")
@Data
public class IdStrRequest extends BaseRequest {

    @ApiModelPropertyCheck(value = "业务ID")
    private String id;
}
