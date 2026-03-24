package com.zds.biz.vo.request.user;

import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import com.zds.biz.vo.request.flow.FlowCommentAnnexRequest;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@ApiModel(description = "事故处理请求对象")
@NoArgsConstructor
@AllArgsConstructor
public class AccidentHandlingRequest {
    @ApiModelPropertyCheck(value = "待办任务id" )
    private Long todoId;

    @ApiModelPropertyCheck(value = "审批意见文本内容" ,required = true)
    private String commentText;

    @ApiModelPropertyCheck(value = "是否通过(1=已处理,2=未处理)" ,required = true)
    private String pass;

    @ApiModelPropertyCheck("审批意见附件")
    protected List<FlowCommentAnnexRequest> commentAnnex;

    @ApiModelPropertyCheck("审批意见图片")
    protected List<FlowCommentAnnexRequest> commentPicture;
}
