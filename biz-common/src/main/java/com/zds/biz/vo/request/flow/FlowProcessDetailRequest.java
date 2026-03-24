package com.zds.biz.vo.request.flow;

import io.swagger.annotations.ApiModel;
import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "流程详情请求对象")
public class FlowProcessDetailRequest {

    @ApiModelPropertyCheck(value = "业务ID")
    private Long id;

    @ApiModelPropertyCheck(value = "流程枚举类型 (字典group_id=TODO_LIST)")
    private String type;
}
