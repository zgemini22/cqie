package com.zds.biz.vo.request.user;

import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(description = "校验token有效性")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OpenCheckTokenRequest {

    @ApiModelPropertyCheck(value = "token", required = true)
    private String token;
}
