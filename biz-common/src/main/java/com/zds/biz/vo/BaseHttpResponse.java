package com.zds.biz.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@ApiModel(description = "Http返回结果基类")
@Data
public class BaseHttpResponse<T> {
    @ApiModelProperty(value="code编码")
    private String code;
    @ApiModelProperty(value="信息")
    private String message;
    @ApiModelProperty(value="描述")
    private String description;
    @ApiModelProperty(value="数据")
    private T data;
}
