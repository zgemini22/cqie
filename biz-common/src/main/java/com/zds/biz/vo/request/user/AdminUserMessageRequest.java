package com.zds.biz.vo.request.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import com.zds.biz.vo.PageRequest;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@ApiModel("查询当前登录用户消息列表请求")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminUserMessageRequest extends PageRequest {

    @ApiModelPropertyCheck("消息状态: true.已读 false.未读")
    private Boolean readFlag;

    @ApiModelPropertyCheck(value = "标题")
    private String messageTitle;

    @ApiModelPropertyCheck(value = "开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date startTime;

    @ApiModelPropertyCheck(value = "结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date endTime;

    @ApiModelPropertyCheck(value = "消息分组,字典group_id=MESSAGE_GROUP")
    private List<String> messageGroup;
}
