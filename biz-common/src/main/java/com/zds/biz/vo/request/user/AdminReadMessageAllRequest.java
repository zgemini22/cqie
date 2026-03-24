package com.zds.biz.vo.request.user;

import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import com.zds.biz.vo.BaseRequest;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(description = "设置消息状态为已读")
public class AdminReadMessageAllRequest extends BaseRequest {

    @ApiModelPropertyCheck(value = "消息分组,字典group_id=MESSAGE_GROUP")
    private List<String> messageGroup;
}
