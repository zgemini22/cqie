package com.zds.biz.vo.request.flow;

import io.swagger.annotations.ApiModel;
import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(description = "同步单位保存")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServiceOrgSaveRequest {

    @ApiModelPropertyCheck("单位ID")
    private String orgId;

    @ApiModelPropertyCheck("单位名")
    private String name;

    @ApiModelPropertyCheck("上级单位ID")
    private String parentId;
}
