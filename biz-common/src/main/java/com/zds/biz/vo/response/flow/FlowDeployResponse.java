package com.zds.biz.vo.response.flow;

import io.swagger.annotations.ApiModel;
import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(description = "流程部署返回")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FlowDeployResponse {
    @ApiModelPropertyCheck("流程定义id")
    private String processDefinitionId;

    @ApiModelPropertyCheck("流程定义key")
    private String processDefinitionKey;
}
