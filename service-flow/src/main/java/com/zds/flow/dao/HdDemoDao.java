package com.zds.flow.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zds.flow.po.HdDemo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HdDemoDao extends BaseMapper<HdDemo> {

    int insertList(@Param("list") List<HdDemo> hdDemos);
}
