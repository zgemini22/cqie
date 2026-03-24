package com.zds.biz.util;

import com.zds.biz.constant.info.PatrolFrequencyEnum;
import com.zds.biz.vo.StartEndVo;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PatrolTimeUtil {

    public static List<StartEndVo> inspectionDates(Date startDate, Date endDate, String type) {
        List<StartEndVo> startEndVos = new ArrayList<>();
        switch (PatrolFrequencyEnum.query(type)) {
            case YEAR:
                startEndVos = yearlyDates(startDate, endDate);
                break;
            case QUARTER:
                startEndVos = quarterlyDates(startDate, endDate);
                break;
            case MONTH:
                startEndVos = monthlyDates(startDate, endDate);
                break;
            case WEEK:
                startEndVos = weekDates(startDate, endDate);
                break;
            case DAY:
                startEndVos = dayDates(startDate, endDate);
                break;

        }
        for (int i = 0; i < startEndVos.size(); i++) {
            if (i == startEndVos.size() - 1) {
                StartEndVo startEndVo = startEndVos.get(i);
                if (startEndVo.getEndDateTime().compareTo(endDate) > 0) {
                    startEndVo.setEndDateTime(endDate);
                }
            }
        }
        System.out.println(startEndVos);
        return startEndVos;
    }

    private static List<StartEndVo> yearlyDates(Date start, Date end) {
        LocalDateTime startDate = LocalDateTime.ofInstant(start.toInstant(), ZoneId.systemDefault());
        LocalDateTime endDate = LocalDateTime.ofInstant(end.toInstant(), ZoneId.systemDefault());

        LocalDateTime currentQuarterStart = startDate;
        List<StartEndVo> list = new ArrayList<>();
        while (currentQuarterStart.isBefore(endDate)) {
            LocalDateTime currentQuarterEnd = currentQuarterStart.plusYears(1).minusDays(1).plusHours(23).plusMinutes(59).plusSeconds(59);
            StartEndVo vo = new StartEndVo();
            Date one = Date.from(currentQuarterStart.atZone(ZoneId.systemDefault()).toInstant());
            Date two = Date.from(currentQuarterEnd.atZone(ZoneId.systemDefault()).toInstant());
            vo.setStartDateTime(one);
            vo.setEndDateTime(two);
            list.add(vo);
            currentQuarterStart = currentQuarterStart.plusYears(1);
        }
        return list;
    }


    private static List<StartEndVo> quarterlyDates(Date start, Date end) {
        LocalDateTime startDate = LocalDateTime.ofInstant(start.toInstant(), ZoneId.systemDefault());
        LocalDateTime endDate = LocalDateTime.ofInstant(end.toInstant(), ZoneId.systemDefault());

        LocalDateTime currentQuarterStart = startDate;
        List<StartEndVo> list = new ArrayList<>();
        while (currentQuarterStart.isBefore(endDate)) {
            LocalDateTime currentQuarterEnd = currentQuarterStart.plusMonths(3).minusDays(1).plusHours(23).plusMinutes(59).plusSeconds(59);
            StartEndVo vo = new StartEndVo();
            Date one = Date.from(currentQuarterStart.atZone(ZoneId.systemDefault()).toInstant());
            Date two = Date.from(currentQuarterEnd.atZone(ZoneId.systemDefault()).toInstant());
            vo.setStartDateTime(one);
            vo.setEndDateTime(two);
            list.add(vo);
            currentQuarterStart = currentQuarterStart.plusMonths(3);
        }
        return list;
    }

    private static List<StartEndVo> monthlyDates(Date start, Date end) {
        LocalDateTime startDate = LocalDateTime.ofInstant(start.toInstant(), ZoneId.systemDefault());
        LocalDateTime endDate = LocalDateTime.ofInstant(end.toInstant(), ZoneId.systemDefault());
        List<StartEndVo> list = new ArrayList<>();
        LocalDateTime currentMonthStart = startDate;
        while (!currentMonthStart.isAfter(endDate)) {
            LocalDateTime currentMonthEnd = currentMonthStart.plusMonths(1).plusDays(-1).plusHours(23).plusMinutes(59).plusSeconds(59);
            StartEndVo vo = new StartEndVo();
            Date one = Date.from(currentMonthStart.atZone(ZoneId.systemDefault()).toInstant());
            Date two = Date.from(currentMonthEnd.atZone(ZoneId.systemDefault()).toInstant());
            vo.setStartDateTime(one);
            vo.setEndDateTime(two);
            list.add(vo);
            currentMonthStart = currentMonthStart.plusMonths(1);
        }
        return list;
    }

    private static List<StartEndVo> weekDates(Date start, Date end) {
        LocalDateTime startDate = LocalDateTime.ofInstant(start.toInstant(), ZoneId.systemDefault());
        LocalDateTime endDate = LocalDateTime.ofInstant(end.toInstant(), ZoneId.systemDefault());
        List<StartEndVo> list = new ArrayList<>();
        LocalDateTime currentMonthStart = startDate;
        while (!currentMonthStart.isAfter(endDate)) {
            LocalDateTime currentMonthEnd = currentMonthStart.plusDays(7).plusDays(-1).plusHours(23).plusMinutes(59).plusSeconds(59);
            StartEndVo vo = new StartEndVo();
            Date one = Date.from(currentMonthStart.atZone(ZoneId.systemDefault()).toInstant());
            Date two = Date.from(currentMonthEnd.atZone(ZoneId.systemDefault()).toInstant());
            vo.setStartDateTime(one);
            vo.setEndDateTime(two);
            list.add(vo);
            currentMonthStart = currentMonthStart.plusDays(7);
        }
        return list;
    }

    private static List<StartEndVo> dayDates(Date start, Date end) {
        LocalDateTime startDate = LocalDateTime.ofInstant(start.toInstant(), ZoneId.systemDefault());
        LocalDateTime endDate = LocalDateTime.ofInstant(end.toInstant(), ZoneId.systemDefault());
        List<StartEndVo> list = new ArrayList<>();
        LocalDateTime currentMonthStart = startDate;
        while (!currentMonthStart.isAfter(endDate)) {
            LocalDateTime currentMonthEnd = currentMonthStart.plusHours(23).plusMinutes(59).plusSeconds(59);
            StartEndVo vo = new StartEndVo();
            Date one = Date.from(currentMonthStart.atZone(ZoneId.systemDefault()).toInstant());
            Date two = Date.from(currentMonthEnd.atZone(ZoneId.systemDefault()).toInstant());
            vo.setStartDateTime(one);
            vo.setEndDateTime(two);
            list.add(vo);
            currentMonthStart = currentMonthStart.plusDays(1);
        }
        return list;
    }

}



