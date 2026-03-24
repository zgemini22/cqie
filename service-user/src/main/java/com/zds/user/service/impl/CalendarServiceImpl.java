package com.zds.user.service.impl;

import com.zds.biz.util.CalendarUtil;
import com.zds.biz.vo.request.user.CalendarMonthRequset;
import com.zds.biz.vo.request.user.CalendarYearRequset;
import com.zds.biz.vo.request.user.WorkCalendarRequest;
import com.zds.biz.vo.response.user.CalendarResponse;
import com.zds.biz.vo.response.user.WorkCalendarResponse;
import com.zds.user.dao.TblCalendarMaintanceDao;
import com.zds.user.po.TblCalendarMaintance;
import com.zds.user.service.CalendarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CalendarServiceImpl implements CalendarService {

    @Autowired
    private TblCalendarMaintanceDao calendarMaintanceDao;

    @Override
    public List<CalendarResponse> findByYear(CalendarYearRequset request) {
        return findByTime(CalendarUtil.getStartTimeByYear(request.getYear()), CalendarUtil.getEndTimeByYear(request.getYear()));
    }

    @Override
    public List<CalendarResponse> findByMonth(CalendarMonthRequset request) {
        return findByTime(CalendarUtil.getStartTimeByMonth(request.getMonth()), CalendarUtil.getEndTimeByMonth(request.getMonth()));
    }

    private List<CalendarResponse> findByTime(Date startTime, Date endTime) {
        List<CalendarResponse> list = calendarMaintanceDao.findByTime(startTime, endTime);
        return CalendarUtil.padList(startTime, endTime, list);
    }

    @Override
    public Boolean saveList(List<CalendarResponse> list) {
        //删除日期已存在的记录
        calendarMaintanceDao.delete(TblCalendarMaintance.getWrapper()
                .in(TblCalendarMaintance::getCalendarDate, list.stream().map(CalendarResponse::getCalendarDate).collect(Collectors.toList())));
        //新增
        int count = calendarMaintanceDao.insertList(list.stream()
                .map(x -> TblCalendarMaintance.builder()
                        .calendarYear(CalendarUtil.getYesrStr(x.getCalendarDate()))
                        .calendarDate(x.getCalendarDate())
                        .dayType(x.getDayType())
                        .build())
                .collect(Collectors.toList()));
        return count > 0;
    }

    @Override
    public WorkCalendarResponse checkWork(WorkCalendarRequest request) {
        List<CalendarResponse> list = findByTime(request.getStartTime(), request.getEndTime());
        //实际工作日天数
        int day = Math.toIntExact(list.stream().filter(x -> x.getDayType() == 1).count());
        WorkCalendarResponse data = WorkCalendarResponse.builder()
                .workStatus(1)
                .workTime(day)
                .build();
        if (day > request.getWorkDay()) {
            data.setWorkStatus(3);
        } else if (day < request.getWorkDay()) {
            data.setWorkStatus(2);
        }
        return data;
    }
}
