package com.zds.biz.vo.request.user;

import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import io.swagger.annotations.ApiModel;
import lombok.Data;

@ApiModel(description = "闪信请求")
@Data
public class FlashLettersRequest {

    @ApiModelPropertyCheck(value="手机号")
    private String mobile;

    @ApiModelPropertyCheck("设备类型，1：烟感设备，2：气感设备")
    private Integer deviceType;
}
