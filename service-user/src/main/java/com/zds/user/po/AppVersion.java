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
@TableName("app_version")
@ApiModel(value = "AppVersion对象", description = "app版本记录")
public class AppVersion implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelPropertyCheck(value = "版本ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelPropertyCheck(value = "app类型,字典group_id=APP_TYPE")
    private String appType;

    @ApiModelPropertyCheck(value = "版本号")
    private String versionCode;

    @ApiModelPropertyCheck(value = "更新备注")
    private String remarks;

    @ApiModelPropertyCheck(value = "状态,1:已发布,2:未发布")
    private Integer status;

    @ApiModelPropertyCheck(value = "下载地址")
    private String downloadUrl;

    @ApiModelPropertyCheck(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;

    @ApiModelPropertyCheck(value = "创建人")
    private Long createId;

    @ApiModelPropertyCheck(value = "系统类型,ios/android")
    private String systemType;

    public static LambdaQueryWrapper<AppVersion> getWrapper() {
        return new LambdaQueryWrapper<>();
    }
}
