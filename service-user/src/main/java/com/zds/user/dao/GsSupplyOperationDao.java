// GsSupplyOperationDao.java
package com.zds.user.dao;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zds.user.po.GsSupplyOperationPo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

// GsSupplyOperationDao.java
@Mapper
public interface GsSupplyOperationDao extends BaseMapper<GsSupplyOperationPo> {

    /**
     * 分页查询供气作业列表（通过外键 org_id 关联组织表）
     */
    @Select("SELECT so.*, " +
            "CASE " +
            "   WHEN so.plan_id > 50 THEN 'PLANNED' " +
            "   ELSE 'UNPLANNED' " +
            "END as supply_type, " +
            "CONCAT(IFNULL(san.district_code, ''), " +
            "       IF(san.street_code IS NOT NULL AND san.street_code != '', CONCAT(' ', san.street_code), ''), " +
            "       IF(san.detail_address IS NOT NULL AND san.detail_address != '', CONCAT(' ', san.detail_address), '')) as supply_area, " +
            "org.organization_name as enterprise_name " +
            "FROM gs_supply_operation so " +
            "LEFT JOIN gs_supply_area_node san ON so.org_id = san.org_id AND san.deleted = 0 " +
            "LEFT JOIN tbl_organization org ON so.org_id = org.id AND org.deleted = 0 " +
            "${ew.customSqlSegment}")
    IPage<GsSupplyOperationPo> selectPageWithRelation(Page<GsSupplyOperationPo> page,
                                                      @Param(Constants.WRAPPER) Wrapper<GsSupplyOperationPo> queryWrapper);
}