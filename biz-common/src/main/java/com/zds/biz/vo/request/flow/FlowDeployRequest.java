package com.zds.biz.vo.request.flow;

import io.swagger.annotations.ApiModel;
import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

@Data
@ApiModel(description = "流程部署请求对象")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FlowDeployRequest {
    @ApiModelPropertyCheck("ID")
    private Long id;

    @ApiModelPropertyCheck(value = "流程文件xml")
    @NotNull(message = "xml不能为空")
    private String xml;

    @ApiModelPropertyCheck(value = "流程名称")
    private String name;

    @ApiModelPropertyCheck("流程key")
    private String key;

    @ApiModelPropertyCheck("流程类别")
    @NotNull(message = "流程类别不能为空")
    private String type;

    @ApiModelPropertyCheck("节点审核部门")
    @NotNull(message = "节点审核部门不能为空")
    private List<FlowNodeDepartmentRequest> nodeDepartment;
}
