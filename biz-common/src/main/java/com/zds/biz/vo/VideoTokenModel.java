package com.zds.biz.vo;

import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import io.swagger.annotations.ApiModel;
import lombok.Data;

@ApiModel(value = "VideoToken基础类")
@Data
public class VideoTokenModel  {
    @ApiModelPropertyCheck(value = "token")
    private String token;

    @ApiModelPropertyCheck(value = "过期时间")
    private String expires_in;
}
