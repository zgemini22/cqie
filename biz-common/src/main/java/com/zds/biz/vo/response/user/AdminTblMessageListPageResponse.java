package com.zds.biz.vo.response.user;


import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@ApiModel(description = "后台消息分页返回")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminTblMessageListPageResponse{

    @ApiModelPropertyCheck("消息ID")
    private Long id;

    @ApiModelPropertyCheck("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;

    @ApiModelPropertyCheck("创建人")
    private Long createId;

    @ApiModelPropertyCheck("修改时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date updateTime;

    @ApiModelPropertyCheck("修改人")
    private Long updateId;

    @ApiModelPropertyCheck("是否删除")
    private Boolean deleted;

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

    @ApiModelPropertyCheck("是否已读")
    private Boolean readFlag;

}
