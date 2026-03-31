package com.zds.biz.vo.request.user;

import com.zds.biz.vo.PageRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@ApiModel(description = "停气计划请求")
@Data
public class GsCutoffPlanRequest extends PageRequest {

    @ApiModelProperty(value = "停气时间")
    private Date startTime;

    @ApiModelProperty(value = "恢复时间")
    private Date endTime;

    @ApiModelProperty(value = "作业等级")
    private String operationLevel;

}
