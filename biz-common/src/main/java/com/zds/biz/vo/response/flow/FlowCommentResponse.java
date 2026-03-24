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

@ApiModel(description = "获取评论返回")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FlowCommentResponse {
    @ApiModelPropertyCheck("ID")
    protected String id;

    @ApiModelPropertyCheck(value = "审批意见文本内容")
    private String commentText;

    @ApiModelPropertyCheck("审批意见附件")
    protected List<FlowCommentAnnexRequest> commentAnnex;

}
