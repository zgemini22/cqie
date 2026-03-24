package com.zds.user.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zds.user.po.TblRoleMenuRelation;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TblRoleMenuRelationDao extends BaseMapper<TblRoleMenuRelation> {

    /**
     * 批量新增角色菜单关联
     */
    int insertList(List<TblRoleMenuRelation> list);

    /**
     * 复制角色(模板)
     */
    int copyTemplateRole(@Param("roleId") Long roleId, @Param("roleType") String roleType);

    /**
     * 批量删除角色菜单关联
     */
    int deleteList(List<TblRoleMenuRelation> list);
}
