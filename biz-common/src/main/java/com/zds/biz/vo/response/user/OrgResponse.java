package com.zds.biz.vo.response.user;

import io.swagger.annotations.ApiModel;
import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(description = "单位列表返回")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrgResponse {

    @ApiModelPropertyCheck(value="单位ID")
    private Long id;

    @ApiModelPropertyCheck(value="单位名称")
    private String organizationName;

    @ApiModelPropertyCheck(value="单位类别,字典group_id=ORGANIZATION_TYPE")
    private String organizationType;

    @ApiModelPropertyCheck(value="单位状态,字典group_id=ORGANIZATION_STATUS")
    private String organizationStatus;

    @ApiModelPropertyCheck(value="父级ID")
    private Long parentId;
}
