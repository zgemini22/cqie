package com.zds.user.service;

import com.zds.biz.vo.request.user.OrganizationPersonSaveRequest;
import com.zds.biz.vo.request.user.OrganizationPersonSelectRequest;
import com.zds.biz.vo.response.user.OrganizationPersonDetailResponse;
import com.zds.biz.vo.response.user.OrganizationPersonResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 组织人员服务
 */
public interface TblOrganizationPersonService {
    /**
     * 查询所有组织人员
     */
    List<OrganizationPersonResponse> findList(OrganizationPersonSelectRequest request);

    /**
     * 组织人员详情
     */
    OrganizationPersonDetailResponse findDetail(Long id);

    /**
     * 保存组织人员
     */
    boolean saveOrganizationPerson(OrganizationPersonSaveRequest request);

    /**
     * 删除组织人员
     */
    boolean delete(Long id);

    /**
     * 组织人员下拉
     */
    List<OrganizationPersonResponse> findSelect(OrganizationPersonSelectRequest request);


    // ====================== 批量导入（JSON数组） ======================
    /**
     * 批量导入从业人员
     */
    boolean importOrganizationPerson(List<OrganizationPersonSaveRequest> requestList);

    // ====================== Excel 导入（你要加的） ======================
    /**
     * Excel批量导入从业人员
     */
    void importByExcel(MultipartFile file) throws Exception;
}