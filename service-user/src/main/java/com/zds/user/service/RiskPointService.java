package com.zds.user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zds.user.po.risk.RiskPointDetail;

import java.util.List;

public interface RiskPointService {

    List<RiskPointDetail> getDetail(Long id);

    IPage<RiskPointDetail> selectRiskPointPage(Page<RiskPointDetail> page,
                                               String riskName,
                                               String riskCode,
                                               String unit,
                                               String planInstallStartDate,
                                               String planInstallEndDate);
}