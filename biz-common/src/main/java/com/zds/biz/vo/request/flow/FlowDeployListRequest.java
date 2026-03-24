package com.zds.biz.vo.request.flow;

import com.zds.biz.vo.PageRequest;
import io.swagger.annotations.ApiModel;
import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@ApiModel(description = "部署列表请求对象")
@NoArgsConstructor
@AllArgsConstructor
public class FlowDeployListRequest extends PageRequest {
    @ApiModelPropertyCheck("ID")
    private Long id;

    @ApiModelPropertyCheck(value = "流程名称")
    private String name;

    @ApiModelPropertyCheck(value = "流程key")
    private String key;

    @ApiModelPropertyCheck(value = "流程xml")
    private String xml;

    @ApiModelPropertyCheck("流程类别")
    private String type;

    @ApiModelPropertyCheck("流程状态（1-已发布,2-未发布）")
    private String status;
}
