package com.zds.biz.vo.request.user;

import com.zds.biz.vo.BaseRequest;
import io.swagger.annotations.ApiModel;
import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import lombok.Data;

@Data
@ApiModel(description = "更新单位状态请求")
public class OrgStatusRequest extends BaseRequest {

    @ApiModelPropertyCheck(value = "单位ID", required = true, example = "1")
    private Long id;

    @ApiModelPropertyCheck(value="单位状态,字典group_id=ORGANIZATION_STATUS", required = true, example = "ENABLE")
    private String organizationStatus;
}
