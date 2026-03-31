package com.zds.user.service;

import com.zds.biz.vo.request.user.OrgCompanySaveRequest;
import com.zds.biz.vo.response.user.OrgCompanyDetailResponse;
import com.zds.biz.vo.response.user.OrgCompanyResponse;

import java.util.List;

public interface OrgCompanyService {

    List<OrgCompanyResponse> list(String name);

    OrgCompanyDetailResponse detail(Long id);

    boolean save(OrgCompanySaveRequest request);

    boolean edit(OrgCompanySaveRequest request);

    boolean delete(Long id);
}