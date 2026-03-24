package com.zds.biz.vo.response.flow;

import io.swagger.annotations.ApiModel;
import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@ApiModel(description = "查询流程当前审核部门返回")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FlowProcessUnitResponse {
    @ApiModelPropertyCheck(value = "流程是否完结")
    private Boolean complete;

    @ApiModelPropertyCheck(value = "部门名称")
    private List<String> name;
}
