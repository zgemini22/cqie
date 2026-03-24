package com.zds.biz.vo.response.flow;

import io.swagger.annotations.ApiModel;
import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(description = "流程启动返回")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FlowStartResponse {
    @ApiModelPropertyCheck("流程定义id")
    private String processDefinitionId;

    @ApiModelPropertyCheck("流程实例id")
    private String processInstanceId;
}
