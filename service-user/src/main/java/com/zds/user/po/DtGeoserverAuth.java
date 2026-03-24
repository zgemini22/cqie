package com.zds.user.po;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
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
@TableName("dt_geoserver_auth")
@ApiModel(value = "DtGeoserverAuth对象", description = "geoserver登录校验")
public class DtGeoserverAuth implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelPropertyCheck(value = "授权码")
    private String authKey;

    @ApiModelPropertyCheck(value = "失效时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private LocalDateTime expiredTime;

    @ApiModelPropertyCheck(value = "授权token")
    private String token;

    @ApiModelPropertyCheck(value = "创建日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private LocalDateTime createDate;

    @ApiModelPropertyCheck(value = "1:有效， 2：无效")
    private Long validState;

    public static LambdaQueryWrapper<DtGeoserverAuth> getWrapper() {
        return new LambdaQueryWrapper<>();
    }
}
