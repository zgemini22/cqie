package com.zds.biz.vo.response.user;

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

@Data
@Builder
@ApiModel(description = "APP待办详情返回")
@NoArgsConstructor
@AllArgsConstructor
public class ClientUserTodoDetailResponse {
    @ApiModelPropertyCheck(value = "待办任务id")
    private Long todoId;

    @ApiModelPropertyCheck(value = "事故id")
    private Long dataId;

    @ApiModelPropertyCheck(value = "处理周期")
    private Integer dueDate;

    @ApiModelPropertyCheck("处理时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date tackleDate;

    @ApiModelPropertyCheck(value = "处理反馈")
    private String commentText;

    @ApiModelPropertyCheck(value = "待办状态,1:未处理,2:已处理")
    private Integer status;

    @ApiModelPropertyCheck("审批意见附件")
    protected List<FlowCommentAnnexRequest> commentAnnex;

    @ApiModelPropertyCheck("审批意见图片")
    protected List<FlowCommentAnnexRequest> commentPicture;

}
