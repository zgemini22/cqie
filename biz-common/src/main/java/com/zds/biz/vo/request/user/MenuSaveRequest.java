package com.zds.biz.vo.request.user;

import com.zds.biz.constant.BaseException;
import com.zds.biz.constant.user.MenuGroupEnum;
import com.zds.biz.constant.user.MenuTypeEnum;
import com.zds.biz.vo.BaseRequest;
import io.swagger.annotations.ApiModel;
import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import lombok.Data;

@Data
@ApiModel(description = "菜单保存请求")
public class MenuSaveRequest extends BaseRequest {

    @ApiModelPropertyCheck(value="菜单ID,传为修改,不传为新增", example = "1")
    private Long id;

    @ApiModelPropertyCheck(value="菜单父级ID", notes = "根目录默认0", required = true, example = "0")
    private Long parentId;

    @ApiModelPropertyCheck(value="菜单名称", required = true, example = "菜单1")
    private String menuName;

    @ApiModelPropertyCheck(value="菜单图标")
    private String fileIcon;

    @ApiModelPropertyCheck(value="排序号")
    private Integer sort;

    @ApiModelPropertyCheck(value="菜单地址")
    private String url;

    @ApiModelPropertyCheck("按钮标识")
    private String buttonUrl;

    @ApiModelPropertyCheck(value="菜单类型,字典group_id=MENU_TYPE", required = true)
    private String menuType;

    @ApiModelPropertyCheck(value="菜单分组,字典group_id=MENU_GROUP", required = true)
    private String menuGroup;

    @ApiModelPropertyCheck("选中菜单")
    private String activeMenu;

    @ApiModelPropertyCheck("组件")
    private String component;

    @ApiModelPropertyCheck(value = "可见权限,1:不限制,2:政府可见,3:企业可见")
    private Integer menuPower;

    @Override
    public void toRequestCheck() {
        if (parentId == null || parentId < 0) {
            parentId = 0L;
        }
        if (fileIcon == null) {
            fileIcon = "";
        }
        if (MenuTypeEnum.query(menuType) == null) {
            throw new BaseException("菜单类型值不在允许范围");
        }
        if (MenuGroupEnum.query(menuGroup) == null) {
            throw new BaseException("菜单分组值不在允许范围");
        }
        if (menuPower == null) {
            menuPower = 1;
        }
    }
}
