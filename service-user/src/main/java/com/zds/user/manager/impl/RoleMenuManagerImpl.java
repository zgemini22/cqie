package com.zds.user.manager.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zds.biz.constant.user.MenuStatusEnum;
import com.zds.biz.constant.user.MessageSourceEnum;
import com.zds.biz.constant.user.OrganizationTypeEnum;
import com.zds.biz.constant.user.UserTodoListEnum;
import com.zds.biz.vo.response.user.MenuListResponse;
import com.zds.user.dao.*;
import com.zds.user.manager.RoleMenuManager;
import com.zds.user.po.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RoleMenuManagerImpl implements RoleMenuManager {

    @Autowired
    private TblMenuDao menuDao;

    @Autowired
    private TblRoleDao roleDao;

    @Autowired
    private TblRoleMenuRelationDao roleMenuRelationDao;

    @Autowired
    private TblOrganizationDao organizationDao;

    @Autowired
    private TblUserDao userDao;

    @Autowired
    private TblTodoMenuRelationDao todoMenuRelationDao;

    /**
     * 待办、消息是否推送整个组织
     */
    @Value("${msg-push.isopen:true}")
    private boolean isopen = true;

    @Override
    public List<MenuListResponse> findMenusByRoleId(Long roleId) {
        //查询角色菜单关联
        LambdaQueryWrapper<TblRoleMenuRelation> r_wrapper = new LambdaQueryWrapper<>();
        r_wrapper.eq(TblRoleMenuRelation::getRoleId, roleId);
        List<TblRoleMenuRelation> relationList = roleMenuRelationDao.selectList(r_wrapper);
        if (relationList.size() > 0) {
            //查询角色信息
            LambdaQueryWrapper<TblMenu> m_wrapper = new LambdaQueryWrapper<>();
            m_wrapper.orderByDesc(TblMenu::getSort)
                    .eq(TblMenu::getDeleted, false)
                    .eq(TblMenu::getMenuStatus, MenuStatusEnum.ENABLE.getKey())
                    .in(TblMenu::getId, relationList.stream().map(TblRoleMenuRelation::getMenuId).collect(Collectors.toList()));
            TblRole role = roleDao.selectById(roleId);
            if (role.getOrganizationId() != 0L) {
                TblOrganization organization = organizationDao.selectById(role.getOrganizationId());
                List<Integer> powers = new ArrayList<>();
                powers.add(1);
                if (organization.getOrganizationType().equals(OrganizationTypeEnum.GOVERNMENT.getKey())) {
                    powers.add(2);
                } else if (organization.getOrganizationType().equals(OrganizationTypeEnum.COMPANY.getKey())) {
                    powers.add(3);
                }
                m_wrapper.in(TblMenu::getMenuPower, powers);
            }
            List<TblMenu> menuList = menuDao.selectList(m_wrapper);
            //构建数据
            List<MenuListResponse> data = menuList.stream()
                    .map(po -> {
                        MenuListResponse vo = new MenuListResponse();
                        BeanUtils.copyProperties(po, vo);
                        return vo;
                    })
                    .collect(Collectors.toList());
            return MenuListResponse.buildList(MenuListResponse.buildTree(data, 0L));
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    public Map<Long, String> getRoleMap(Long organizationId) {
        LambdaQueryWrapper<TblRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(TblRole::getCreateTime)
                .eq(TblRole::getOrganizationId, organizationId);
        List<TblRole> list = roleDao.selectList(wrapper);
        return list.stream()
                .collect(Collectors.toMap(TblRole::getId, TblRole::getRoleName, (key1 , key2)-> key2 ));
    }

    @Override
    public Map<Long, TblRole> getRoleMap(List<Long> ids) {
        Map<Long, TblRole> map = new HashMap<>();
        if (ids.size() > 0) {
            map = roleDao.selectBatchIds(ids).stream()
                    .collect(Collectors.toMap(TblRole::getId, x -> x, (a, b) -> b));
        }
        return map;
    }

    @Override
    public List<Long> getDeleteMenuIdList(Long roleId, List<Long> menuIds) {
        LambdaQueryWrapper<TblRoleMenuRelation> r_wrapper = new LambdaQueryWrapper<>();
        r_wrapper.eq(TblRoleMenuRelation::getRoleId, roleId);
        List<TblRoleMenuRelation> relationList = roleMenuRelationDao.selectList(r_wrapper);
        List<Long> list = new ArrayList<>();
        if (menuIds == null || menuIds.size() == 0) {
            list = relationList.stream().map(TblRoleMenuRelation::getMenuId).collect(Collectors.toList());
        } else {
            Map<Long, Long> map = menuIds.stream().collect(Collectors.toMap(x -> x, x -> x, (a,b) -> b));
            for (TblRoleMenuRelation relation : relationList) {
                if (!map.containsKey(relation.getMenuId())) {
                    list.add(relation.getMenuId());
                }
            }
        }
        return list;
    }

    @Override
    public List<Long> getUserIdsByMessageSource(Long organizationId, MessageSourceEnum messageSourceEnum) {
        return getUserIdsByWork(organizationId, messageSourceEnum.getKey());
    }

    @Override
    public List<Long> getUserIdsByTodo(Long organizationId, UserTodoListEnum userTodoListEnum) {
        return getUserIdsByWork(organizationId, userTodoListEnum.getKey());
    }

    private List<Long> getUserIdsByWork(Long organizationId, String workEnum) {
        //默认推送整个组织
        LambdaQueryWrapper<TblUser> wrapper = TblUser.getWrapper()
                .eq(TblUser::getDeleted, false)
                .eq(TblUser::getOrganizationId, organizationId);
        if (!isopen) {
            //查询指定单位可见指定菜单的用户集合
            List<Long> roleIds = getRoleIdsByWork(workEnum);
            wrapper.in(roleIds.size() > 0, TblUser::getRoleId, roleIds);
        }
        List<TblUser> userList = userDao.selectList(wrapper);
        return userList.stream().map(TblUser::getId).collect(Collectors.toList());
    }

    private List<Long> getRoleIdsByWork(String workEnum) {
        List<Long> roleIds = new ArrayList<>();
        TblTodoMenuRelation relation = todoMenuRelationDao.selectOne(TblTodoMenuRelation.getWrapper().eq(TblTodoMenuRelation::getWorkEnum, workEnum).last("limit 1"));
        if (relation != null && StringUtils.isNotEmpty(relation.getMenuIds())) {
            List<Long> menuIds = Arrays.stream(relation.getMenuIds().split(",")).map(Long::valueOf).collect(Collectors.toList());
            List<TblRoleMenuRelation> roleMenuRelations = roleMenuRelationDao.selectList(TblRoleMenuRelation.getWrapper().in(TblRoleMenuRelation::getMenuId, menuIds));
            Map<Long, List<TblRoleMenuRelation>> map = roleMenuRelations.stream().collect(Collectors.groupingBy(TblRoleMenuRelation::getRoleId));
            for (Long roleId : map.keySet()) {
                Map<Long, Long> itemMap = map.get(roleId).stream()
                        .collect(Collectors.toMap(TblRoleMenuRelation::getMenuId, TblRoleMenuRelation::getMenuId, (a, b) -> b));
                int count = 0;
                for (Long menuId : menuIds) {
                    if (itemMap.containsKey(menuId)) {
                        count++;
                    }
                }
                if (count == menuIds.size()) {
                    roleIds.add(roleId);
                }
            }
        }
        return roleIds;
    }
}
