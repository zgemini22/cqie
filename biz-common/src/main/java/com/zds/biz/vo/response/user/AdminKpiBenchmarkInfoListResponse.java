package com.zds.biz.vo.response.user;

import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@ApiModel(description = "标杆管理分页列表返回")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminKpiBenchmarkInfoListResponse {

    @ApiModelPropertyCheck(value = "ID")
    private Long id;

    @ApiModelPropertyCheck(value = "指标编号")
    private String normCode;

    @ApiModelPropertyCheck(value = "指标名称")
    private String normName;

    @ApiModelPropertyCheck(value = "标杆类型（1=自动维护，2=手动维护）")
    private Integer benchmarkType;

    @ApiModelPropertyCheck(value = "标杆描述")
    private String benchmarkRemarks;

    @ApiModelPropertyCheck(value = "状态（1=启用，2=停用）")
    private Integer status;
}
