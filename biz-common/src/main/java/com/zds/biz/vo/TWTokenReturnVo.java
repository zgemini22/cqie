package com.zds.biz.vo;

import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(description = "天网摄像头token返回")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TWTokenReturnVo {

    @ApiModelPropertyCheck(value = "token")
    private String access_token;

}
