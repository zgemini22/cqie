package com.zds.biz.vo.response.user;

import io.swagger.annotations.ApiModel;
import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(description = "省市区编码返回")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AreaResponse {

    @ApiModelPropertyCheck(value="省市区编码")
    private String code;

    @ApiModelPropertyCheck(value="省市区名称")
    private String areaName;

    @ApiModelPropertyCheck(value="父级编码")
    private String parentCode;
}
