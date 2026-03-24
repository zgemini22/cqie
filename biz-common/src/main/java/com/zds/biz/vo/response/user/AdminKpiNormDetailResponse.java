package com.zds.biz.vo.response.user;

import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(description = "指标类别详情请求")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminKpiNormDetailResponse {

    @ApiModelProperty(value = "ID")
    private Long id;

    @ApiModelPropertyCheck(value = "父级ID")
    private Long parentId;

    @ApiModelPropertyCheck(value = "类别名称")
    private String classifyName;

    @ApiModelPropertyCheck(value = "层级")
    private Integer levelNum;
}
