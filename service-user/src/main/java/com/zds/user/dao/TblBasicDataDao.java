package com.zds.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zds.user.po.TblBasicData;
import org.springframework.stereotype.Repository;

@Repository
public interface TblBasicDataDao extends BaseMapper<TblBasicData> {
    TblBasicData findByDataKey(String dataKey);
}
