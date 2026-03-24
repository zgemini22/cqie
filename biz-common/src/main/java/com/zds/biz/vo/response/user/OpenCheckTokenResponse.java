package com.zds.biz.vo.response.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@ApiModel(description = "校验token有效性")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OpenCheckTokenResponse {

    @ApiModelPropertyCheck(value = "token是否有效")
    private Boolean flag;

    @ApiModelPropertyCheck(value = "失效时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date failureTime;
}
