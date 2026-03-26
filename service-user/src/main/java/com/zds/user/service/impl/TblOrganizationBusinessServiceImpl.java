package com.zds.user.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zds.biz.util.ThreadLocalUtil;
import com.zds.biz.vo.request.user.TblOrganizationBusinessRequest;
import com.zds.biz.vo.response.user.TblOrganizationBusinessResponse;
import com.zds.user.dao.TblOrganizationBusinessDao;
import com.zds.user.po.TblOrganizationBusiness;
import com.zds.user.service.TblOrganizationBusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TblOrganizationBusinessServiceImpl implements TblOrganizationBusinessService {

    @Autowired
    private ThreadLocalUtil threadLocalUtil;

    @Autowired
    private TblOrganizationBusinessDao tblOrganizationBusinessDao;

    @Override
    public boolean save(TblOrganizationBusinessRequest saveRequest) {
        TblOrganizationBusiness tblOrganizationBusiness = new TblOrganizationBusiness();
        BeanUtil.copyProperties(saveRequest, tblOrganizationBusiness);
        tblOrganizationBusiness.setCreateId(threadLocalUtil.getUserId());
        tblOrganizationBusiness.setUpdateId(threadLocalUtil.getUserId());
        tblOrganizationBusiness.setDeleted(0);
        return tblOrganizationBusinessDao.insert(tblOrganizationBusiness) > 0;
    }

    @Override
    public boolean edit(TblOrganizationBusinessRequest updateRequest) {
        TblOrganizationBusiness tblOrganizationBusiness = new TblOrganizationBusiness();
        BeanUtil.copyProperties(updateRequest, tblOrganizationBusiness);
        tblOrganizationBusiness.setUpdateId(threadLocalUtil.getUserId());
        return tblOrganizationBusinessDao.updateById(tblOrganizationBusiness) > 0;
    }

    @Override
    public boolean deleteById(Long id) {
        TblOrganizationBusiness tblOrganizationBusiness = new TblOrganizationBusiness();
        tblOrganizationBusiness.setId(id);
        tblOrganizationBusiness.setDeleted(1);
        tblOrganizationBusiness.setUpdateId(threadLocalUtil.getUserId());
        return tblOrganizationBusinessDao.updateById(tblOrganizationBusiness) > 0;
    }

    @Override
    public TblOrganizationBusinessResponse detail(Long id) {
        return tblOrganizationBusinessDao.selectDetailById(id);
    }

    @Override
    public IPage<TblOrganizationBusinessResponse> list(TblOrganizationBusinessRequest request) {
        return tblOrganizationBusinessDao.selectPageWithCondition(new Page<>(request.getPageNum(), request.getPageSize()), request);
    }

}