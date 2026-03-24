package com.zds.biz.vo.request.user;

import com.zds.biz.constant.BaseException;
import com.zds.biz.vo.BaseRequest;
import io.swagger.annotations.ApiModel;
import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import lombok.Data;


@Data
@ApiModel(description = "修改消息")
public class AdminModifyMessageRequest extends BaseRequest {

    @ApiModelPropertyCheck("消息ID")
    private Long id;

    @ApiModelPropertyCheck("组织ID")
    private Long organizationId;

    @ApiModelPropertyCheck("用户ID")
    private Long userId;

    @ApiModelPropertyCheck("标题")
    private String messageTitle;

    @ApiModelPropertyCheck("内容")
    private String messageContent;

    @ApiModelPropertyCheck("跳转标识")
    private String jumpUrl;

    @Override
    public void toRequestCheck() {
        if(id == null || id < 1){
            throw new BaseException("消息ID不能为空");
        }
        if(organizationId == null || organizationId < 1){
            throw new BaseException("组织ID不能为空");
        }
        if(userId == null || userId<1){
            throw new BaseException("用户ID不能为空");
        }
        if(messageTitle == null){
            throw new BaseException("消息标题不能为空");
        }
        if(messageContent == null){
            throw new BaseException("消息内容不能为空");
        }
    }
}
