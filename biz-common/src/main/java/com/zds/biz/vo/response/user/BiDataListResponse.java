package com.zds.biz.vo.response.user;

import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@ApiModel(description = "数据类型列表返回")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BiDataListResponse {

    @ApiModelPropertyCheck(value = "信息ID")
    private Long id;

    @ApiModelPropertyCheck(value = "父ID")
    private Long parentId;

    @ApiModelPropertyCheck(value = "数据标识")
    private String biDataKey;

    @ApiModelPropertyCheck(value = "数据名称")
    private String biDataName;

    @ApiModelPropertyCheck(value = "子集合")
    private List<BiDataListResponse> children;

    /**
     * 平行集合转树状集合
     */
    public static List<BiDataListResponse> buildTree(List<BiDataListResponse> deptList, Long pid) {
        List<BiDataListResponse> treeList = new ArrayList<>();
        for (BiDataListResponse dept : deptList) {
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
    public static List<BiDataListResponse> buildList(List<BiDataListResponse> treeList) {
        List<BiDataListResponse> deptList = new ArrayList<>();
        for (BiDataListResponse tree : treeList) {
            if (tree.getChildren() != null && tree.getChildren().size() > 0) {
                deptList.addAll(buildList(tree.getChildren()));
            }
            tree.setChildren(new ArrayList<>());
            deptList.add(tree);
        }
        return deptList;
    }
}
