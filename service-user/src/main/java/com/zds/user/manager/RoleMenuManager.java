package com.zds.user.manager;

import com.zds.biz.constant.user.MessageSourceEnum;
import com.zds.biz.constant.user.UserTodoListEnum;
import com.zds.biz.vo.response.user.MenuListResponse;
import com.zds.user.po.TblRole;

import java.util.List;
import java.util.Map;

public interface RoleMenuManager {
    /**
     * 查询角色可见菜单
     */
    List<MenuListResponse> findMenusByRoleId(Long roleId);

    /**
     * 获取角色map
     * @param organizationId 组织ID
     * @return 角色ID/角色名称
     */
    Map<Long, String> getRoleMap(Long organizationId);

    /**
     * 查询角色map
     */
    Map<Long, TblRole> getRoleMap(List<Long> ids);

    /**
     * 计算指定组织的待删除菜单id集合
     * @param roleId 组织管理员角色ID
     * @param menuIds 组织管理员的新菜单集合
     * @return 待删除的菜单id集合
     */
    List<Long> getDeleteMenuIdList(Long roleId, List<Long> menuIds);

    /**
     * 查询指定单位可见指定菜单的用户集合(消息来源)
     */
    List<Long> getUserIdsByMessageSource(Long organizationId, MessageSourceEnum messageSourceEnum);

    /**
     * 查询指定单位可见指定菜单的用户集合(待办类型)
     */
    List<Long> getUserIdsByTodo(Long organizationId, UserTodoListEnum userTodoListEnum);
}
