package com.zds.user.po;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import io.swagger.annotations.ApiModel;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonFormat;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("tbl_role_menu_relation")
@ApiModel(value = "TblRoleMenuRelation对象", description = "角色菜单关联")
public class TblRoleMenuRelation implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelPropertyCheck(value = "角色ID")
    private Long roleId;

    @ApiModelPropertyCheck(value = "菜单ID")
    private Long menuId;

    public static LambdaQueryWrapper<TblRoleMenuRelation> getWrapper() {
        return new LambdaQueryWrapper<>();
    }
}
