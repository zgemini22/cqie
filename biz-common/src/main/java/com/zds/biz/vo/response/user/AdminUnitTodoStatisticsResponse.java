package com.zds.biz.vo.response.user;

import io.swagger.annotations.ApiModel;
import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@ApiModel(description = "部门审批统计返回")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminUnitTodoStatisticsResponse {

    @ApiModelPropertyCheck("部门id")
    private String unitId;

    @ApiModelPropertyCheck("部门名称")
    private String unitName;

    @ApiModelPropertyCheck("审批次数")
    private Integer approvalNumber;

    @ApiModelPropertyCheck("超时次数")
    private Integer outTimeNumber;

    @ApiModelPropertyCheck("建设审批详情")
    private List<AdminGoudyStreetDetailResponse> detail;
}
