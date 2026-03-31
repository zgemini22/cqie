package com.zds.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zds.biz.vo.request.user.OrgCompanySaveRequest;
import com.zds.biz.vo.request.user.OrgSaveRequest;
import com.zds.biz.vo.response.user.OrgCompanyDetailResponse;
import com.zds.biz.vo.response.user.OrgCompanyResponse;
import com.zds.user.dao.TblOrganizationDao;
import com.zds.user.dao.TblOrganizationInfoDao;
import com.zds.user.po.TblOrganization;
import com.zds.user.po.TblOrganizationInfo;
import com.zds.user.service.OrgCompanyService;
import com.zds.user.service.OrgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class OrgCompanyServiceImpl implements OrgCompanyService {

    @Autowired
    private TblOrganizationInfoDao organizationInfoDao;

    @Autowired
    private TblOrganizationDao organizationDao;

    @Autowired
    private OrgService orgService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<OrgCompanyResponse> list(String name) {
        System.out.println("=== OrgCompanyServiceImpl.list 收到的 name 参数 ===: " + name);
        LambdaQueryWrapper<TblOrganizationInfo> wrapper = new LambdaQueryWrapper<>();

        // 修改：添加软删除过滤条件，只查询未删除的数据
        wrapper.eq(TblOrganizationInfo::getDeleted, false);

        if (name != null && !name.isEmpty()) {
            wrapper.like(TblOrganizationInfo::getName, name);
        }

        List<TblOrganizationInfo> infoList = organizationInfoDao.selectList(wrapper);

        return infoList.stream()
                .map(info -> {
                    OrgCompanyResponse response = new OrgCompanyResponse();
                    response.setId(info.getOrgId());
                    TblOrganization org = organizationDao.selectById(info.getOrgId());
                    // 修改：组织表也要过滤已删除的
                    if (org != null && !org.getDeleted()) {
                        response.setName(org.getOrganizationName());
                        response.setShortName(info.getShortName());
                        response.setCreditCode(info.getCreditCode());
                        response.setAddress(info.getAddress());
                        response.setLicenseNumber(info.getLicenseNumber());
                        response.setLicenseExpiryDate(info.getLicenseExpiryDate());
                        return response;
                    }
                    return null;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    @Override
    public OrgCompanyDetailResponse detail(Long id) {
        TblOrganizationInfo info = organizationInfoDao.selectOne(
                new LambdaQueryWrapper<TblOrganizationInfo>()
                        .eq(TblOrganizationInfo::getOrgId, id)
                        .eq(TblOrganizationInfo::getDeleted, false)  // 修改：只查询未删除的
        );

        if (info == null) {
            return null;
        }

        TblOrganization org = organizationDao.selectById(info.getOrgId());
        // 修改：组织表也要检查是否已删除
        if (org == null || org.getDeleted()) {
            return null;
        }

        OrgCompanyDetailResponse response = new OrgCompanyDetailResponse();
        response.setId(info.getOrgId());
        response.setName(org.getOrganizationName());
        response.setShortName(info.getShortName());
        response.setCreditCode(info.getCreditCode());
        response.setBusinessNature(info.getBusinessNature());
        response.setLegalPerson(info.getLegalPerson());
        response.setRegisteredCapital(info.getRegisteredCapital());
        response.setRegistrationDate(info.getRegistrationDate());
        response.setScale(info.getScale());
        response.setCode(info.getCode());
        response.setAddress(info.getAddress());
        response.setLicenseIssuingAuthority(info.getLicenseIssuingAuthority());
        response.setLicenseNumber(info.getLicenseNumber());
        response.setLicenseExpiryDate(info.getLicenseExpiryDate());
        response.setBusinessScope(info.getBusinessScope());
        response.setSalesScope(info.getSalesScope());
        response.setGasSupplyScope(info.getGasSupplyScope());
        response.setContactPerson(info.getContactPerson());
        response.setContactPhone(info.getContactPhone());
        return response;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean save(OrgCompanySaveRequest request) {
        // 1. 保存组织基本信息
        OrgSaveRequest orgRequest = new OrgSaveRequest();
        orgRequest.setOrganizationName(request.getName());
        orgRequest.setOrganizationType("company");
        orgRequest.setOrganizationStatus("1");
        orgService.saveOrg(orgRequest);

        // 获取新创建的orgId
        Long orgId = orgService.findOrgListByName(request.getName()).get(0);

        // 2. 保存企业详细信息
        TblOrganizationInfo info = new TblOrganizationInfo();
        info.setOrgId(orgId);
        info.setName(request.getName());
        info.setShortName(request.getShortName());
        info.setCreditCode(request.getCreditCode());
        info.setBusinessNature(request.getBusinessNature());
        info.setLegalPerson(request.getLegalPerson());
        info.setRegisteredCapital(request.getRegisteredCapital());
        info.setRegistrationDate(request.getRegistrationDate());
        info.setScale(request.getScale());
        info.setCode(request.getCode());
        info.setAddress(request.getAddress());
        info.setLicenseIssuingAuthority(request.getLicenseIssuingAuthority());
        info.setLicenseNumber(request.getLicenseNumber());
        info.setLicenseExpiryDate(request.getLicenseExpiryDate());
        info.setBusinessScope(request.getBusinessScope());
        info.setSalesScope(request.getSalesScope());
        info.setGasSupplyScope(request.getGasSupplyScope());
        info.setContactPerson(request.getContactPerson());
        info.setContactPhone(request.getContactPhone());
        info.setCreateTime(new Date());
        info.setDeleted(false);  // 修改：新增时设置未删除

        return organizationInfoDao.insert(info) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean edit(OrgCompanySaveRequest request) {
        // 1. 更新组织名称
        TblOrganization org = organizationDao.selectById(request.getId());
        if (org != null && !org.getDeleted()) {  // 修改：只编辑未删除的
            org.setOrganizationName(request.getName());
            organizationDao.updateById(org);
        }

        // 2. 更新企业信息
        TblOrganizationInfo info = organizationInfoDao.selectOne(
                new LambdaQueryWrapper<TblOrganizationInfo>()
                        .eq(TblOrganizationInfo::getOrgId, request.getId())
                        .eq(TblOrganizationInfo::getDeleted, false)  // 修改：只更新未删除的
        );

        if (info != null) {
            info.setName(request.getName());
            info.setShortName(request.getShortName());
            info.setCreditCode(request.getCreditCode());
            info.setBusinessNature(request.getBusinessNature());
            info.setLegalPerson(request.getLegalPerson());
            info.setRegisteredCapital(request.getRegisteredCapital());
            info.setRegistrationDate(request.getRegistrationDate());
            info.setScale(request.getScale());
            info.setCode(request.getCode());
            info.setAddress(request.getAddress());
            info.setLicenseIssuingAuthority(request.getLicenseIssuingAuthority());
            info.setLicenseNumber(request.getLicenseNumber());
            info.setLicenseExpiryDate(request.getLicenseExpiryDate());
            info.setBusinessScope(request.getBusinessScope());
            info.setSalesScope(request.getSalesScope());
            info.setGasSupplyScope(request.getGasSupplyScope());
            info.setContactPerson(request.getContactPerson());
            info.setContactPhone(request.getContactPhone());
            info.setUpdateTime(new Date());
            return organizationInfoDao.updateById(info) > 0;
        }

        return false;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean delete(Long id) {
        // 1. 软删除组织
        TblOrganization org = organizationDao.selectById(id);
        if (org != null && !org.getDeleted()) {  // 修改：只删除未删除的
            org.setDeleted(true);
            organizationDao.updateById(org);
        }

        // 2. 软删除企业信息
        TblOrganizationInfo info = organizationInfoDao.selectOne(
                new LambdaQueryWrapper<TblOrganizationInfo>()
                        .eq(TblOrganizationInfo::getOrgId, id)
                        .eq(TblOrganizationInfo::getDeleted, false)  // 修改：只删除未删除的
        );

        if (info != null) {
            info.setDeleted(true);
            return organizationInfoDao.updateById(info) > 0;
        }

        return false;
    }
}