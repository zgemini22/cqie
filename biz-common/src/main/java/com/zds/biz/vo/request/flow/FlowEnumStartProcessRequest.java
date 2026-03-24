package com.zds.biz.vo.request.flow;

import io.swagger.annotations.ApiModel;
import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@ApiModel(description = "枚举启动流程开始请求对象")
@NoArgsConstructor
@AllArgsConstructor
public class FlowEnumStartProcessRequest {
    @ApiModelPropertyCheck(value = "流程枚举")
    private String flowEnum;

    @ApiModelPropertyCheck(value = "业务Id")
    private Long projectid;

    @ApiModelPropertyCheck("发起人Id")
    private String initiatorId;
}
