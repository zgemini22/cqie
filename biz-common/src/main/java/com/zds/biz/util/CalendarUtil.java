package com.zds.biz.util;

import com.zds.biz.vo.response.user.CalendarResponse;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 日期维护工具类
 */
public class CalendarUtil {

    /**
     * 填充工作日历信息集合
     * @param startTime 开始日期
     * @param endTime 结束日期
     * @param dataList 数据库中对应时间段的数据，可空
     */
    public static List<CalendarResponse> padList(Date startTime, Date endTime, List<CalendarResponse> dataList) {
        Map<Date, CalendarResponse> map = new HashMap<>();
        if (dataList != null && dataList.size() > 0) {
            for (CalendarResponse calendarResponse : dataList) {
                map.put(calendarResponse.getCalendarDate(), calendarResponse);
            }
        }
        List<CalendarResponse> list = new ArrayList<>();
        Calendar start = cleanHMSM(startTime);
        Calendar end = cleanHMSM(endTime);
        while (start.compareTo(end) < 1) {
            Date time = start.getTime();

            list.add(map.getOrDefault(time,
                    CalendarResponse.builder()
                            .calendarDate(time)
                            .dayType(isWeekend(time) ? 2 : 1)
                            .build()));
            start.add(Calendar.DATE,1);
        }
        return list;
    }

    public static boolean isWeekend(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY;
    }

    /**
     * 年初
     * @param year yyyy
     */
    public static Date getStartTimeByYear(String year) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, Integer.parseInt(year));
        calendar.set(Calendar.MONTH, 0);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        firstHMSM(calendar);
        return calendar.getTime();
    }

    /**
     * 年底
     * @param year yyyy
     */
    public static Date getEndTimeByYear(String year) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, Integer.parseInt(year));
        calendar.set(Calendar.MONTH, 11);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        lastHMSM(calendar);
        return calendar.getTime();
    }

    /**
     * 月初
     * @param month yyyy-MM
     */
    public static Date getStartTimeByMonth(String month) {
        Calendar calendar = Calendar.getInstance();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
            calendar.setTime(sdf.parse(month));
        } catch (Exception e) {
            e.printStackTrace();
        }
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        firstHMSM(calendar);
        return calendar.getTime();
    }


    /**
     * 月初最后时间:23:59:59
     * @param month yyyy-MM
     */
    public static Date getStartTimeByMonthLast(String month) {
        Calendar calendar = Calendar.getInstance();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
            calendar.setTime(sdf.parse(month));
        } catch (Exception e) {
            e.printStackTrace();
        }
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        lastHMSM(calendar);
        return calendar.getTime();
    }


    /**
     * 月底
     * @param month yyyy-MM
     */
    public static Date getEndTimeByMonth(String month) {
        Calendar calendar = Calendar.getInstance();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
            calendar.setTime(sdf.parse(month));
        } catch (Exception e) {
            e.printStackTrace();
        }
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        lastHMSM(calendar);
        return calendar.getTime();
    }

    public static void firstHMSM(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
    }

    public static void lastHMSM(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
    }

    public static Calendar getFirstHMSM(Calendar calendar) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(calendar.getTime());
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal;
    }

    public static Calendar getLastHMSM(Calendar calendar) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(calendar.getTime());
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 999);
        return cal;
    }

    /**
     * 时分秒毫秒归零
     */
    public static Calendar cleanHMSM(Date time) {
        Calendar item = Calendar.getInstance();
        item.setTime(time);
        item.set(Calendar.HOUR_OF_DAY, 0);
        item.set(Calendar.MINUTE, 0);
        item.set(Calendar.SECOND, 0);
        item.set(Calendar.MILLISECOND, 0);
        return item;
    }

    public static String getYesrStr(Date time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        return calendar.get(Calendar.YEAR) + "";
    }

    public static String getMonthStr(Date time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        return calendar.get(Calendar.MONTH) + "";
    }

    public static int getDifferenceBySecond(Date start, Date end) {
        if (start == null || end == null) {
            return 0;
        }
        long between = (end.getTime() - start.getTime()) / 1000;
        return (int) between;
    }

    public static int getDifferenceByDay(Date start, Date end) {
        if (start == null || end == null) {
            return 0;
        }
        long between = (end.getTime() - start.getTime()) / (24*60*60*1000);
        return (int) between;
    }

    /**
     * 获取上期时间
     */
    public static Date getLastDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, -1);
        return calendar.getTime();
    }

}
