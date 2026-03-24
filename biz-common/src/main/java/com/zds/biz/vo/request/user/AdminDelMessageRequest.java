package com.zds.biz.vo.request.user;

import com.zds.biz.vo.BaseRequest;
import io.swagger.annotations.ApiModel;
import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import lombok.Data;

@Data
@ApiModel(description = "删除消息")
public class AdminDelMessageRequest extends BaseRequest {

    @ApiModelPropertyCheck("消息ID")
    private Long id;

}
