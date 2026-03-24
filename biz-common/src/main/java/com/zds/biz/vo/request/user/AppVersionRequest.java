package com.zds.biz.vo.request.user;

import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel(description = "APP版本信息新增请求")
public class AppVersionRequest {

    @ApiModelPropertyCheck(value = "系统类型,ios/android", required = true)
    private String systemType;
}
