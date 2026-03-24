package com.zds.biz.util;

import com.zds.biz.constant.TimeGroupEnum;
import com.zds.biz.vo.PeriodNodeData;
import com.zds.biz.vo.TimeChartRequest;
import com.zds.biz.vo.TimeNodeData;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.zds.biz.constant.TimeGroupEnum.*;

/**
 * 时间范围图表格式数据处理工具类
 */
public class TimeChartUtil {

    public static void main(String[] args) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date startTime = sdf.parse("2024-01-01 00:00:00");
            Date endTime = sdf.parse("2024-01-31 23:59:59");
            TimeGroupEnum timeGroup = WEEK;
            //ChartBaseVo.xAxis.data
            List<String> list = getAxisData(startTime, endTime, timeGroup, "MM-dd");
            for (String str : list) {
                System.out.println(str);
            }
            List<TimeNodeData> dataList = new ArrayList<>();
            dataList.add(TimeNodeData.builder().time(sdf.parse("2024-01-07 00:00:00")).num(new BigDecimal("7")).build());
            dataList.add(TimeNodeData.builder().time(sdf.parse("2024-01-08 00:00:00")).num(new BigDecimal("8")).build());
            //ChartBaseVo.series.data
            List<BigDecimal> dataByGroup = getDataByGroup(dataList, timeGroup, startTime, endTime);
            dataByGroup.forEach(System.out::println);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 判断是否是月 季度 年
     */
    public static Integer getDateSection(Date startTime, Date endTime) {
        Calendar itemTime = Calendar.getInstance();
        itemTime.setTime(startTime);
        Calendar end = Calendar.getInstance();
        end.setTime(endTime);
        if (itemTime.get(Calendar.YEAR) == end.get(Calendar.YEAR)) {
            if (itemTime.get(Calendar.MONTH) == end.get(Calendar.MONTH)) {
                //月
                return 1;
            }
        }
        if (itemTime.get(Calendar.YEAR) == end.get(Calendar.YEAR)) {
            if (itemTime.get(Calendar.MONTH) / 3 == end.get(Calendar.MONTH) / 3) {
                //季度
                return 2;
            }
        }
        if (itemTime.get(Calendar.YEAR) == end.get(Calendar.YEAR)) {
            if (itemTime.get(Calendar.MONTH) == 0 && end.get(Calendar.MONTH) == 11) {
                //年
                return 3;
            }
        }
        return 0;
    }

    /**
     * 时间范围按分组类型组装集合
     *
     * @param timeChart 时间范围图表格式请求
     */
    public static List<String> getAxisData(TimeChartRequest timeChart) {
        return getAxisData(timeChart.getStartTime(), timeChart.getEndTime(), TimeGroupEnum.queryByKey(timeChart.getGroup()), null);
    }

    /**
     * 时间范围按分组类型组装集合
     *
     * @param timeChart      时间范围图表格式请求
     * @param axisDateFormat 轴时间数据输出格式
     */
    public static List<String> getAxisData(TimeChartRequest timeChart, String axisDateFormat) {
        return getAxisData(timeChart.getStartTime(), timeChart.getEndTime(), TimeGroupEnum.queryByKey(timeChart.getGroup()), axisDateFormat);
    }

    /**
     * 时间范围按分组类型组装key集合
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @param timeGroup 时间分组类型
     */
    public static List<String> getAxisData(Date startTime, Date endTime, TimeGroupEnum timeGroup) {
        return getAxisData(startTime, endTime, timeGroup, null);
    }

    /**
     * 时间范围按分组类型组装key集合
     *
     * @param startTime      开始时间
     * @param endTime        结束时间
     * @param timeGroup      时间分组类型
     * @param axisDateFormat 时间分组类型
     */
    public static List<String> getAxisData(Date startTime, Date endTime, TimeGroupEnum timeGroup, String axisDateFormat) {
        List<String> list = new ArrayList<>();
        //开始时间
        Calendar calBegin = Calendar.getInstance();
        calBegin.setTime(startTime);
        //结束时间
        Calendar calEnd = Calendar.getInstance();
        calEnd.setTime(endTime);
        while (calEnd.after(calBegin)) {
            switch (timeGroup) {
                case DAY:
                    list.add(axisDateFormat != null ? getDayStr(calBegin, axisDateFormat) : getDayStr(calBegin));
                    calBegin.add(Calendar.DAY_OF_MONTH, 1);
                    break;
                case MONTH:
                    list.add(axisDateFormat != null ? getMonthStr(calBegin, axisDateFormat) : getMonthStr(calBegin));
                    calBegin.add(Calendar.MONTH, 1);
                    break;
                case HOUR:
                    list.add(axisDateFormat != null ? getHourStr(calBegin, axisDateFormat) : getHourStr(calBegin));
                    calBegin.add(Calendar.HOUR_OF_DAY, 1);
                    break;
                case YEAR:
                    list.add(axisDateFormat != null ? getYearStr(calBegin, axisDateFormat) : getYearStr(calBegin));
                    calBegin.add(Calendar.YEAR, 1);
                    break;
                case WEEK:
                    list.add(axisDateFormat != null ? getWeekStr(calBegin, axisDateFormat) : getWeekStr(calBegin));
                    calBegin.add(Calendar.WEEK_OF_MONTH, 1);
                    break;
            }
        }
        return list;
    }

    /**
     * 生成某段时间的每天日期集合
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     */
    public static List<Date> groupDayByTime(Date startTime, Date endTime) {
        List<Date> list = new ArrayList<>();
        //开始时间
        Calendar calBegin = Calendar.getInstance();
        calBegin.setTime(startTime);
        //结束时间
        Calendar calEnd = Calendar.getInstance();
        calEnd.setTime(endTime);
        while (calEnd.after(calBegin)) {
            list.add(calBegin.getTime());
            calBegin.add(Calendar.DAY_OF_MONTH, 1);
        }
        return list;
    }

    private static String getDayStr(Calendar calendar) {
        return String.valueOf(calendar.get(Calendar.DATE));
    }

    private static String getMonthStr(Calendar calendar) {
        return String.valueOf(calendar.get(Calendar.MONTH) + 1);
    }

    private static String getHourStr(Calendar calendar) {
        return String.valueOf(calendar.get(Calendar.HOUR_OF_DAY) + 1);
    }

    private static String getYearStr(Calendar calendar) {
        return String.valueOf(calendar.get(Calendar.YEAR));
    }

    private static String getWeekStr(Calendar calendar) {
        return String.valueOf(calendar.get(Calendar.WEEK_OF_MONTH) + 1);
    }

    private static String getDayStr(Calendar calendar, String axisDateFormat) {
        String str;
        try {
            str = calendarToString(calendar, axisDateFormat);
        } catch (Exception e) {
            e.printStackTrace();
            str = getDayStr(calendar);
        }
        return str;
    }

    private static String getMonthStr(Calendar calendar, String axisDateFormat) {
        String str;
        try {
            str = calendarToString(calendar, axisDateFormat);
        } catch (Exception e) {
            e.printStackTrace();
            str = getMonthStr(calendar);
        }
        return str;
    }

    private static String getHourStr(Calendar calendar, String axisDateFormat) {
        String str;
        try {
            str = calendarToString(calendar, axisDateFormat);
        } catch (Exception e) {
            e.printStackTrace();
            str = getHourStr(calendar);
        }
        return str;
    }

    private static String getYearStr(Calendar calendar, String axisDateFormat) {
        String str;
        try {
            str = calendarToString(calendar, axisDateFormat);
        } catch (Exception e) {
            e.printStackTrace();
            str = getYearStr(calendar);
        }
        return str;
    }

    private static String getWeekStr(Calendar calendar, String axisDateFormat) {
        String str;
        try {
            str = calendarToString(calendar, axisDateFormat);
        } catch (Exception e) {
            e.printStackTrace();
            str = getWeekStr(calendar);
        }
        return str;
    }

    private static String calendarToString(Calendar calendar, String axisDateFormat) {
        SimpleDateFormat sdf = new SimpleDateFormat(axisDateFormat);
        return sdf.format(calendar.getTime());
    }

    /**
     * 时间范围按分组类型组装key集合
     *
     * @param timeChart 时间范围图表格式请求
     */
    public static Map<String, BigDecimal> getAxisDataToMap(TimeChartRequest timeChart) {
        return getAxisDataToMap(timeChart.getStartTime(), timeChart.getEndTime(), TimeGroupEnum.queryByKey(timeChart.getGroup()));
    }

    /**
     * 时间范围按分组类型组装key集合
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @param timeGroup 时间分组类型
     */
    public static Map<String, BigDecimal> getAxisDataToMap(Date startTime, Date endTime, TimeGroupEnum timeGroup) {
        SimpleDateFormat sdf = new SimpleDateFormat(getDateFormat(timeGroup));
        Map<String, BigDecimal> map = new LinkedHashMap<>();
        //开始时间
        Calendar calBegin = Calendar.getInstance();
        calBegin.setTime(startTime);
        //结束时间
        Calendar calEnd = Calendar.getInstance();
        calEnd.setTime(endTime);
        while (calEnd.after(calBegin)) {
            switch (timeGroup) {
                case DAY:
                    map.put(sdf.format(calBegin.getTime()), BigDecimal.ZERO);
                    calBegin.add(Calendar.DAY_OF_MONTH, 1);
                    break;
                case MONTH:
                    map.put(sdf.format(calBegin.getTime()), BigDecimal.ZERO);
                    calBegin.add(Calendar.MONTH, 1);
                    break;
                case HOUR:
                    map.put(sdf.format(calBegin.getTime()), BigDecimal.ZERO);
                    calBegin.add(Calendar.HOUR_OF_DAY, 1);
                    break;
                case YEAR:
                    map.put(sdf.format(calBegin.getTime()), BigDecimal.ZERO);
                    calBegin.add(Calendar.YEAR, 1);
                    break;
                case WEEK:
                    map.put(getWeekGroupStr(calBegin), BigDecimal.ZERO);
                    calBegin.add(Calendar.WEEK_OF_MONTH, 1);
                    break;
            }
        }
        return map;
    }

    private static String getWeekGroupStr(Calendar calendar) {
        //设置每周第一天为周一 默认每周第一天为周日
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        //获取当前日期所在周周日
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        return String.valueOf(calendar.get(Calendar.YEAR)).concat("年").
                concat(String.valueOf(calendar.get(Calendar.MONTH) + 1)).concat("月第").
                concat(String.valueOf(calendar.get(Calendar.WEEK_OF_MONTH))).concat("周");
    }

    /**
     * 获取时间格式
     *
     * @param timeGroup 时间分组类型
     */
    private static String getDateFormat(TimeGroupEnum timeGroup) {
        String format = "yyyy-MM-dd HH:mm:ss";
        switch (timeGroup) {
            case DAY:
                format = "yyyy-MM-dd";
                break;
            case MONTH:
                format = "yyyy-MM";
                break;
            case HOUR:
                format = "HH";
                break;
            case YEAR:
                format = "yyyy";
                break;
        }
        return format;
    }

    /**
     * 按时间分组计算数值
     *
     * @param list      时间和数值集合
     * @param timeGroup 时间分组类型
     * @param startTime 开始时间
     * @param endTime   结束时间
     */
    public static List<BigDecimal> getDataByGroup(List<TimeNodeData> list, TimeGroupEnum timeGroup, Date startTime, Date endTime) {
        Map<String, BigDecimal> map = getAxisDataToMap(startTime, endTime, timeGroup);
        return getDataByGroup(list, timeGroup, map);
    }

    /**
     * 按时间分组计算数值
     *
     * @param list      时间和数值集合
     * @param timeGroup 时间分组类型
     * @param map       时间分组节点map
     */
    public static List<BigDecimal> getDataByGroup(List<TimeNodeData> list, TimeGroupEnum timeGroup, Map<String, BigDecimal> map) {
        SimpleDateFormat sdf = new SimpleDateFormat(getDateFormat(timeGroup));
        for (TimeNodeData nodeData : list) {
            String key = sdf.format(nodeData.getTime());
            if (timeGroup.equals(WEEK)) {//周特殊处理
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(nodeData.getTime());
                key = getWeekGroupStr(calendar);
            }
            if (map.containsKey(key)) {
                map.put(key, nodeData.getNum().add(map.get(key)));
            }
        }
        return new ArrayList<>(map.values());
    }

    /**
     * 按时间分组计算数值
     *
     * @param list      时间和数值集合
     * @param timeChart 时间范围图表格式请求
     */
    public static List<BigDecimal> getDataByGroup(List<TimeNodeData> list, TimeChartRequest timeChart) {
        TimeGroupEnum timeGroup = TimeGroupEnum.queryByKey(timeChart.getGroup());
        Map<String, BigDecimal> map = getAxisDataToMap(timeChart.getStartTime(), timeChart.getEndTime(), timeGroup);
        return getDataByGroup(list, timeGroup, map);
    }

    /**
     * 获取环比时间
     */
    public static Date getChainComparison(Date time, Integer group) {
        TimeGroupEnum timeGroupEnum = TimeGroupEnum.queryByKey(group);
        Calendar cal = Calendar.getInstance();
        cal.setTime(time);
        if (timeGroupEnum == DAY) {
            cal.add(Calendar.DAY_OF_MONTH, -1);//前一天
        } else if (timeGroupEnum == MONTH) {
            cal.add(Calendar.MONTH, -1);//前一月
        }
        return cal.getTime();
    }

    /**
     * 获取同比时间
     */
    public static Date getSameComparison(Date time, Integer group) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(time);
        cal.add(Calendar.YEAR, -1);//前一年
        return cal.getTime();
    }

    /**
     * 上月
     */
    public static Date getLastMonth(Date time) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(time);
        cal.add(Calendar.MONTH, -1);
        return cal.getTime();
    }

    /**
     * 上季度
     */
    public static Date getLastQuarter(Date time) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(time);
        cal.add(Calendar.MONTH, -3);
        return cal.getTime();
    }

    /**
     * 上年
     */
    public static Date getLastYear(Date time) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(time);
        cal.add(Calendar.YEAR, -1);
        return cal.getTime();
    }

    /**
     * 根据时间切割为各月份
     */
    public static Map<String, List<Date>> groupByDate(Date startTime, Date endTime) {
        Map<String, List<Date>> map = new HashMap<>();
        Calendar itemTime = Calendar.getInstance();
        itemTime.setTime(startTime);
        Calendar end = Calendar.getInstance();
        end.setTime(endTime);
        SimpleDateFormat sdf_f = new SimpleDateFormat("yyyy-MM-dd");
        while (itemTime.compareTo(end) < 1) {
            List<Date> list = new ArrayList<>();
            list.add(itemTime.getTime());//开始

            itemTime.add(Calendar.MONTH, 1);
            itemTime.add(Calendar.DAY_OF_MONTH, -1);
            list.add(itemTime.getTime());//结束

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
            map.put(sdf.format(itemTime.getTime()), list);

            itemTime.add(Calendar.DAY_OF_MONTH, 1);
        }
        return map;
    }

    /**
     * 根据时间切割为天数
     */
    public static Map<String, List<Date>> groupByDay(Date startTime, Date endTime) {
        Map<String, List<Date>> map = new HashMap<>();
        Calendar itemTime = Calendar.getInstance();
        itemTime.setTime(startTime);
        Calendar end = Calendar.getInstance();
        end.setTime(endTime);
        SimpleDateFormat sdf_f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        while (itemTime.compareTo(end) < 1) {
            List<Date> list = new ArrayList<>();
            list.add(itemTime.getTime());//开始

            itemTime.add(Calendar.DAY_OF_MONTH, 1);
            itemTime.add(Calendar.SECOND, -1);
            list.add(itemTime.getTime());//结束

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            map.put(sdf.format(itemTime.getTime()), list);

            itemTime.add(Calendar.SECOND, 1);
        }
        return map;
    }

    /**
     * 根据时间分组，计算各时间节点的数据存量
     */
    public static List<TimeNodeData> getStockGroupByTime(TimeChartRequest requset, List<PeriodNodeData> list) {
        List<TimeNodeData> dataList = new ArrayList<>();
        Date startTime = requset.getStartTime();
        Date endTime = requset.getEndTime();
        Map<String, List<Date>> map = new HashMap<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        TimeGroupEnum groupEnum = TimeGroupEnum.queryByKey(requset.getGroup());
        switch (groupEnum) {
            case DAY:
                map = TimeChartUtil.groupByDay(startTime, endTime);
                break;
            case MONTH:
                map = TimeChartUtil.groupByDate(startTime, endTime);
                sdf = new SimpleDateFormat("yyyy-MM");
                break;
        }

        Map<String, BigDecimal> dataMap = new HashMap<>();
        for (String key : map.keySet()) {
            List<Date> dates = map.get(key);
            Date time_start = dates.get(0);
            Date time_end = dates.get(1);
            //遍历查询
            for (PeriodNodeData timeAndNumResponse : list) {
                //过滤开始结束时间
                if (timeAndNumResponse.getStartTime().compareTo(time_end) < 1 && timeAndNumResponse.getEndTime().compareTo(time_start) > -1) {
                    dataMap.put(key, dataMap.getOrDefault(key, BigDecimal.ZERO).add(timeAndNumResponse.getNum()));
                }
            }
        }

        try {
            for (String key : dataMap.keySet()) {
                dataList.add(TimeNodeData.builder()
                        .time(sdf.parse(key))
                        .num(dataMap.get(key))
                        .build());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataList;
    }

    public static Date getLastMonthFirst() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -1);
        return getMonthFirst(cal);
    }

    public static Date getLastMonthLast() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -1);
        return getMonthLast(cal);
    }

    public static Date getMonthFirst(Calendar cal) {
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    public static Date getMonthLast(Calendar cal) {
        cal.add(Calendar.MONTH, 1);
        cal.set(Calendar.DAY_OF_MONTH, 0);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 999);
        return cal.getTime();
    }
}
