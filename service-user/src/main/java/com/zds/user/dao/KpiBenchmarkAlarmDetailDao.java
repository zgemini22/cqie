package com.zds.user.dao;

import com.zds.user.po.KpiBenchmarkAlarmDetail;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KpiBenchmarkAlarmDetailDao extends BaseMapper<KpiBenchmarkAlarmDetail> {

    /**
     * 批量新增
     * */
    boolean insertList(List<KpiBenchmarkAlarmDetail> list);
}
