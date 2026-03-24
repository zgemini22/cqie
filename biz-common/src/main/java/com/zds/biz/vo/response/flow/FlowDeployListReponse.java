package com.zds.biz.vo.response.flow;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@ApiModel(description = "流程列表返回")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FlowDeployListReponse  {
    @ApiModelPropertyCheck("ID")
    private Long id;

    @ApiModelPropertyCheck("流程名称")
    private String name;

    @ApiModelPropertyCheck("流程key")
    private String key;

    @ApiModelPropertyCheck("流程xml")
    private String xml;

    @ApiModelPropertyCheck("流程类别")
    private String type;

    @ApiModelPropertyCheck("创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;

    @ApiModelPropertyCheck("流程状态（1-已发布,2-未发布）")
    private String status;
}
