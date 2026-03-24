package com.zds.biz.vo.response.user;

import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@ApiModel(description = "节点审批统计返回")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminPointStatisticsResponse {

    @ApiModelPropertyCheck("节点名称")
    private String pointName;

    @ApiModelPropertyCheck("审批次数")
    private Integer approvalNumber;

    @ApiModelPropertyCheck("超时次数")
    private Integer outTimeNumber;

    @ApiModelPropertyCheck("镇街分别次数")
    private List<AdminGoudyStreetResponse> list;

}
