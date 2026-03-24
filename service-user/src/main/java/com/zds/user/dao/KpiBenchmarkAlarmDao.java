package com.zds.user.dao;

import com.zds.user.po.KpiBenchmarkAlarm;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KpiBenchmarkAlarmDao extends BaseMapper<KpiBenchmarkAlarm> {
    /**
     * 批量新增
     * */
    boolean insertList(List<KpiBenchmarkAlarm> list);
    /**
     * 批量修改
     * */
    boolean updateList(List<KpiBenchmarkAlarm> list);
}
