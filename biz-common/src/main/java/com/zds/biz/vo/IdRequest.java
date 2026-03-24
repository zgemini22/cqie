package com.zds.biz.vo;

import io.swagger.annotations.ApiModel;
import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import lombok.Data;

@ApiModel(description = "业务ID请求")
@Data
public class IdRequest extends BaseRequest {

    @ApiModelPropertyCheck(value = "业务ID", example = "1")
    private Long id;
}
