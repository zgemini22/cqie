package com.zds.biz.vo.request.flow;

import io.swagger.annotations.ApiModel;
import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "查询流程当前审核部门请求对象")
public class FlowProcessUnitRequest {
    @ApiModelPropertyCheck(value = "流程类型枚举")
    private String type;

    @ApiModelPropertyCheck(value = "业务id")
    private Long id;
}
