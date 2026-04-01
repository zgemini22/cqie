package com.zds.user.dao.risk;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zds.user.po.risk.RiskPointDetail;
import com.zds.user.po.risk.RpRiskPoint;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RiskPointMapper extends BaseMapper<RpRiskPoint> {

    IPage<RiskPointDetail> selectRiskPointDetailPage(Page<RiskPointDetail> page,
                                                     @Param("riskName") String riskName,
                                                     @Param("riskCode") String riskCode,
                                                     @Param("organizationName") String organizationName,
                                                     @Param("planInstallStartDate") String planInstallStartDate,
                                                     @Param("planInstallEndDate") String planInstallEndDate);
    // 按风险点ID查询
    List<RiskPointDetail> selectDeviceListByRiskId(Long riskPointId);
    // 按风险点ID查询主表（兜底）
    RiskPointDetail selectRiskPointById(Long riskPointId);
}