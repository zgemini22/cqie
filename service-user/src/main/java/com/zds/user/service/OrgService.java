package com.zds.user.service;

import com.zds.biz.vo.request.user.OrgSaveRequest;
import com.zds.biz.vo.request.user.OrgSelectRequest;
import com.zds.biz.vo.request.user.OrgStatusRequest;
import com.zds.biz.vo.response.user.OrgDetailResponse;
import com.zds.biz.vo.response.user.OrgResponse;

import java.util.List;
import java.util.Map;

/**
 * 单位服务
 */
public interface OrgService {
    /**
     * 查询所有单位
     */
    List<OrgResponse> findList();

    /**
     * 单位详情
     */
    OrgDetailResponse findDetail(Long id);

    /**
     * 保存单位
     */
    boolean saveOrg(OrgSaveRequest request);

    /**
     * 更新单位状态
     */
    boolean updateStatus(OrgStatusRequest request);

    /**
     * 删除单位
     */
    boolean delete(Long id);

    /**
     * 单位下拉
     */
    List<OrgResponse> findSelect(OrgSelectRequest request);

    /**
     * 单位下拉(不过滤权限,过滤政府单位有用户的)
     */
    List<OrgResponse> orgSelectHavUser();

    /**
     * 当前用户单位派系下拉
     */
    List<OrgResponse> findFactionsSelectByUser();

    /**
     * 企业单位下拉
     */
    List<OrgResponse> findCompanySelect();

    /**
     * 单位下拉(默认过滤)
     */
    List<OrgResponse> findDefaultSelect();

    /**
     * 查询指定单位范围的单位名称
     */
    Map<Long, String> findOrgMapById(List<Long> request);

    /**
     * 查询所有单位信息
     */
    List<OrgResponse> findAllOrgInfo(String secretKey);

    /**
     * 单位名称模糊查询单位ID集合
     */
    List<Long> findOrgListByName(String organizationName);

    /**
     * 查询当前用户可见的单位范围
     */
    List<Long> findOrgVisibleListByUser();

    /**
     * 查询指定单位可见的单位ID集合
     */
    List<Long> findOrgVisibleListByOrg(Long organizationId);

    /**
     * 查询所有企业信息
     */
    List<OrgResponse> infoCompany();

    /**
     * 查询三个城燃企业对应的组织集合
     */
    Map<String, List<Long>> getOrgChildOrgMap();

    /**
     * 查询三个城燃企业下拉
     */
    List<OrgResponse> childOrgSelect();
}
