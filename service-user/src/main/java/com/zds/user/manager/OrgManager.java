package com.zds.user.manager;

import com.zds.user.po.TblOrganization;
import org.nfunk.jep.function.Str;

import java.util.List;
import java.util.Map;

public interface OrgManager {

    Map<Long, String> getOrgMap(List<Long> ids);

    Map<Long, TblOrganization> getOrgPoMap(List<Long> ids);

    /**
     * 查询本单位及下属单位ID集合
     */
    List<Long> findChildOrg(Long organizationId);

    /**
     * 查询本单位及下属单位ID集合
     */
    List<Long> findChildOrgNotNull(Long organizationId);

    /**
     * 查询三个城燃企业对应的组织集合
     */
    Map<String, List<Long>> getOrgChildOrgMap();
}
