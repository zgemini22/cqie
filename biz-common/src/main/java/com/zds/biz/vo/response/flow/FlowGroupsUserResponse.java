package com.zds.biz.vo.response.flow;

import io.swagger.annotations.ApiModel;
import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(description = "查询组下用户返回")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FlowGroupsUserResponse {
    @ApiModelPropertyCheck("用户id")
    private String id;

    @ApiModelPropertyCheck("用户姓名")
    private String name;
}
