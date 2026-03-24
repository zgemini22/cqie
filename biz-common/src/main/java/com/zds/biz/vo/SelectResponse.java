package com.zds.biz.vo;

import io.swagger.annotations.ApiModel;
import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(description = "下拉列表通用返回")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SelectResponse {

    @ApiModelPropertyCheck(value="ID")
    private Long id;

    @ApiModelPropertyCheck(value="名称")
    private String name;

    @ApiModelPropertyCheck(value="状态,1:启用,0:禁用")
    private Integer status = 1;
}
