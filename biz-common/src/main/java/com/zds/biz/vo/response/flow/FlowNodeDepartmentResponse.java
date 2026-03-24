package com.zds.biz.vo.response.flow;

import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@ApiModel(description = "节点部门")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FlowNodeDepartmentResponse {
    @ApiModelPropertyCheck("id")
    private Long id;

    @ApiModelPropertyCheck("节点id")
    private String nodeId;

    @ApiModelPropertyCheck("部门Id")
    private String departmentId;

    @ApiModelPropertyCheck("节点周期")
    private Integer dueDay;

}
