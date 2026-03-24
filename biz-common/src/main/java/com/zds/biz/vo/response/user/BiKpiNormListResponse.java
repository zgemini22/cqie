package com.zds.biz.vo.response.user;

import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@ApiModel(description = "指标类别列表树形返回")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BiKpiNormListResponse {

    @ApiModelProperty(value = "ID")
    private Long id;

    @ApiModelProperty(value = "父级ID")
    private Long parentId;

    @ApiModelProperty(value = "分类名称")
    private String classifyName;

    @ApiModelPropertyCheck(value = "层级")
    private Integer levelNum;

    @ApiModelPropertyCheck(value = "指标集合")
    private List<BiKpiNormInfoResponse> kpiNormInfoList;

    @ApiModelProperty(value = "子级类别")
    private List<BiKpiNormListResponse> children;

    /**
     * 平行集合转树状集合
     */
    public static List<BiKpiNormListResponse> buildTree(List<BiKpiNormListResponse> classifyList, Long pid) {
        List<BiKpiNormListResponse> treeList = new ArrayList();
        for (BiKpiNormListResponse classify : classifyList) {
            if (classify.getParentId().equals(pid)) {
                classify.setClassifyName(classify.getClassifyName());
                classify.setLevelNum(classify.getLevelNum());
                classify.setChildren(buildTree(classifyList, classify.getId()));
                treeList.add(classify);
            }
        }
        return treeList;
    }

}
