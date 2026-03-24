package com.zds.biz.vo.request.flow;

import io.swagger.annotations.ApiModel;
import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@ApiModel(description = "部署保存请求对象")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FlowDeployAddRequest {

    @ApiModelPropertyCheck("ID")
    private Long id;

    @ApiModelPropertyCheck("流程名称")
    private String name;

    @ApiModelPropertyCheck("流程key")
    private String key;

    @ApiModelPropertyCheck("流程xml")
    private String xml;

    @ApiModelPropertyCheck("流程类别")
    private String type;

    @ApiModelPropertyCheck("节点审核部门")
    private List<FlowNodeDepartmentRequest> nodeDepartment;
}
