package com.zds.biz.vo.response.flow;

import io.swagger.annotations.ApiModel;
import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(description = "查询所有组返回")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FlowGroupListResponse {
    @ApiModelPropertyCheck("部门ID")
    private String Id;

    @ApiModelPropertyCheck("父级ID")
    private String parentId;

    @ApiModelPropertyCheck("部门名称")
    private String name;
}
