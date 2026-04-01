package com.zds.user.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zds.user.dao.risk.RiskPointMapper;
import com.zds.user.po.risk.RiskPointDetail;
import com.zds.user.service.RiskPointService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class RiskPointServiceImpl implements RiskPointService {

    @Resource
    private RiskPointMapper riskPointMapper;

    @Override
    public List<RiskPointDetail> getDetail(Long riskPointId) {
        return riskPointMapper.selectDeviceListByRiskId(riskPointId);
    }

    @Override
    public IPage<RiskPointDetail> selectRiskPointPage(Page<RiskPointDetail> page,
                                                      String riskName,
                                                      String riskCode,
                                                      String organizationName,
                                                      String planInstallStartDate,
                                                      String planInstallEndDate) {
        return riskPointMapper.selectRiskPointDetailPage(page, riskName, riskCode, organizationName, planInstallStartDate, planInstallEndDate);
    }

}