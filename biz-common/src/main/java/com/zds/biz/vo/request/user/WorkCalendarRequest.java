package com.zds.biz.vo.request.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@ApiModel(value = "计算事务时间状态请求")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkCalendarRequest {

    @ApiModelPropertyCheck(value = "开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date startTime;

    @ApiModelPropertyCheck(value = "结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date endTime;

    @ApiModelPropertyCheck(value = "规定工作日天数")
    private Integer workDay;
}
