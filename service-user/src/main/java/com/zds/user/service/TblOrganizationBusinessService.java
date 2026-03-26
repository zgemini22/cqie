package com.zds.user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zds.biz.vo.request.user.TblOrganizationBusinessRequest;
import com.zds.biz.vo.response.user.TblOrganizationBusinessResponse;

public interface TblOrganizationBusinessService {

    boolean save(TblOrganizationBusinessRequest saveRequest);

    boolean edit(TblOrganizationBusinessRequest request);

    boolean deleteById(Long id);

    TblOrganizationBusinessResponse detail(Long id);

    IPage<TblOrganizationBusinessResponse> list(TblOrganizationBusinessRequest request);

}