package com.zds.user.manager.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zds.biz.constant.BaseException;
import com.zds.biz.constant.PermissionEnum;
import com.zds.biz.constant.user.MenuStatusEnum;
import com.zds.biz.util.ThreadLocalUtil;
import com.zds.user.dao.TblMenuDao;
import com.zds.user.dao.TblRoleMenuRelationDao;
import com.zds.user.manager.CheckUserPermissionManager;
import com.zds.user.po.TblMenu;
import com.zds.user.po.TblRoleMenuRelation;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CheckUserPermissionManagerImpl implements CheckUserPermissionManager {

    @Autowired
    private ThreadLocalUtil threadLocalUtil;

    @Autowired
    private TblMenuDao tblMenuDao;

    @Autowired
    private TblRoleMenuRelationDao tblRoleMenuRelationDao;

    @Override
    public void checkUserPermission(PermissionEnum permissionEnum) {
        Long roleId = threadLocalUtil.getRoleId();
        LambdaQueryWrapper<TblRoleMenuRelation> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(TblRoleMenuRelation::getRoleId, roleId);
        List<Long> menuIds = tblRoleMenuRelationDao.selectList(wrapper).stream().map(TblRoleMenuRelation::getMenuId).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(menuIds)) {
            throw new BaseException("权限不足");
        }
        LambdaQueryWrapper<TblMenu> menuWrapper = new LambdaQueryWrapper<>();
        menuWrapper.in(TblMenu::getId, menuIds);
        menuWrapper.eq(TblMenu::getDeleted,false);
        menuWrapper.eq(TblMenu::getButtonUrl,permissionEnum.getTitle());
        menuWrapper.eq(TblMenu::getMenuStatus,MenuStatusEnum.ENABLE.getKey());
        List<TblMenu> tblMenus = tblMenuDao.selectList(menuWrapper);
        if (CollectionUtils.isEmpty(tblMenus)) {
            throw new BaseException("权限不足");
        }
    }
}
