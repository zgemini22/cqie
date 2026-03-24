package com.zds.user.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("tbl_log")
@ApiModel(value = "TblLog对象", description = "操作日志")
public class TblLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelPropertyCheck(value = "操作日志ID")
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

    @ApiModelPropertyCheck(value = "创建人组织ID")
    private Long organizationId;

    @ApiModelPropertyCheck(value = "系统模块")
    private String systemModule;

    @ApiModelPropertyCheck(value = "操作类型")
    private String type;

    @ApiModelPropertyCheck(value = "操作状态")
    private String status;

    @ApiModelPropertyCheck(value = "操作时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date date;

    @ApiModelPropertyCheck(value = "请求地址")
    private String requestUrl;

    @ApiModelPropertyCheck(value = "请求方法")
    private String requestMethod;

    @ApiModelPropertyCheck(value = "请求参数")
    private String requestParam;

    @ApiModelPropertyCheck(value = "请求IP")
    private String requestIp;

    @ApiModelPropertyCheck(value = "请求端口")
    private String requestPort;

    @ApiModelPropertyCheck(value = "接口响应时间")
    private Long duration;
    public static LambdaQueryWrapper<TblLog> getWrapper() {
        return new LambdaQueryWrapper<>();
    }
}
