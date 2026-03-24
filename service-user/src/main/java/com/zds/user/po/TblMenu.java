package com.zds.user.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
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
@TableName("tbl_menu")
@ApiModel(value = "TblMenu对象", description = "菜单信息")
public class TblMenu implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelPropertyCheck(value = "菜单ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelPropertyCheck(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;

    @ApiModelPropertyCheck(value = "创建人")
    private Long createId;

    @ApiModelPropertyCheck(value = "修改时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date updateTime;

    @ApiModelPropertyCheck(value = "修改人")
    private Long updateId;

    @ApiModelPropertyCheck(value = "是否删除")
    private Boolean deleted;

    @ApiModelPropertyCheck(value = "父ID")
    private Long parentId;

    @ApiModelPropertyCheck(value = "菜单名称")
    private String menuName;

    @ApiModelPropertyCheck(value = "菜单图标")
    private String fileIcon;

    @ApiModelPropertyCheck(value = "排序号")
    private Integer sort;

    @ApiModelPropertyCheck(value = "菜单地址")
    private String url;

    @ApiModelPropertyCheck(value = "按钮标识")
    private String buttonUrl;

    @ApiModelPropertyCheck(value = "菜单类型,字典group_id=MENU_TYPE")
    private String menuType;

    @ApiModelPropertyCheck(value = "菜单状态,字典group_id=MENU_STATUS")
    private String menuStatus;

    @ApiModelPropertyCheck(value = "菜单分组,字典group_id=MENU_GROUP")
    private String menuGroup;

    @ApiModelPropertyCheck(value = "选中菜单")
    private String activeMenu;

    @ApiModelPropertyCheck(value = "组件")
    private String component;

    @ApiModelPropertyCheck(value = "可见权限,1:不限制,2:政府可见,3:企业可见")
    private Integer menuPower;

    public static LambdaQueryWrapper<TblMenu> getWrapper() {
        return new LambdaQueryWrapper<>();
    }
}
