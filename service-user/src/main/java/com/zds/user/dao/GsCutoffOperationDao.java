package com.zds.user.dao;

import com.zds.user.po.GsCutoffOperation;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zds.biz.vo.request.user.GsCutoffOperationRequest;
import com.zds.biz.vo.response.user.GsCutoffOperationResponse;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface GsCutoffOperationDao extends BaseMapper<GsCutoffOperation> {

    IPage<GsCutoffOperationResponse> selectPageWithJoin(Page<?> page, @Param("request") GsCutoffOperationRequest request);

}
