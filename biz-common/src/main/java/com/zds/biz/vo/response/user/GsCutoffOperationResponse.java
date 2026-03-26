package com.zds.biz.vo.response.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@ApiModel(description = "停气作业列表返回")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GsCutoffOperationResponse {

    @ApiModelProperty(value = "主键ID")
    private Long id;

    @ApiModelProperty(value = "计划停气时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date startTime;

    @ApiModelProperty(value = "计划恢复时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date endTime;

    @ApiModelProperty(value = "城燃企业")
    private String organizationName;

    @ApiModelProperty(value = "停气类型")
    private Long operationType;

    @ApiModelProperty(value = "停气区域")
    private Long detailAddress;

    @ApiModelProperty(value = "停气原因")
    private String reason;

    @ApiModelProperty(value = "停气作业等级")
    private String operationLevel;

    @ApiModelProperty(value = "影响户数")
    private Integer affectedHouseholds;

    @ApiModelProperty(value = "备注说明")
    private String remark;

}
