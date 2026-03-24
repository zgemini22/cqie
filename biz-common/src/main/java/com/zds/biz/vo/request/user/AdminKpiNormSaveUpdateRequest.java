package com.zds.biz.vo.request.user;

import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(description = "指标类别保存修改请求")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminKpiNormSaveUpdateRequest {

    @ApiModelProperty(value = "ID(不传新增,传为修改)")
    private Long id;

    @ApiModelPropertyCheck(value = "父级ID,默认为0",required = true)
    private Long parentId = 0L;

    @ApiModelPropertyCheck(value = "类别名称",required = true)
    private String classifyName;
}
