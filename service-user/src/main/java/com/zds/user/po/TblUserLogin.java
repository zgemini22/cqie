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
@TableName("tbl_user_login")
@ApiModel(value = "TblUserLogin对象", description = "用户登录记录")
public class TblUserLogin implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelPropertyCheck(value = "登录记录ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelPropertyCheck(value = "用户ID")
    private Long userId;

    @ApiModelPropertyCheck(value = "登录时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date loginTime;

    @ApiModelPropertyCheck(value = "登录IP")
    private String loginIp;

    @ApiModelPropertyCheck(value = "登录渠道,1:管理后台web,2:客户端APP,3:设备管理web")
    private Integer loginWay;

    public static LambdaQueryWrapper<TblUserLogin> getWrapper() {
        return new LambdaQueryWrapper<>();
    }
}
