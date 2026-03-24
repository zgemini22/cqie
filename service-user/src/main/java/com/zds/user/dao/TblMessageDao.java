package com.zds.user.dao;

import com.zds.user.po.TblMessage;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TblMessageDao extends BaseMapper<TblMessage> {

    int insertList(@Param("list") List<TblMessage> areaLines);
}
