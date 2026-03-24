package com.zds.biz.vo.request.flow;

import io.swagger.annotations.ApiModel;
import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "节点加签请求对象")
public class FlowAddSignRequest {
    @ApiModelPropertyCheck(value = "流程实例Id")
    private String processInstanceId;

    @ApiModelPropertyCheck(value = "节点Id")
    private String nodeId;

    @ApiModelPropertyCheck(value = "用户Id")
    private List<String> userId;
}
