package com.zds.biz.vo.response.flow;

import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@ApiModel(description = "获取xml流程图返回")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FlowXmlDiagramResponse {
    @ApiModelPropertyCheck("id")
    private String processId;

    @ApiModelPropertyCheck("流程图xml")
    private String processXml;

    @ApiModelPropertyCheck("流程图名称")
    private String processName;

    @ApiModelPropertyCheck("流程图类型")
    private String processType;

    @ApiModelPropertyCheck("节点审核部门")
    private List<FlowNodeDepartmentResponse> nodeDepartment;
}
