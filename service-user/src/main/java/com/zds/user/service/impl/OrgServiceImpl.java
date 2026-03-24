package com.zds.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zds.biz.constant.BaseException;
import com.zds.biz.constant.SecretKeyEnum;
import com.zds.biz.constant.user.OrganizationNodeEnum;
import com.zds.biz.constant.user.OrganizationStatusEnum;
import com.zds.biz.constant.user.OrganizationTypeEnum;
import com.zds.biz.constant.user.UserTypeEnum;
import com.zds.biz.util.ThreadLocalUtil;
import com.zds.biz.vo.request.flow.ServiceOrgSaveRequest;
import com.zds.biz.vo.request.user.OrgSaveRequest;
import com.zds.biz.vo.request.user.OrgSelectRequest;
import com.zds.biz.vo.request.user.OrgStatusRequest;
import com.zds.biz.vo.response.user.OrgDetailResponse;
import com.zds.biz.vo.response.user.OrgResponse;
import com.zds.user.dao.TblOrganizationDao;
import com.zds.user.dao.TblUserDao;
import com.zds.user.feign.FlowService;
import com.zds.user.manager.OrgManager;
import com.zds.user.manager.UserManager;
import com.zds.user.po.TblOrganization;
import com.zds.user.po.TblUser;
import com.zds.user.service.OrgService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OrgServiceImpl implements OrgService {

    @Autowired
    private TblOrganizationDao organizationDao;

    @Autowired
    private TblUserDao userDao;

    @Autowired
    private UserManager userManager;

    @Autowired
    private OrgManager orgManager;

    @Autowired
    private ThreadLocalUtil threadLocalUtil;

    @Autowired
    private FlowService flowService;

    @Override
    public List<OrgResponse> findList() {
        return organizationDao.selectList(TblOrganization.getWrapper()
                .orderByDesc(TblOrganization::getCreateTime)
                .eq(TblOrganization::getDeleted, false)
                .like(!threadLocalUtil.getOrganizationId().equals(OrganizationNodeEnum.ROOT_COMPANY_CODE.getCode()), TblOrganization::getParentUrl, "/" + threadLocalUtil.getOrganizationId() + "/"))//非系统管理组,只能查看本单位及下属单位
                .stream()
                .map(x -> OrgResponse.builder()
                        .id(x.getId())
                        .organizationName(x.getOrganizationName())
                        .organizationType(x.getOrganizationType())
                        .organizationStatus(x.getOrganizationStatus())
                        .parentId(x.getParentId())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public OrgDetailResponse findDetail(Long id) {
        TblOrganization po = organizationDao.selectById(id);
        return OrgDetailResponse.builder()
                .id(po.getId())
                .organizationName(po.getOrganizationName())
                .organizationType(po.getOrganizationType())
                .organizationStatus(po.getOrganizationStatus())
                .parentId(po.getParentId())
                .build();
    }

    @Override
    public boolean saveOrg(OrgSaveRequest request) {
        request.toRequestCheck();
        String parentUrl;
        if (request.getParentId() == null || request.getParentId() == 0L) {
            request.setParentId(0L);
            parentUrl = "/0/";
        } else {
            TblOrganization parentOrg = organizationDao.selectById(request.getParentId());
            if (parentOrg == null) {
                throw new BaseException("未找到指定上级单位");
            } else {
                //判断是否选择自己的下级作为上级单位
                if (request.getId() != null && parentOrg.getParentUrl().contains("/" + request.getId() + "/")) {
                    throw new BaseException("上级单位不能选择本单位的下属单位");
                }
                parentUrl = parentOrg.getParentUrl() + parentOrg.getId() + "/";
            }
        }
        TblOrganization po = TblOrganization.builder().parentUrl(parentUrl).build();
        BeanUtils.copyProperties(request, po);
        boolean flag;
        if (request.getId() == null) {
            flag = organizationDao.insert(po) == 1;
        } else {
            TblOrganization old = organizationDao.selectById(po.getId());
            flag = organizationDao.updateById(po) == 1;
            //状态有变更
            if (!old.getOrganizationStatus().equals(po.getOrganizationStatus())) {
                OrgStatusRequest orgStatusRequest = new OrgStatusRequest();
                orgStatusRequest.setId(po.getId());
                orgStatusRequest.setOrganizationStatus(po.getOrganizationStatus());
                updateStatus(orgStatusRequest);
            }
        }
        if (flag) {
            flowService.orgSaveByService(ServiceOrgSaveRequest.builder()
                    .orgId(po.getId().toString())
                    .name(po.getOrganizationName())
                    .parentId(po.getParentId().toString())
                    .build());
        }
        return flag;
    }

    @Override
    public boolean updateStatus(OrgStatusRequest request) {
        //查询本单位及下属单位ID集合
        List<Long> ids = orgManager.findChildOrgNotNull(request.getId());
        //批量更新单位状态
        int count = organizationDao.update(TblOrganization.builder().organizationStatus(request.getOrganizationStatus()).build(),
                TblOrganization.getWrapper().in(TblOrganization::getId, ids));
        //单位冻结或禁用,清理相关用户token
        if (OrganizationStatusEnum.FROZEN.getKey().equals(request.getOrganizationStatus()) || OrganizationStatusEnum.DISABLE.getKey().equals(request.getOrganizationStatus())) {
            userManager.cleanUserTokenByOrg(ids);
        }
        return count > 0;
    }

    @Override
    public boolean delete(Long id) {
        //查询本单位及下属单位ID集合
        List<Long> ids = orgManager.findChildOrgNotNull(id);
        //批量删除单位
        boolean flag = organizationDao.update(TblOrganization.builder().deleted(true).build(),
                TblOrganization.getWrapper().in(TblOrganization::getId, ids)) > 0;
        if (flag) {
            //单位冻结或禁用,清理相关用户token
            userManager.cleanUserTokenByOrg(ids);
        }
        return flag;
    }

    @Override
    public List<OrgResponse> findSelect(OrgSelectRequest request) {
        List<Long> orgIds = null;
        if (request.isDataFlag()) {
            orgIds = findOrgVisibleListByUser();
        }
        LambdaQueryWrapper<TblOrganization> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(TblOrganization::getCreateTime)
                .eq(TblOrganization::getDeleted, false)
                .eq(StringUtils.isNotEmpty(request.getOrganizationType()), TblOrganization::getOrganizationType, request.getOrganizationType())
                .like(request.getId() != null, TblOrganization::getParentUrl, "/" + request.getId() + "/")
                .in(orgIds != null && request.isDataFlag(), TblOrganization::getId, orgIds);//非系统管理组,只能查看本单位及下属单位
        return organizationDao.selectList(wrapper).stream()
                .map(x -> OrgResponse.builder()
                        .id(x.getId())
                        .organizationName(x.getOrganizationName())
                        .organizationType(x.getOrganizationType())
                        .organizationStatus(x.getOrganizationStatus())
                        .parentId(x.getParentId())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public List<OrgResponse> orgSelectHavUser() {
        List<Object> objects = userDao.selectObjs(TblUser.getWrapper()
                .select(TblUser::getOrganizationId)
                .eq(TblUser::getDeleted, false)
                .eq(TblUser::getUserType, UserTypeEnum.ORG_USER.getKey()));
        if (objects == null || objects.size() == 0) {
            return new ArrayList<>();
        }
        LambdaQueryWrapper<TblOrganization> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(TblOrganization::getCreateTime)
                .eq(TblOrganization::getDeleted, false)
                .eq(TblOrganization::getOrganizationType, OrganizationTypeEnum.GOVERNMENT.getKey())
                .in(TblOrganization::getId, objects);
        return organizationDao.selectList(wrapper).stream()
                .map(x -> OrgResponse.builder()
                        .id(x.getId())
                        .organizationName(x.getOrganizationName())
                        .organizationType(x.getOrganizationType())
                        .organizationStatus(x.getOrganizationStatus())
                        .parentId(x.getParentId())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public List<OrgResponse> findFactionsSelectByUser() {
        Long organizationId = threadLocalUtil.getOrganizationId();
        List<TblOrganization> list;
        LambdaQueryWrapper<TblOrganization> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(TblOrganization::getCreateTime)
                .eq(TblOrganization::getDeleted, false);
        if (organizationId == 0L) {
            list = organizationDao.selectList(wrapper);
        } else {
            TblOrganization organization = organizationDao.selectById(threadLocalUtil.getOrganizationId());
            if (organization.getParentId() != 0L) {
                String parentUrl = organization.getParentUrl();
                int i = parentUrl.indexOf("/", 3);
                organizationId = Long.valueOf(parentUrl.substring(3, i));
            }
            wrapper.like(TblOrganization::getParentUrl, "/" + organizationId + "/");
            list = organizationDao.selectList(wrapper);
            if (organization.getParentId() == 0L) {
                list.add(organization);
            }
        }
        return list.stream()
                .map(x -> OrgResponse.builder()
                        .id(x.getId())
                        .organizationName(x.getOrganizationName())
                        .organizationType(x.getOrganizationType())
                        .organizationStatus(x.getOrganizationStatus())
                        .parentId(x.getParentId())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public List<OrgResponse> findCompanySelect() {
        List<Long> orgIds = threadLocalUtil.getOrganizationType().equals(OrganizationTypeEnum.COMPANY.getKey()) ? findOrgVisibleListByUser() : null;
        LambdaQueryWrapper<TblOrganization> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(TblOrganization::getCreateTime)
                .eq(TblOrganization::getDeleted, false)
                .eq(TblOrganization::getOrganizationType, OrganizationTypeEnum.COMPANY.getKey())
                .in(orgIds != null, TblOrganization::getId, orgIds);//企业只能查看本单位及下属单位
        return organizationDao.selectList(wrapper).stream()
                .map(x -> OrgResponse.builder()
                        .id(x.getId())
                        .organizationName(x.getOrganizationName())
                        .organizationType(x.getOrganizationType())
                        .organizationStatus(x.getOrganizationStatus())
                        .parentId(x.getParentId())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public List<OrgResponse> findDefaultSelect() {
        List<Long> orgIds = threadLocalUtil.getOrganizationType().equals(OrganizationTypeEnum.COMPANY.getKey()) ? findOrgVisibleListByUser() : null;
        LambdaQueryWrapper<TblOrganization> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(TblOrganization::getCreateTime)
                .eq(TblOrganization::getDeleted, false)
                .eq(threadLocalUtil.getOrganizationType().equals(OrganizationTypeEnum.COMPANY.getKey()), TblOrganization::getOrganizationType, OrganizationTypeEnum.COMPANY.getKey())
                .in(orgIds != null, TblOrganization::getId, orgIds);//政府查看所有单位,企业查看自身及下属单位
        return organizationDao.selectList(wrapper).stream()
                .map(x -> OrgResponse.builder()
                        .id(x.getId())
                        .organizationName(x.getOrganizationName())
                        .organizationType(x.getOrganizationType())
                        .organizationStatus(x.getOrganizationStatus())
                        .parentId(x.getParentId())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public Map<Long, String> findOrgMapById(List<Long> request) {
        return organizationDao.selectList(TblOrganization.getWrapper()
                .orderByDesc(TblOrganization::getCreateTime)
                .in(request != null && request.size() > 0, TblOrganization::getId, request))
                .stream()
                .collect(Collectors.toMap(TblOrganization::getId, TblOrganization::getOrganizationName, (a, b) -> b));
    }

    @Override
    public List<OrgResponse> findAllOrgInfo(String secretKey) {
        if (!secretKey.equals(SecretKeyEnum.SECRET_KEY.getKey())) {
            return new ArrayList<>();
        } else {
            return organizationDao.selectList(TblOrganization.getWrapper()
                    .orderByDesc(TblOrganization::getCreateTime)
                    .eq(TblOrganization::getDeleted, false))
                    .stream()
                    .map(x -> OrgResponse.builder()
                            .id(x.getId())
                            .organizationName(x.getOrganizationName())
                            .organizationType(x.getOrganizationType())
                            .organizationStatus(x.getOrganizationStatus())
                            .parentId(x.getParentId())
                            .build())
                    .collect(Collectors.toList());
        }
    }

    @Override
    public List<Long> findOrgListByName(String organizationName) {
        return organizationDao.selectList(TblOrganization.getWrapper()
                    .orderByDesc(TblOrganization::getCreateTime)
                    .eq(TblOrganization::getDeleted, false)
                    .like(TblOrganization::getOrganizationName, organizationName))
                .stream()
                .map(TblOrganization::getId)
                .collect(Collectors.toList());
    }

    @Override
    public List<Long> findOrgVisibleListByUser() {
        return orgManager.findChildOrg(threadLocalUtil.getOrganizationId());
    }

    @Override
    public List<Long> findOrgVisibleListByOrg(Long organizationId) {
        return orgManager.findChildOrgNotNull(organizationId);
    }

    @Override
    public List<OrgResponse> infoCompany() {
        return organizationDao.selectList(TblOrganization.getWrapper()
                .orderByDesc(TblOrganization::getCreateTime)
                .eq(TblOrganization::getDeleted, false)
                .eq(TblOrganization::getOrganizationType, OrganizationTypeEnum.COMPANY.getKey()))
                .stream()
                .map(x -> OrgResponse.builder()
                        .id(x.getId())
                        .organizationName(x.getOrganizationName())
                        .organizationType(x.getOrganizationType())
                        .organizationStatus(x.getOrganizationStatus())
                        .parentId(x.getParentId())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public Map<String, List<Long>> getOrgChildOrgMap() {
        return orgManager.getOrgChildOrgMap();
    }

    @Override
    public List<OrgResponse> childOrgSelect() {
        LambdaQueryWrapper<TblOrganization> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(TblOrganization::getDeleted,false);
        queryWrapper.eq(TblOrganization::getOrganizationStatus, OrganizationStatusEnum.ENABLE.getKey());
        queryWrapper.in(TblOrganization::getOrganizationName, Arrays.asList("重燃沙坪坝分公司","凯源(利民)燃气科学城分公司","中梁山渝能燃气公司"));
        return organizationDao.selectList(queryWrapper).stream()
                .map(x -> OrgResponse.builder()
                        .id(x.getId())
                        .organizationName(x.getOrganizationName())
                        .organizationType(x.getOrganizationType())
                        .organizationStatus(x.getOrganizationStatus())
                        .parentId(x.getParentId())
                        .build())
                .collect(Collectors.toList());
    }
}
