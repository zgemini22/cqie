package com.zds.biz.vo.request.flow;

import io.swagger.annotations.ApiModel;
import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@ApiModel(value = "FlowCommentAnnex", description = "审批附件")
public class FlowCommentAnnexRequest {
    @ApiModelPropertyCheck(value = "原始文件名")
    private String realFileName;

    @ApiModelPropertyCheck(value = "保存文件名")
    private String fileName;
}
