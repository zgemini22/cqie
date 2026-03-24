package com.zds.biz.vo.request.user;

import com.zds.biz.vo.BaseRequest;
import io.swagger.annotations.ApiModel;
import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import lombok.Data;

@Data
@ApiModel(description = "单位下拉请求")
public class OrgSelectRequest extends BaseRequest {

    @ApiModelPropertyCheck(value = "单位ID")
    private Long id;

    @ApiModelPropertyCheck(value="单位类别,字典group_id=ORGANIZATION_TYPE")
    private String organizationType;

    @ApiModelPropertyCheck(value="是否权限过滤")
    private boolean dataFlag = true;
}
