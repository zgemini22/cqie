package com.zds.user.dao;

import com.zds.user.po.KpiDataDetail;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KpiDataDetailDao extends BaseMapper<KpiDataDetail> {

    void insertList(List<KpiDataDetail> addList);
}
