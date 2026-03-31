package com.zds.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zds.biz.constant.BaseException;
import com.zds.biz.util.ThreadLocalUtil;
import com.zds.biz.vo.request.user.OrganizationPersonSaveRequest;
import com.zds.biz.vo.request.user.OrganizationPersonSelectRequest;
import com.zds.biz.vo.response.user.OrganizationPersonDetailResponse;
import com.zds.biz.vo.response.user.OrganizationPersonResponse;
import com.zds.user.dao.TblOrganizationPersonDao;
import com.zds.user.po.TblOrganizationPerson;
import com.zds.user.service.TblOrganizationPersonService;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TblOrganizationPersonServiceImpl implements TblOrganizationPersonService {

    @Autowired
    private TblOrganizationPersonDao organizationPersonDao;

    @Autowired
    private ThreadLocalUtil threadLocalUtil;

    // ====================== 【条件查询已修复】 ======================
    //补全字段
    @Override
    public List<OrganizationPersonResponse> findList(OrganizationPersonSelectRequest request) {
        LambdaQueryWrapper<TblOrganizationPerson> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(TblOrganizationPerson::getCreatedAt)
                .isNull(TblOrganizationPerson::getDeletedAt);

        if (StringUtils.isNotBlank(request.getName())) {
            wrapper.like(TblOrganizationPerson::getName, request.getName());
        }
        if (StringUtils.isNotBlank(request.getPostType())) {
            wrapper.eq(TblOrganizationPerson::getPostType, request.getPostType());
        }
        if (request.getHasCertified() != null) {
            wrapper.eq(TblOrganizationPerson::getHasCertified, request.getHasCertified());
        }
        if (StringUtils.isNotBlank(request.getDepartment())) {
            wrapper.eq(TblOrganizationPerson::getDepartment, request.getDepartment());
        }
        if (request.getOrgId() != null) {
            wrapper.eq(TblOrganizationPerson::getOrgId, request.getOrgId());
        }

        // ===================== ✅ 下面三个是你需要新增的 =====================
        // 1. 证书编号
        if (StringUtils.isNotBlank(request.getCertifiedNo())) {
            wrapper.like(TblOrganizationPerson::getCertifiedNo, request.getCertifiedNo());
        }
        // 2. 取证时间范围
        if (StringUtils.isNotBlank(request.getGetCertifiedTimeStart())) {
            wrapper.ge(TblOrganizationPerson::getGetCertifiedTime, request.getGetCertifiedTimeStart());
        }
        if (StringUtils.isNotBlank(request.getGetCertifiedTimeEnd())) {
            wrapper.le(TblOrganizationPerson::getGetCertifiedTime, request.getGetCertifiedTimeEnd());
        }
        // 3. 证书有效期范围
        if (StringUtils.isNotBlank(request.getCertifiedValidityStart())) {
            wrapper.ge(TblOrganizationPerson::getCertifiedValidity, request.getCertifiedValidityStart());
        }
        if (StringUtils.isNotBlank(request.getCertifiedValidityEnd())) {
            wrapper.le(TblOrganizationPerson::getCertifiedValidity, request.getCertifiedValidityEnd());
        }

        return organizationPersonDao.selectList(wrapper)
                .stream()
                .map(x -> OrganizationPersonResponse.builder()
                        .id(x.getId())
                        .name(x.getName())
                        .gender(x.getGender())
                        .age(x.getAge())
                        .education(x.getEducation())
                        .phone(x.getPhone())
                        .department(x.getDepartment())
                        .postType(x.getPostType())
                        .professional(x.getProfessional())
                        .jobPosition(x.getJobPosition())
                        .orgId(x.getOrgId())
                        .hasCertified(x.getHasCertified())
                        .certifiedNo(x.getCertifiedNo())
                        .getCertifiedTime(x.getGetCertifiedTime())
                        .certifiedValidity(x.getCertifiedValidity())
                        .issuingUnit(x.getIssuingUnit())
                        .createdAt(x.getCreatedAt())
                        .build())
                .collect(Collectors.toList());
    }
    // ====================== 以下全部是你原来的代码，完全没动 ======================
    @Override
    public OrganizationPersonDetailResponse findDetail(Long id) {
        TblOrganizationPerson po = organizationPersonDao.selectById(id);
        if (po == null || po.getDeletedAt() != null) {
            throw new BaseException("未找到指定组织人员");
        }
        return OrganizationPersonDetailResponse.builder()
                .id(po.getId())
                .name(po.getName())
                .gender(po.getGender())
                .age(po.getAge())
                .education(po.getEducation())
                .idCard(po.getIdCard())
                .phone(po.getPhone())
                .department(po.getDepartment())
                .postType(po.getPostType())
                .professional(po.getProfessional())
                .jobPosition(po.getJobPosition())
                .skills(po.getSkills())
                .workingYears(po.getWorkingYears())
                .hasCertified(po.getHasCertified())
                .certifiedNo(po.getCertifiedNo())
                .getCertifiedTime(po.getGetCertifiedTime())
                .certifiedValidity(po.getCertifiedValidity())
                .issuingUnit(po.getIssuingUnit())
                .remark(po.getRemark())
                .orgId(po.getOrgId())
                .build();
    }

    @Override
    public boolean saveOrganizationPerson(OrganizationPersonSaveRequest request) {
        request.toRequestCheck();
        TblOrganizationPerson po = new TblOrganizationPerson();
        BeanUtils.copyProperties(request, po);
        boolean flag;
        if (request.getId() == null) {
            po.setCreatedAt(new java.util.Date());
            flag = organizationPersonDao.insert(po) == 1;
        } else {
            TblOrganizationPerson old = organizationPersonDao.selectById(po.getId());
            if (old == null || old.getDeletedAt() != null) {
                throw new BaseException("未找到指定组织人员");
            }
            po.setCreatedAt(old.getCreatedAt());
            po.setUpdatedAt(new java.util.Date());
            flag = organizationPersonDao.updateById(po) == 1;
        }
        return flag;
    }

    @Override
    public boolean delete(Long id) {
        TblOrganizationPerson po = organizationPersonDao.selectById(id);
        if (po == null || po.getDeletedAt() != null) {
            throw new BaseException("未找到指定组织人员");
        }
        po.setDeletedAt(new java.util.Date());
        return organizationPersonDao.updateById(po) == 1;
    }

    @Override
    public List<OrganizationPersonResponse> findSelect(OrganizationPersonSelectRequest request) {
        LambdaQueryWrapper<TblOrganizationPerson> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(TblOrganizationPerson::getCreatedAt)
                .isNull(TblOrganizationPerson::getDeletedAt)
                .eq(StringUtils.isNotEmpty(request.getDepartment()), TblOrganizationPerson::getDepartment, request.getDepartment())
                .eq(request.getOrgId() != null, TblOrganizationPerson::getOrgId, request.getOrgId())
                .like(StringUtils.isNotEmpty(request.getName()), TblOrganizationPerson::getName, request.getName());
        return organizationPersonDao.selectList(wrapper).stream()
                .map(x -> OrganizationPersonResponse.builder()
                        .id(x.getId())
                        .name(x.getName())
                        .gender(x.getGender())
                        .age(x.getAge())
                        .education(x.getEducation())
                        .phone(x.getPhone())
                        .department(x.getDepartment())
                        .postType(x.getPostType())
                        .professional(x.getProfessional())
                        .jobPosition(x.getJobPosition())
                        .orgId(x.getOrgId())
                        .build())
                .collect(Collectors.toList());
    }
    // ====================== 批量导入从业人员（完整版已加入） ======================
    @Override
    public boolean importOrganizationPerson(List<OrganizationPersonSaveRequest> requestList) {
        if (requestList == null || requestList.isEmpty()) {
            throw new BaseException("导入数据不能为空");
        }
        // 循环调用原有保存逻辑，确保校验&入库一致
        for (OrganizationPersonSaveRequest request : requestList) {
            saveOrganizationPerson(request);
        }
        return true;
    }

    // ====================== ✅ Excel 批量导入（你要的功能） ======================
    @Override
    public void importByExcel(MultipartFile file) throws Exception {
        Workbook workbook = WorkbookFactory.create(file.getInputStream());
        Sheet sheet = workbook.getSheetAt(0);

        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row == null) continue;

            OrganizationPersonSaveRequest req = new OrganizationPersonSaveRequest();

            req.setName(getVal(row, 0));
            req.setGender(getVal(row, 1));
            req.setAge(getInt(row, 2));
            req.setEducation(getVal(row, 3));
            req.setProfessional(getVal(row, 4));
            req.setWorkingYears(getInt(row, 5));
            req.setPhone(getVal(row, 6));
            req.setDepartment(getVal(row, 7));
            req.setPostType(getVal(row, 8));
            req.setJobPosition(getVal(row, 9));
            req.setSkills(getVal(row, 10));
            req.setHasCertified("是".equals(getVal(row, 11)) ? 1 : 0);
            req.setCertifiedNo(getVal(row, 12));
            req.setGetCertifiedTime(getVal(row, 13));
            req.setCertifiedValidity(getVal(row, 14));
            req.setIssuingUnit(getVal(row, 15));
            req.setRemark(getVal(row, 16));
            req.setOrgId(1L);

            req.toRequestCheck();
            saveOrganizationPerson(req);
        }
        workbook.close();
    }

    // 工具：读取单元格字符串
    private String getVal(Row row, int idx) {
        Cell cell = row.getCell(idx);
        if (cell == null) return "";
        cell.setCellType(CellType.STRING);
        return cell.getStringCellValue().trim();
    }

    // 工具：读取数字
    private Integer getInt(Row row, int idx) {
        String val = getVal(row, idx);
        if (val.isEmpty()) return null;
        return Integer.valueOf(val);
    }
}