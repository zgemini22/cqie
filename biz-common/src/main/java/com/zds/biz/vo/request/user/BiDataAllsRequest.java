package com.zds.biz.vo.request.user;

import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import lombok.Data;

@Data
public class BiDataAllsRequest {
    @ApiModelPropertyCheck(value="业务领域")
    private String o;
    @ApiModelPropertyCheck(value="时间条件")
    private String v;
    @ApiModelPropertyCheck(value="指标类型")
    private String d;
}
