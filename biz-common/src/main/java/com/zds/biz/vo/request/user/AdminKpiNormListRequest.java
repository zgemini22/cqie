package com.zds.biz.vo.request.user;

import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(description = "指标类别列表请求")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminKpiNormListRequest {

    @ApiModelPropertyCheck(value = "类别名称")
    private String classifyName;
}
