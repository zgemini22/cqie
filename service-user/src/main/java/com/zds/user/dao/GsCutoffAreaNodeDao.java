package com.zds.user.dao;

import com.zds.user.po.GsCutoffAreaNode;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zds.biz.vo.request.user.GsCutoffAreaNodeRequest;
import com.zds.biz.vo.response.user.GsCutoffAreaNodeResponse;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface GsCutoffAreaNodeDao extends BaseMapper<GsCutoffAreaNode> {

    IPage<GsCutoffAreaNodeResponse> selectPageWithJoin(Page<?> page, @Param("request") GsCutoffAreaNodeRequest request);

}
