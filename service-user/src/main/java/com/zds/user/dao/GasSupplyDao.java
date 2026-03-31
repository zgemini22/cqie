package com.zds.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zds.user.po.GasSupplyPo;
import com.zds.biz.vo.GasSupplyResponse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;

@Mapper
public interface GasSupplyDao extends BaseMapper<GasSupplyPo> {

    /**
     * 分页查询列表（带企业名称和地址）
     */
    List<GasSupplyResponse> selectPageList(Page<?> page,
                                           @Param("planStartTime") String planStartTime,
                                           @Param("planEndTime") String planEndTime,
                                           @Param("supplyStatus") String supplyStatus,
                                           @Param("supplyType") String supplyType);

    /**
     * 简单分页查询（不带关联表）
     */
    List<GasSupplyPo> selectSimplePageList(Page<?> page,
                                           @Param("planStartTime") String planStartTime,
                                           @Param("planEndTime") String planEndTime,
                                           @Param("supplyStatus") String supplyStatus,
                                           @Param("supplyType") String supplyType);

    /**
     * 根据ID查询详情
     */
    GasSupplyResponse selectDetailById(@Param("id") Integer id);
}