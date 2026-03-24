package com.zds.biz.vo.request.flow;

import io.swagger.annotations.ApiModel;
import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "表单填写请求对象")
public class FlowStartFormRequest {

    @ApiModelPropertyCheck(value = "流程定义key")
    private String processDefinitionKey;

    @ApiModelPropertyCheck(value = "表单内容")
    private Map<String, Object> variables;
}
