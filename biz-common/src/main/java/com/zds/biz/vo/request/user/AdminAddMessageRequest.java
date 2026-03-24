package com.zds.biz.vo.request.user;

import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import com.zds.biz.vo.BaseRequest;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(description = "新增消息")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminAddMessageRequest extends BaseRequest {

    @ApiModelPropertyCheck(value = "用户ID", required = true)
    private Long userId;

    @ApiModelPropertyCheck(value = "消息来源", required = true)
    private String messageSource;

    @ApiModelPropertyCheck(value = "标题", required = true, min = 1, max = 100)
    private String messageTitle;

    @ApiModelPropertyCheck("内容")
    private String messageContent;

    @ApiModelPropertyCheck("跳转标识")
    private String jumpUrl;

    @ApiModelPropertyCheck(value = "消息分组,字典group_id=MESSAGE_GROUP", required = true)
    private String messageGroup;
}
