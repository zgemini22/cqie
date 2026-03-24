package com.zds.user.manager.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zds.biz.constant.user.OrganizationNodeEnum;
import com.zds.biz.constant.user.OrganizationStatusEnum;
import com.zds.user.dao.TblOrganizationDao;
import com.zds.user.manager.OrgManager;
import com.zds.user.po.TblOrganization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrgManagerImpl implements OrgManager {

    @Autowired
    private TblOrganizationDao organizationDao;

    @Override
    public Map<Long, String> getOrgMap(List<Long> ids) {
        Map<Long, String> map = new HashMap<>();
        if (ids.size() > 0) {
            map = organizationDao.selectBatchIds(ids).stream()
                    .collect(Collectors.toMap(TblOrganization::getId, TblOrganization::getOrganizationName, (a, b) -> b));
        }
        return map;
    }

    @Override
    public Map<Long, TblOrganization> getOrgPoMap(List<Long> ids) {
        Map<Long, TblOrganization> map = new HashMap<>();
        if (ids.size() > 0) {
            map = organizationDao.selectBatchIds(ids).stream()
                    .collect(Collectors.toMap(TblOrganization::getId, x -> x, (a, b) -> b));
        }
        return map;
    }

    @Override
    public List<Long> findChildOrg(Long organizationId) {
        if (organizationId.equals(OrganizationNodeEnum.ROOT_COMPANY_CODE.getCode())) {
            return null;
        }
        List<Long> ids = new ArrayList<>();
        ids.add(organizationId);
        List<TblOrganization> list = organizationDao.selectList(TblOrganization.getWrapper()
                .orderByDesc(TblOrganization::getCreateTime)
                .eq(TblOrganization::getDeleted, false)
                .like(TblOrganization::getParentUrl, "/" + organizationId + "/"));
        ids.addAll(list.stream().map(TblOrganization::getId).collect(Collectors.toList()));
        return ids;
    }

    @Override
    public List<Long> findChildOrgNotNull(Long organizationId) {
        List<Long> ids = new ArrayList<>();
        ids.add(organizationId);
        List<TblOrganization> list = organizationDao.selectList(TblOrganization.getWrapper()
                .orderByDesc(TblOrganization::getCreateTime)
                .eq(TblOrganization::getDeleted, false)
                .like(TblOrganization::getParentUrl, "/" + organizationId + "/"));
        ids.addAll(list.stream().map(TblOrganization::getId).collect(Collectors.toList()));
        return ids;
    }

    @Override
    public Map<String, List<Long>> getOrgChildOrgMap() {
        //查询城燃企业
        Map<String, List<Long>> map = new HashMap<>();
        LambdaQueryWrapper<TblOrganization> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TblOrganization::getDeleted,false);
        queryWrapper.eq(TblOrganization::getOrganizationStatus, OrganizationStatusEnum.ENABLE.getKey());
        queryWrapper.in(TblOrganization::getOrganizationName, Arrays.asList("重燃沙坪坝分公司","凯源(利民)燃气科学城分公司","中梁山渝能燃气公司"));
        Map<Long, String> map1 = organizationDao.selectList(queryWrapper).stream().collect(Collectors.toMap(TblOrganization::getId, TblOrganization::getOrganizationName));
        LambdaQueryWrapper<TblOrganization> queryWrapper1 = new LambdaQueryWrapper<>();
        queryWrapper1.eq(TblOrganization::getDeleted,false);
        queryWrapper1.eq(TblOrganization::getOrganizationStatus, OrganizationStatusEnum.ENABLE.getKey());
        List<TblOrganization> organizations = organizationDao.selectList(queryWrapper1);
        //组装map
        for (Long aLong : map1.keySet()) {
            List<Long> ids = new ArrayList<>();
            ids.add(aLong);
            for (TblOrganization organization : organizations) {
                if (organization.getParentUrl().contains(aLong.toString())) {
                    ids.add(organization.getId());
                }
            }
            map.put(map1.get(aLong),ids);
        }
        return map;
    }
}
