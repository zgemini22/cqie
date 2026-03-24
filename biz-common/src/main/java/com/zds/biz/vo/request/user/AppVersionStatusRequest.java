package com.zds.biz.vo.request.user;

import io.swagger.annotations.ApiModel;
import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import lombok.Data;

@Data
@ApiModel(description = "APP版本信息新增请求")
public class AppVersionStatusRequest {

    @ApiModelPropertyCheck(value="版本ID")
    private Long id;

    @ApiModelPropertyCheck(value="状态,1:已发布,2:未发布,默认未发布")
    private Integer status;
}
