package com.zds.biz.vo.response.flow;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import com.zds.biz.vo.request.flow.FlowCommentAnnexRequest;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@ApiModel(description = "事故处置流程详情返回")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GasReviewTaskResponse {
    @ApiModelPropertyCheck("是否为审核人")
    private Boolean review;

    @ApiModelPropertyCheck("对应审核待办id")
    private String todoListId;

}
