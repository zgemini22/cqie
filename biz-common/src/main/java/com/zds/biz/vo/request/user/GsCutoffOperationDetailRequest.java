package com.zds.biz.vo.request.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("停气作业详情请求参数")
public class GsCutoffOperationDetailRequest {
    @ApiModelProperty("作业ID")
    private Long id;

    @ApiModelProperty("详细地址")
    private String detailAddress;
}