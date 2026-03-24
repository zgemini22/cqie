package com.zds.biz.vo.response.user;

import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@ApiModel(description = "数据源分页列表返回")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminKpiDataListResponse {

    @ApiModelPropertyCheck(value = "ID")
    private Long id;

    @ApiModelPropertyCheck(value = "数据源编号")
    private String dataCode;

    @ApiModelPropertyCheck(value = "数据源名称")
    private String dataName;

    @ApiModelPropertyCheck(value = "数据来源（1=数据库，2=接口对接）")
    private Integer dataSource;

    @ApiModelPropertyCheck(value = "数据源描述")
    private String remarks;

    @ApiModelPropertyCheck(value = "数据状态（1=启用，2=停用）")
    private Integer status;
}
