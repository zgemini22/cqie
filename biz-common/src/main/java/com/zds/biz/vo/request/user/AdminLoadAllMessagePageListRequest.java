package com.zds.biz.vo.request.user;

import com.zds.biz.vo.PageRequest;
import io.swagger.annotations.ApiModel;
import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel("所有消息分页请求")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminLoadAllMessagePageListRequest extends PageRequest {

    @ApiModelPropertyCheck("消息状态: true.已读 false.未读")
    private Boolean readFlag;

    @ApiModelPropertyCheck(value = "消息分组,字典group_id=MESSAGE_GROUP")
    private String messageGroup;
}
