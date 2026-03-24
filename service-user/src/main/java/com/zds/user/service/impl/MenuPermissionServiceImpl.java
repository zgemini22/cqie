package com.zds.user.service.impl;


import com.zds.user.dao.TblMenuPermissionDao;
import com.zds.user.po.TblMenuPermission;
import com.zds.user.service.MenuPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuPermissionServiceImpl implements MenuPermissionService {

    @Autowired
    private TblMenuPermissionDao menuPermissionDao;


    @Override
    public List<TblMenuPermission> findPageList() {
        return menuPermissionDao.selectList(null);
    }

}
