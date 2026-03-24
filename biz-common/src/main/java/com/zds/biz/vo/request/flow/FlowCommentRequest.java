package com.zds.biz.vo.request.flow;

import io.swagger.annotations.ApiModel;
import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@ApiModel(description = "完成待办请求对象")
@NoArgsConstructor
@AllArgsConstructor
public class FlowCommentRequest {
    @ApiModelPropertyCheck(value = "待办任务id" )
    private Long todoId;

    @ApiModelPropertyCheck(value = "审批意见文本内容")
    private String commentText;

    @ApiModelPropertyCheck(value = "是否通过(1=通过,2=不通过)" ,required = true)
    private String pass;

    @ApiModelPropertyCheck("审批意见附件")
    protected List<FlowCommentAnnexRequest> commentAnnex;

    @ApiModelPropertyCheck("审批意见图片")
    protected List<FlowCommentAnnexRequest> commentPicture;
}
