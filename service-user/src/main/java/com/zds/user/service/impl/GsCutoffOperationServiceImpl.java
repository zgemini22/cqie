package com.zds.user.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zds.biz.util.ThreadLocalUtil;
import com.zds.biz.vo.request.user.GsCutoffOperationRequest;
import com.zds.biz.vo.response.user.GsCutoffOperationDetailResponse;
import com.zds.biz.vo.response.user.GsCutoffOperationResponse;
import com.zds.user.dao.GsCutoffAreaNodeDao;
import com.zds.user.dao.GsCutoffOperationDao;
import com.zds.user.dao.TblOrganizationDao;
import com.zds.user.po.GsCutoffAreaNode;
import com.zds.user.po.GsCutoffOperation;
import com.zds.user.po.TblOrganization;
import com.zds.user.service.GsCutoffOperationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class GsCutoffOperationServiceImpl implements GsCutoffOperationService {

    @Autowired
    private ThreadLocalUtil threadLocalUtil;

    @Autowired
    private GsCutoffOperationDao gsCutoffOperationDao;

    @Autowired
    private GsCutoffAreaNodeDao gsCutoffAreaNodeDao;

    @Autowired
    private TblOrganizationDao tblOrganizationDao;

    @Override
    public boolean save(GsCutoffOperationRequest request) {
        GsCutoffOperation gsCutoffOperation = new GsCutoffOperation();
        BeanUtil.copyProperties(request, gsCutoffOperation);
        gsCutoffOperation.setOrgId(threadLocalUtil.getOrganizationId());
        return gsCutoffOperationDao.insert(gsCutoffOperation) > 0;
    }

    @Override
    public boolean edit(GsCutoffOperationRequest request) {
        GsCutoffOperation gsCutoffOperation = new GsCutoffOperation();
        BeanUtil.copyProperties(request, gsCutoffOperation);
        gsCutoffOperation.setOrgId(null);
        return gsCutoffOperationDao.updateById(gsCutoffOperation) > 0;
    }

    @Override
    public boolean deleteById(Long id) {
        return gsCutoffOperationDao.deleteById(id) > 0;
    }

    @Override
    public GsCutoffOperationDetailResponse detail(Long id, String detailAddress) {
        GsCutoffOperation gsCutoffOperation = gsCutoffOperationDao.selectById(id);
        if (gsCutoffOperation == null) {
            throw new IllegalArgumentException("停气作业不存在");
        }
        if (StrUtil.isBlank(detailAddress)) {
            throw new IllegalArgumentException("停气区域为空");
        }
        GsCutoffOperationDetailResponse response = new GsCutoffOperationDetailResponse();
        response.setDetailAddress(detailAddress);
        BeanUtil.copyProperties(gsCutoffOperation, response);

        // 查询organization表获得城燃企业
        LambdaQueryWrapper<TblOrganization> orgWrapper = new LambdaQueryWrapper<>();
        orgWrapper.eq(TblOrganization::getId, gsCutoffOperation.getOrgId());
        TblOrganization tblOrganization = tblOrganizationDao.selectOne(orgWrapper);
        if (tblOrganization != null) {
            response.setOrganizationName(tblOrganization.getOrganizationName());
        }

        return response;
    }

    @Override
    public IPage<GsCutoffOperationResponse> list(GsCutoffOperationRequest request) {
        return gsCutoffOperationDao.selectPageWithJoin(new Page<>(request.getPageNum(), request.getPageSize()), request);
    }
}
