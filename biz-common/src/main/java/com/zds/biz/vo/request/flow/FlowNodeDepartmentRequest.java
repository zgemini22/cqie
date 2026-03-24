package com.zds.biz.vo.request.flow;

import io.swagger.annotations.ApiModel;
import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder
@ApiModel(description = "节点部门")
@NoArgsConstructor
@AllArgsConstructor
public class FlowNodeDepartmentRequest {
    @ApiModelPropertyCheck("id")
    private Long id;

    @ApiModelPropertyCheck("节点id")
    private String nodeId;

    @ApiModelPropertyCheck("流程id")
    private Long processId;

    @ApiModelPropertyCheck("部门Id")
    @NotNull(message = "部门Id不能为空")
    private String departmentId;

    @ApiModelPropertyCheck("节点周期")
    @NotNull(message = "节点周期不能为空")
    private Integer dueDay;

}
