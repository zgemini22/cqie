package com.zds.biz.vo.response.flow;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@ApiModel(description = "应急流程下拉返回")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FlowEmergencyListReponse {
    @ApiModelPropertyCheck("应急流程处置方案ID")
    private Long id;

    @ApiModelPropertyCheck("流程Key")
    private String key;

    @ApiModelPropertyCheck("流程名称")
    private String name;
}
