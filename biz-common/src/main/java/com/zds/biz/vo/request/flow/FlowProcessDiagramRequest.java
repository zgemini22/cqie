package com.zds.biz.vo.request.flow;

import io.swagger.annotations.ApiModel;
import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "获取xml请求对象")
public class FlowProcessDiagramRequest {

    @ApiModelPropertyCheck(value = "流程定义key")
    private String processKey;

    @ApiModelPropertyCheck(value = "流程草稿Id")
    private Long processId;

}
