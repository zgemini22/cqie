package com.zds.biz.vo.response.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@ApiModel(value = "日历信息返回")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CalendarResponse {

    @ApiModelPropertyCheck(value = "日期,yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date calendarDate;

    @ApiModelPropertyCheck("日期类型,1:工作日,2:假日")
    private Integer dayType;
}
