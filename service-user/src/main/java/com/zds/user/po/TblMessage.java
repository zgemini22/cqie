package com.zds.user.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import io.swagger.annotations.ApiModel;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonFormat;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("tbl_message")
@ApiModel(value = "TblMessage对象", description = "消息记录")
public class TblMessage implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelPropertyCheck(value = "消息ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelPropertyCheck(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;

    @ApiModelPropertyCheck(value = "创建人")
    private Long createId;

    @ApiModelPropertyCheck(value = "修改时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date updateTime;

    @ApiModelPropertyCheck(value = "修改人")
    private Long updateId;

    @ApiModelPropertyCheck(value = "是否删除")
    private Boolean deleted;

    @ApiModelPropertyCheck(value = "组织ID")
    private Long organizationId;

    @ApiModelPropertyCheck(value = "用户ID")
    private Long userId;

    @ApiModelPropertyCheck(value = "标题")
    private String messageTitle;

    @ApiModelPropertyCheck(value = "内容")
    private String messageContent;

    @ApiModelPropertyCheck(value = "跳转标识")
    private String jumpUrl;

    @ApiModelPropertyCheck(value = "是否已读")
    private Boolean readFlag;

    @ApiModelPropertyCheck(value = "消息分组,字典group_id=MESSAGE_GROUP")
    private String messageGroup;

    @ApiModelPropertyCheck(value = "消息来源,字典group_id=MESSAGE_SOURCE")
    private String messageSource;

    public static LambdaQueryWrapper<TblMessage> getWrapper() {
        return new LambdaQueryWrapper<>();
    }
}
