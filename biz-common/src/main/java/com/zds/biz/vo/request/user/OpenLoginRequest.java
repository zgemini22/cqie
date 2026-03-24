package com.zds.biz.vo.request.user;

import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(description = "获取认证token")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OpenLoginRequest {

    @ApiModelPropertyCheck(value = "appId", required = true)
    private String appId;

    @ApiModelPropertyCheck(value = "appSecret", required = true)
    private String appSecret;
}
