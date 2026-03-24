package com.zds.biz.vo.request.user;

import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import lombok.Data;

@Data
public class BiDataZjsRequest {
    @ApiModelPropertyCheck(value="指标ID")
    private String a;
    @ApiModelPropertyCheck(value="时间条件")
    private String e;
}
