package com.zds.biz.vo.request.flow;

import io.swagger.annotations.ApiModel;
import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(description = "同步用户保存")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServiceUserSaveRequest {

    @ApiModelPropertyCheck("用户ID")
    private String userId;

    @ApiModelPropertyCheck("用户名")
    private String name;

    @ApiModelPropertyCheck("所属单位ID")
    private String orgId;
}
