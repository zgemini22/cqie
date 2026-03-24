package com.zds.user.controller.admin;

import com.zds.biz.targetcheck.Authorization;
import com.zds.biz.vo.BaseResult;
import com.zds.biz.vo.request.user.CalendarMonthRequset;
import com.zds.biz.vo.request.user.CalendarYearRequset;
import com.zds.biz.vo.request.user.WorkCalendarRequest;
import com.zds.biz.vo.response.user.CalendarResponse;
import com.zds.biz.vo.response.user.WorkCalendarResponse;
import com.zds.user.service.CalendarService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "后台工作日历模块")
@RestController
@RequestMapping(value = "/admin/calendar")
public class AdminCalendarController {

    @Autowired
    private CalendarService calendarService;

    @Authorization
    @ApiOperation("按年查询日历")
    @RequestMapping(value = "/year", method = RequestMethod.POST)
    public BaseResult<List<CalendarResponse>> findByYear(@RequestBody CalendarYearRequset request){
        return BaseResult.success(calendarService.findByYear(request));
    }

    @Authorization
    @ApiOperation("按月查询日历")
    @RequestMapping(value = "/month", method = RequestMethod.POST)
    public BaseResult<List<CalendarResponse>> findByMonth(@RequestBody CalendarMonthRequset request){
        return BaseResult.success(calendarService.findByMonth(request));
    }

    @Authorization
    @ApiOperation("保存日历信息")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public BaseResult<String> saveList(@RequestBody List<CalendarResponse> list){
        return BaseResult.judgeOperate(calendarService.saveList(list));
    }

    @ApiOperation("根据规定工作日计算业务完成状态")
    @RequestMapping(value = "/check/work", method = RequestMethod.POST)
    public BaseResult<WorkCalendarResponse> checkWork(@RequestBody WorkCalendarRequest request){
        return BaseResult.success(calendarService.checkWork(request));
    }
}
