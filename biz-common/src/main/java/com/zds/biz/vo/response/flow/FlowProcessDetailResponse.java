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

@ApiModel(description = "后台流程详情返回")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FlowProcessDetailResponse {

    @ApiModelPropertyCheck("发起时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date initiateDate;

    @ApiModelPropertyCheck("节点id")
    private String taskId;

    @ApiModelPropertyCheck("对应待办id")
    private String todoId;

    @ApiModelPropertyCheck("节点名称")
    private String name;

    @ApiModelPropertyCheck("操作者")
    private String assignee;

    @ApiModelPropertyCheck("所属单位")
    private String group;

    @ApiModelPropertyCheck("处理意见")
    private String comment;

    @ApiModelPropertyCheck(value = "状态(1=审核通过,2=审核不通过)")
    private String pass;

    @ApiModelPropertyCheck("备注")
    private String remarks;

    @ApiModelPropertyCheck("实际处理时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date tackleDate;

    @ApiModelPropertyCheck("处理时间时限")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date dueDate;

    @ApiModelPropertyCheck("是否超期")
    private Boolean overdue;

    @ApiModelPropertyCheck("办理周期（天）")
    private Integer dueDay;

    @ApiModelPropertyCheck("附件")
    private List<FlowCommentAnnexRequest> files;

    @ApiModelPropertyCheck("图片")
    private List<FlowCommentAnnexRequest> picture;
}
