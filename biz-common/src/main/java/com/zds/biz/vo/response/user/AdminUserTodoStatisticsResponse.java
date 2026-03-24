package com.zds.biz.vo.response.user;

import io.swagger.annotations.ApiModel;
import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(description = "当前用户待办统计返回")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminUserTodoStatisticsResponse {

    @ApiModelPropertyCheck("待办总数")
    private Integer waitTotal;

    @ApiModelPropertyCheck("已办总数")
    private Integer completedTotal;

    @ApiModelPropertyCheck("今日已办")
    private Integer todayUsed;
}
