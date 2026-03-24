package com.zds.biz.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zds.biz.constant.TimeGroupEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@ApiModel(value = "时间范围图表格式请求")
public class TimeChartRequest {

    @ApiModelProperty(value = "开始时间,yyyy-MM-dd HH:mm:ss", required = true, example = "2021-05-01 00:00:00")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date startTime;

    @ApiModelProperty(value = "结束时间,yyyy-MM-dd HH:mm:ss", required = true, example = "2021-05-31 23:59:59")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date endTime;

    @ApiModelProperty(value = "时间分组类型,1:日,2:月,3:小时,4:年,5:周:,默认1", example = "1")
    private Integer group = TimeGroupEnum.DAY.getKey();
}
