package com.zds.biz.vo.response.flow;

import io.swagger.annotations.ApiModel;
import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(description = "后台流程Key返回")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FlowProcessKeyResponse {

    @ApiModelPropertyCheck("流程名称")
    private String name;

    @ApiModelPropertyCheck("流程选择KEY")
    private String processSelectKey;

    @ApiModelPropertyCheck("流程启动KEY")
    private String processInstanceKey;
}
