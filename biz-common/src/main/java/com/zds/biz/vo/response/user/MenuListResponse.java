package com.zds.biz.vo.response.user;

import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@ApiModel(description = "菜单列表返回")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MenuListResponse {

    @ApiModelPropertyCheck(value="菜单ID")
    private Long id;

    @ApiModelPropertyCheck(value="菜单父级ID")
    private Long parentId;

    @ApiModelPropertyCheck(value="菜单名称")
    private String menuName;

    @ApiModelPropertyCheck(value="菜单图标")
    private String fileIcon;

    @ApiModelPropertyCheck(value="排序号")
    private Integer sort;

    @ApiModelPropertyCheck(value="菜单地址")
    private String url;

    @ApiModelPropertyCheck("按钮标识")
    private String buttonUrl;

    @ApiModelPropertyCheck(value="菜单类型,字典group_id=MENU_TYPE")
    private String menuType;

    @ApiModelPropertyCheck(value="菜单分组,字典group_id=MENU_GROUP")
    private String menuGroup;

    @ApiModelPropertyCheck("选中菜单")
    private String activeMenu;

    @ApiModelPropertyCheck("组件")
    private String component;

    @ApiModelPropertyCheck(value = "可见权限,1:不限制,2:政府可见,3:企业可见")
    private Integer menuPower;

    @ApiModelPropertyCheck(value="子菜单集合")
    private List<MenuListResponse> children;

    /**
     * 平行集合转树状集合
     */
    public static List<MenuListResponse> buildTree(List<MenuListResponse> deptList, Long pid){
        List<MenuListResponse> treeList = new ArrayList<>();
        for (MenuListResponse dept : deptList) {
            if (dept.getParentId().equals(pid)) {
                dept.setChildren(buildTree(deptList, dept.getId()));
                treeList.add(dept);
            }
        }
        return treeList;
    }

    /**
     * 树状集合转平行集合
     */
    public static List<MenuListResponse> buildList(List<MenuListResponse> treeList){
        List<MenuListResponse> deptList = new ArrayList<>();
        for (MenuListResponse tree : treeList) {
            if (tree.getChildren() != null && tree.getChildren().size() > 0) {
                deptList.addAll(buildList(tree.getChildren()));
            }
            tree.setChildren(new ArrayList<>());
            deptList.add(tree);
        }
        return deptList;
    }
}
