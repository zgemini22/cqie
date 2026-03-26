package com.zds.user.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zds.biz.util.ThreadLocalUtil;
import com.zds.biz.vo.request.user.GsCutoffAreaNodeRequest;
import com.zds.biz.vo.response.user.GsCutoffAreaNodeDetailResponse;
import com.zds.biz.vo.response.user.GsCutoffAreaNodeResponse;
import com.zds.user.dao.GsCutoffAreaNodeDao;
import com.zds.user.po.GsCutoffAreaNode;
import com.zds.user.service.GsCutoffAreaNodeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class GsCutoffAreaNodeServiceImpl implements GsCutoffAreaNodeService {

    @Autowired
    private ThreadLocalUtil threadLocalUtil;

    @Autowired
    private GsCutoffAreaNodeDao gsCutoffAreaNodeDao;

    @Override
    public boolean save(GsCutoffAreaNodeRequest request) {
        GsCutoffAreaNode gsCutoffAreaNode = new GsCutoffAreaNode();
        BeanUtil.copyProperties(request, gsCutoffAreaNode);
        gsCutoffAreaNode.setOrgId(threadLocalUtil.getOrganizationId());
        return gsCutoffAreaNodeDao.insert(gsCutoffAreaNode) > 0;
    }

    @Override
    public boolean edit(GsCutoffAreaNodeRequest request) {
        GsCutoffAreaNode gsCutoffAreaNode = new GsCutoffAreaNode();
        BeanUtil.copyProperties(request, gsCutoffAreaNode);
        gsCutoffAreaNode.setOrgId(null);
        return gsCutoffAreaNodeDao.updateById(gsCutoffAreaNode) > 0;
    }

    @Override
    public boolean deleteById(Long id) {
        return gsCutoffAreaNodeDao.deleteById(id) > 0;
    }

    @Override
    public GsCutoffAreaNodeDetailResponse detail(Long id) {
        GsCutoffAreaNode gsCutoffAreaNode = gsCutoffAreaNodeDao.selectById(id);
        if (gsCutoffAreaNode == null) {
            throw new IllegalArgumentException("停气涉及区域不存在");
        }
        GsCutoffAreaNodeDetailResponse response = new GsCutoffAreaNodeDetailResponse();
        BeanUtil.copyProperties(gsCutoffAreaNode, response);
        return response;
    }

    @Override
    public IPage<GsCutoffAreaNodeResponse> list(GsCutoffAreaNodeRequest request) {
        return gsCutoffAreaNodeDao.selectPageWithJoin(new Page<>(request.getPageNum(), request.getPageSize()), request);
    }
}
