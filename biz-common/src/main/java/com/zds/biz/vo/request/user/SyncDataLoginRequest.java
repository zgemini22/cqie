package com.zds.biz.vo.request.user;

import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel(description = "同步数据-模拟登录请求")
public class SyncDataLoginRequest {

    @ApiModelPropertyCheck(value = "账号")
    private String loginName;

    @ApiModelPropertyCheck(value = "组织ID")
    private Long orgId;

    @ApiModelPropertyCheck(value = "登录密钥")
    private String secretValue;
}
