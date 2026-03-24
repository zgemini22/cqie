package com.zds.user.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zds.biz.util.ThreadLocalUtil;
import com.zds.biz.vo.request.user.TpcConstructionRequest;
import com.zds.biz.vo.response.user.TpcConstructionDetailResponse;
import com.zds.biz.vo.response.user.TpcConstructionResponse;
import com.zds.user.dao.TpcConstructionDao;
import com.zds.user.po.TpcConstruction;
import com.zds.user.service.TpcConstructionService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TpcConstructionServiceImpl implements TpcConstructionService {

    @Autowired
    private ThreadLocalUtil threadLocalUtil;

    @Autowired
    private TpcConstructionDao tpcConstructionDao;

    @Override
    public boolean save(TpcConstructionRequest saveRequest) {
        TpcConstruction tpcConstruction = new TpcConstruction();
        BeanUtil.copyProperties(saveRequest, tpcConstruction);
        tpcConstruction.setOrgId(threadLocalUtil.getOrganizationId());
        return tpcConstructionDao.insert(tpcConstruction) > 0;
    }

    @Override
    public boolean edit(TpcConstructionRequest updateRequest) {
        TpcConstruction tpcConstruction = new TpcConstruction();
        BeanUtil.copyProperties(updateRequest, tpcConstruction);
        tpcConstruction.setOrgId(null);
        return tpcConstructionDao.updateById(tpcConstruction) > 0;
    }

    @Override
    public boolean deleteById(Long id) {
        return tpcConstructionDao.deleteById(id) > 0;
    }

    @Override
    public TpcConstructionDetailResponse detail(Long id) {
        TpcConstruction tpcConstruction = tpcConstructionDao.selectById(id);
        TpcConstructionDetailResponse rsp = new TpcConstructionDetailResponse();
        BeanUtil.copyProperties(tpcConstruction, rsp);
        return rsp;
    }

    @Override
    public IPage<TpcConstructionResponse> list(TpcConstructionRequest request) {
        LambdaQueryWrapper<TpcConstruction> wrapper = new LambdaQueryWrapper<>();
        wrapper.like( StrUtil.isNotBlank(request.getConstructionCode()), TpcConstruction::getConstructionCode, request.getConstructionCode())
                .like(StrUtil.isNotBlank(request.getConstructionName()), TpcConstruction::getConstructionName, request.getConstructionName())
                .eq(StrUtil.isNotBlank(request.getProjectStatus()), TpcConstruction::getProjectStatus, request.getProjectStatus())
                .like(StrUtil.isNotBlank(request.getAddress()), TpcConstruction::getAddress, request.getAddress())
                .like(StrUtil.isNotBlank(request.getConstructionCompany()), TpcConstruction::getConstructionCompany, request.getConstructionCompany())
                .eq(request.getIsNoticeSigned() != null, TpcConstruction::getIsNoticeSigned, request.getIsNoticeSigned())
                .eq(request.getIsSafetySigned() != null, TpcConstruction::getIsSafetySigned, request.getIsSafetySigned())
                .orderByDesc(TpcConstruction::getCreateTime);
        Page<TpcConstruction> list = tpcConstructionDao.selectPage(new Page<>(request.getPageNum(), request.getPageSize()), wrapper);
        return list.convert(po -> {
            TpcConstructionResponse vo = new TpcConstructionResponse();
            BeanUtils.copyProperties(po, vo);
            return vo;
        });
    }
}
