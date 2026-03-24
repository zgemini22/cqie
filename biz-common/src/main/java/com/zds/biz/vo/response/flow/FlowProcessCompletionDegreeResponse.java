package com.zds.biz.vo.response.flow;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zds.biz.vo.request.flow.FlowCommentAnnexRequest;
import io.swagger.annotations.ApiModel;
import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@ApiModel(description = "后台流程完成度返回")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FlowProcessCompletionDegreeResponse {
    @ApiModelPropertyCheck("流程id")
    private String id;

    @ApiModelPropertyCheck("完成百分比")
    private Double percentage;

}
