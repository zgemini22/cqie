package com.zds.biz.vo.request.user;

import io.swagger.annotations.ApiModel;
import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import lombok.Data;

@Data
@ApiModel(description = "APP版本信息新增请求")
public class AppVersionAddRequest {

    @ApiModelPropertyCheck(value = "app类型,字典group_id=APP_TYPE", required = true)
    private String appType;

    @ApiModelPropertyCheck(value="版本号", required = true, example = "1")
    private String versionCode;

    @ApiModelPropertyCheck(value="更新备注")
    private String remarks;

    @ApiModelPropertyCheck(value="状态,1:已发布,2:未发布,默认未发布")
    private Integer status;

    @ApiModelPropertyCheck(value="下载地址", required = true, example = "1")
    private String downloadUrl;

    @ApiModelPropertyCheck(value = "系统类型,ios/android", required = true)
    private String systemType;
}
