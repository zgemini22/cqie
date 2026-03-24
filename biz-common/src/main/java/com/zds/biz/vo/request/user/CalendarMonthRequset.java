package com.zds.biz.vo.request.user;

import io.swagger.annotations.ApiModel;
import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import lombok.Data;

@Data
@ApiModel(value = "日历月份请求")
public class CalendarMonthRequset {

    @ApiModelPropertyCheck(value = "年月,yyyy-MM", example = "2021-05")
    private String month;
}
