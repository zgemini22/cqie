package com.zds.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zds.biz.vo.response.user.CalendarResponse;
import com.zds.user.po.TblCalendarMaintance;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface TblCalendarMaintanceDao extends BaseMapper<TblCalendarMaintance> {

    int insertList(List<TblCalendarMaintance> collect);

    List<CalendarResponse> findByTime(@Param("startTime") Date startTime, @Param("endTime")  Date endTime);
}
