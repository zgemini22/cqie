package com.zds.biz.vo.request.user;

import io.swagger.annotations.ApiModel;
import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import lombok.Data;

@Data
@ApiModel(value = "日历年份请求")
public class CalendarYearRequset {

    @ApiModelPropertyCheck(value = "年份,yyyy", example = "2021")
    private String year;
}
