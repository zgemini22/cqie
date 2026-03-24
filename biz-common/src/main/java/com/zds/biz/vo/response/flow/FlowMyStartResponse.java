package com.zds.biz.vo.response.flow;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@ApiModel(description = "我发起的返回")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FlowMyStartResponse {
    @ApiModelPropertyCheck("流程实例id")
    private String processInstanceId;

    @ApiModelPropertyCheck("流程定义key")
    private String processDefinitionKey;

    @ApiModelPropertyCheck("流程定义id")
    private String processDefinitionId;

    @ApiModelPropertyCheck("流程定义名称")
    private String processDefinitionName;

    @ApiModelPropertyCheck("流程定义版本")
    private Integer processDefinitionVersion;

    @ApiModelPropertyCheck("开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date startTime;

    @ApiModelPropertyCheck("结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date endTime;

    @ApiModelPropertyCheck("移除时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date removalTime;

    @ApiModelPropertyCheck("持续时间")
    private Long durationInMillis;

}
