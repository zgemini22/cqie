package com.zds.user.service;

import com.zds.biz.vo.request.user.CalendarMonthRequset;
import com.zds.biz.vo.request.user.CalendarYearRequset;
import com.zds.biz.vo.request.user.WorkCalendarRequest;
import com.zds.biz.vo.response.user.CalendarResponse;
import com.zds.biz.vo.response.user.WorkCalendarResponse;

import java.util.List;

/**
 * 工作日历服务
 */
public interface CalendarService {

    /**
     * 按年查询日历
     */
    List<CalendarResponse> findByYear(CalendarYearRequset request);

    /**
     * 按月查询日历
     */
    List<CalendarResponse> findByMonth(CalendarMonthRequset request);

    /**
     * 保存日历信息
     */
    Boolean saveList(List<CalendarResponse> list);

    /**
     * 根据规定工作日计算业务完成状态
     */
    WorkCalendarResponse checkWork(WorkCalendarRequest request);
}
