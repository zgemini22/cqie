package com.zds.biz.vo.response.user;

import io.swagger.annotations.ApiModel;
import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(value = "计算事务时间状态返回")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkCalendarResponse {

    @ApiModelPropertyCheck(value = "事务时间状态,1:正常完成,2:提前完成,3:超期完成")
    private Integer workStatus;

    @ApiModelPropertyCheck(value = "实际工作日天数")
    private Integer workTime;
}
