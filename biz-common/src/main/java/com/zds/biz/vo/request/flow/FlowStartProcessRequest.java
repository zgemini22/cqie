package com.zds.biz.vo.request.flow;

import io.swagger.annotations.ApiModel;
import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@ApiModel(description = "流程开始请求对象")
@NoArgsConstructor
@AllArgsConstructor
public class FlowStartProcessRequest {
    @ApiModelPropertyCheck(value = "流程定义Key")
    private String processDefinitionKey;

    @ApiModelPropertyCheck(value = "业务Id")
    private Long projectId;

    @ApiModelPropertyCheck(value = "发起人Id")
    private String initiatorId;

    @ApiModelPropertyCheck(value = "项目类型")
    private String projectType;
}
