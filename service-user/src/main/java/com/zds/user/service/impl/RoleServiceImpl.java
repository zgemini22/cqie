package com.zds.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zds.biz.constant.BaseException;
import com.zds.biz.constant.PermissionEnum;
import com.zds.biz.constant.user.*;
import com.zds.biz.util.ThreadLocalUtil;
import com.zds.biz.vo.SelectResponse;
import com.zds.biz.vo.request.user.*;
import com.zds.biz.vo.response.user.MenuListResponse;
import com.zds.biz.vo.response.user.RoleDetailResponse;
import com.zds.biz.vo.response.user.RoleResponse;
import com.zds.user.dao.*;
import com.zds.user.manager.CheckUserPermissionManager;
import com.zds.user.manager.OrgManager;
import com.zds.user.manager.RoleMenuManager;
import com.zds.user.po.*;
import com.zds.user.service.RoleService;
import com.zds.user.service.TokenService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private TblRoleDao roleDao;

    @Autowired
    private TblRoleMenuRelationDao roleMenuRelationDao;

    @Autowired
    private TblMenuDao menuDao;

    @Autowired
    private TblUserDao userDao;

    @Autowired
    private TblOrganizationDao organizationDao;

    @Autowired
    private ThreadLocalUtil threadLocalUtil;

    @Autowired
    private RoleMenuManager roleMenuManager;

    @Autowired
    private OrgManager orgManager;

    @Autowired
    private CheckUserPermissionManager checkUserPermissionManager;

    @Autowired
    private TokenService tokenService;

    @Override
    public boolean saveRole(RoleSaveRequest request) {
        //判断用户是否拥有权限
        checkUserPermissionManager.checkUserPermission(PermissionEnum.ROLE_SAVE_UPDATE);
        //检查入参
        request.toRequestCheck();
        //检查角色类型
        checkRoleType(request.getId());
        //检查角色名是否已存在
        LambdaQueryWrapper<TblRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(TblRole::getCreateTime)
                .eq(TblRole::getDeleted, false)
                .eq(TblRole::getOrganizationId, request.getOrganizationId())
                .eq(TblRole::getRoleName, request.getRoleName())
                .ne(request.getId() != null, TblRole::getId, request.getId());
        List<TblRole> list = roleDao.selectList(wrapper);
        if (list != null && list.size() > 0) {
            throw new BaseException("角色名称已存在");
        }
        //保存角色信息
        boolean addFlag = request.getId() == null;
        TblRole record = new TblRole();
        BeanUtils.copyProperties(request, record);
        if (addFlag) {
            if (StringUtils.isEmpty(record.getRoleStatus())) {
                record.setRoleStatus(RoleStatusEnum.ENABLE.getKey());
            }
            record.setRoleType(RoleTypeEnum.OTHER.getKey());
        }
        int count = addFlag ? roleDao.insert(record) : roleDao.updateById(record);
        //删除旧角色菜单关联信息
        if (!addFlag && count == 1) {
            LambdaQueryWrapper<TblRoleMenuRelation> delWrapper = new LambdaQueryWrapper<>();
            delWrapper.eq(TblRoleMenuRelation::getRoleId, record.getId());
            roleMenuRelationDao.delete(delWrapper);
        }
        //有传菜单集合
        if (request.getMenuIds() != null && request.getMenuIds().size() > 0) {
            //新增角色菜单关联信息
            List<TblRoleMenuRelation> relationList = request.getMenuIds().stream()
                    .map(x -> TblRoleMenuRelation.builder()
                            .roleId(record.getId())
                            .menuId(x)
                            .build())
                    .collect(Collectors.toList());
            roleMenuRelationDao.insertList(relationList);
        }
        if (!addFlag) {
            clearUserTokenCache(List.of(record.getId()));
        }
        return count == 1;
    }

    private void clearUserTokenCache(List<Long> roleIds) {
        if (roleIds == null || roleIds.isEmpty()) {
            return;
        }
        LambdaQueryWrapper<TblUser> userWrapper = new LambdaQueryWrapper<>();
        userWrapper.in(TblUser::getRoleId, roleIds);
        userWrapper.eq(TblUser::getDeleted, false);
        List<TblUser> users = userDao.selectList(userWrapper);
        if (users != null && !users.isEmpty()) {
            List<Long> userIds = users.stream().map(TblUser::getId).collect(Collectors.toList());
            tokenService.deleteToken(userIds);
        }
    }

    @Override
    public RoleDetailResponse findDetail(Long roleId) {
        RoleDetailResponse detail = new RoleDetailResponse();
        if (roleId != null) {
            TblRole role = roleDao.selectById(roleId);
            if (role != null) {
                BeanUtils.copyProperties(role, detail);
                detail.setMenus(roleMenuManager.findMenusByRoleId(roleId));
                if (threadLocalUtil.getOrganizationId().equals(OrganizationNodeEnum.ROOT_COMPANY_CODE.getCode())) {
                    detail.setOrganizationName(OrganizationNodeEnum.ROOT_COMPANY_CODE.getName());
                } else {
                    detail.setOrganizationName(organizationDao.selectById(detail.getOrganizationId()).getOrganizationName());
                }
            }
        }
        return detail;
    }

    @Override
    public boolean deleteById(Long roleId) {
        checkRoleType(roleId, true);
        boolean flag = false;
        if (roleId != null) {
            //检查是否有用户绑定此角色
            LambdaQueryWrapper<TblUser> userWrapper = new LambdaQueryWrapper<>();
            userWrapper.eq(TblUser::getDeleted, false)
                    .eq(TblUser::getRoleId, roleId);
            List<TblUser> list = userDao.selectList(userWrapper);
            if (list != null && list.size() > 0) {
                throw new BaseException("角色存在绑定的用户，不允许删除");
            }
            //删除角色
            flag = roleDao.updateById(TblRole.builder()
                    .id(roleId)
                    .deleted(true)
                    .build()) == 1;
        }
        return flag;
    }

    @Override
    public IPage<RoleResponse> findRolePageList(RolePageListRequest request) {
        //检查入参
        request.toRequestCheck();
        //分页
        LambdaQueryWrapper<TblRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(TblRole::getId)
                .eq(TblRole::getDeleted, false);
        wrapper.like(StringUtils.isNotEmpty(request.getRoleName()), TblRole::getRoleName, request.getRoleName());
        wrapper.like(StringUtils.isNotEmpty(request.getRemarks()), TblRole::getRemarks, request.getRemarks());
        wrapper.eq(RoleStatusEnum.query(request.getRoleStatus()) != null, TblRole::getRoleStatus, request.getRoleStatus());
        wrapper.eq(request.getOrganizationId() != null, TblRole::getOrganizationId, request.getOrganizationId());
        //非系统管理组,只能查看本单位及下属单位的角色
        List<Long> orgIds = orgManager.findChildOrg(threadLocalUtil.getOrganizationId());
        wrapper.in(orgIds != null, TblRole::getOrganizationId, orgIds);
        Page<TblRole> list = roleDao.selectPage(new Page<>(request.getPageNum(), request.getPageSize()), wrapper);
        Map<Long, String> orgMap = orgManager.getOrgMap(list.getRecords().stream().map(TblRole::getOrganizationId).distinct().collect(Collectors.toList()));
        return list.convert(po -> {
            RoleResponse vo = new RoleResponse();
            BeanUtils.copyProperties(po, vo);
            if (po.getOrganizationId().equals(OrganizationNodeEnum.ROOT_COMPANY_CODE.getCode())) {
                vo.setOrganizationName(OrganizationNodeEnum.ROOT_COMPANY_CODE.getName());
            } else {
                vo.setOrganizationName(orgMap.getOrDefault(vo.getOrganizationId(), ""));
            }
            return vo;
        });
    }

    @Override
    public boolean updateStatus(RoleStatusRequest request) {
        //检查入参
        request.toRequestCheck();
        //检查角色类型
        checkRoleType(request.getId());
        int count = roleDao.updateById(TblRole.builder()
                .id(request.getId())
                .roleStatus(request.getRoleStatus())
                .build());
        return count == 1;
    }

    /**
     * 检查角色类型
     */
    private TblRole checkRoleType(Long id) {
        return checkRoleType(id, false);
    }

    /**
     * 检查角色类型
     */
    private TblRole checkRoleType(Long id, boolean isDelete) {
        if (id != null && id != 0) {
            TblRole po = roleDao.selectById(id);
            if (isDelete && !po.getRoleType().equals(RoleTypeEnum.OTHER.getKey())) {
                throw new BaseException("指定角色不允许删除");
            } else if (!threadLocalUtil.getOrganizationId().equals(OrganizationNodeEnum.ROOT_COMPANY_CODE.getCode())
                    && !isDelete && !po.getRoleType().equals(RoleTypeEnum.OTHER.getKey())) {
                throw new BaseException("指定角色不允许变更");
            } else if (threadLocalUtil.getOrganizationId().equals(OrganizationNodeEnum.ROOT_COMPANY_CODE.getCode())
                    && po.getRoleType().equals(RoleTypeEnum.SYSTEM_ADMIN.getKey())) {
//                throw new BaseException("指定角色不允许变更");
            }
            return po;
        }
        return null;
    }

    @Override
    public List<SelectResponse> selectList(RoleSelectRequest request) {
        //非系统管理组,只能查看本单位及下属单位的角色
        List<Long> orgIds = orgManager.findChildOrg(threadLocalUtil.getOrganizationId());
        //下拉列表只返回其它角色
        LambdaQueryWrapper<TblRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(TblRole::getCreateTime)
                .eq(request.getOrganizationId() != null, TblRole::getOrganizationId, request.getOrganizationId())
                .eq(TblRole::getRoleType, RoleTypeEnum.OTHER.getKey())
                .eq(TblRole::getDeleted, false)
                .in(orgIds != null, TblRole::getOrganizationId, orgIds);
        return roleDao.selectList(wrapper).stream()
                .map(x -> SelectResponse.builder()
                        .id(x.getId())
                        .name(x.getRoleName())
                        .status(x.getRoleStatus().equals(RoleStatusEnum.ENABLE.getKey()) ? 1 : 0)
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public boolean grantRole(RoleGrantRequest request) {
        //检查入参
        request.toRequestCheck();
        //检查角色类型
        checkRoleType(request.getId());
        //删除旧角色菜单关联信息
        roleMenuRelationDao.delete(TblRoleMenuRelation.getWrapper().eq(TblRoleMenuRelation::getRoleId, request.getId()));
        //有传菜单集合
        if (request.getMenuIds() != null && request.getMenuIds().size() > 0) {
            //新增角色菜单关联信息
            List<TblRoleMenuRelation> relationList = request.getMenuIds().stream()
                    .map(x -> TblRoleMenuRelation.builder()
                            .roleId(request.getId())
                            .menuId(x)
                            .build())
                    .collect(Collectors.toList());
            roleMenuRelationDao.insertList(relationList);
        }
        return true;
    }

    @Override
    public List<MenuListResponse> findVisibleMenu(Long organizationId) {
        List<MenuListResponse> data;
        if (threadLocalUtil.getUserType().equals(UserTypeEnum.SYSTEM_ADMIN.getKey())) {
            LambdaQueryWrapper<TblMenu> wrapper = new LambdaQueryWrapper<>();
            wrapper.orderByDesc(TblMenu::getSort)
                    .eq(TblMenu::getDeleted, false)
                    .eq(TblMenu::getMenuStatus, MenuStatusEnum.ENABLE.getKey());
            if (organizationId != null) {
                TblOrganization organization = organizationDao.selectById(organizationId);
                List<Integer> powers = new ArrayList<>();
                powers.add(1);
                if (organization.getOrganizationType().equals(OrganizationTypeEnum.GOVERNMENT.getKey())) {
                    powers.add(2);
                } else if (organization.getOrganizationType().equals(OrganizationTypeEnum.COMPANY.getKey())) {
                    powers.add(3);
                }
                wrapper.in(TblMenu::getMenuPower, powers);
            }
            Map<Long, MenuListResponse> map = new HashMap<>();
            data = menuDao.selectList(wrapper).stream()
                    .map(po -> {
                        MenuListResponse vo = new MenuListResponse();
                        BeanUtils.copyProperties(po, vo);
                        map.put(vo.getId(), vo);
                        return vo;
                    })
                    .collect(Collectors.toList());
            data = MenuListResponse.buildList(MenuListResponse.buildTree(data, 0L));
        } else {
            Long roleId = threadLocalUtil.getRoleId();
            data = roleMenuManager.findMenusByRoleId(roleId);
        }
        return data;
    }
}
