package com.zds.biz.vo.response.user;

import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(description = "当前用户消息统计返回")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClientMessageStatisticsResponse {
    @ApiModelPropertyCheck("未读总数")
    private Integer readTotal;
}
