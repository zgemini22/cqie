package com.zds.biz.vo.request.user;

import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import com.zds.biz.vo.PageRequest;
import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel(description = "APP版本信息列表请求")
public class AppVersionFindRequest extends PageRequest {

    @ApiModelPropertyCheck(value = "系统类型,ios/android")
    private String systemType;

    @ApiModelPropertyCheck(value = "app类型,字典group_id=APP_TYPE", required = true)
    private String appType;

    @ApiModelPropertyCheck(value="状态,1:已发布,2:未发布,默认未发布")
    private Integer status;
}
