package com.zds.user.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zds.biz.vo.request.user.TblOrganizationBusinessRequest;
import com.zds.biz.vo.response.user.TblOrganizationBusinessResponse;
import com.zds.user.po.TblOrganizationBusiness;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TblOrganizationBusinessDao extends BaseMapper<TblOrganizationBusiness> {

    IPage<TblOrganizationBusinessResponse> selectPageWithCondition(Page<?> page, @Param("request") TblOrganizationBusinessRequest request);

    TblOrganizationBusinessResponse selectDetailById(@Param("id") Long id);

}