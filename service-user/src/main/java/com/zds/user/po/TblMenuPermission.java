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
@TableName("tbl_menu_permission")
@ApiModel(value = "TblMenuPermission对象", description = "菜单详情表")
public class TblMenuPermission implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelPropertyCheck(value = "菜单ID")
    private Long menuId;

    @ApiModelPropertyCheck(value = "权限名")
    private String permissionName;

    @ApiModelPropertyCheck(value = "权限枚举")
    private String permissionEnum;

    public static LambdaQueryWrapper<TblMenuPermission> getWrapper() {
        return new LambdaQueryWrapper<>();
    }
}
