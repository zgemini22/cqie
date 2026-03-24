package com.zds.biz.vo.request.construction;

import io.swagger.annotations.ApiModelProperty;

// ### 2. 请求参数类 (Request)
public class TpcConstructionAddRequest {
    @ApiModelProperty(value = "三方施工编号",required = true)
    private String constructionCode;
}
