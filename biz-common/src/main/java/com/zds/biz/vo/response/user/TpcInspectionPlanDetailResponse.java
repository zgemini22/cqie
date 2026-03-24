package com.zds.biz.vo.response.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@ApiModel(description = "巡检计划详情返回")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TpcInspectionPlanDetailResponse {

    @ApiModelProperty(value = "主键ID")
    private Long id;

    @ApiModelProperty(value = "巡检计划名称")
    private String planName;

    @ApiModelProperty(value = "巡检类别")
    private String planCategory;

    @ApiModelProperty(value = "巡检频次")
    private String inspectionFrequency;

    @ApiModelProperty(value = "计划有效期开始")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date validStartDate;

    @ApiModelProperty(value = "计划有效期结束")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date validEndDate;

    @ApiModelProperty(value = "巡检人")
    private Long inspector;

    @ApiModelProperty(value = "巡检人名称")
    private String inspectorName;

    @ApiModelProperty(value = "巡检人手机号")
    private String inspectorPhone;

    @ApiModelProperty(value = "三方施工名称")
    private String constructionName;

    @ApiModelProperty(value = "详细地址")
    private String address;

    @ApiModelProperty(value = "行政区域名称")
    private String areaName;

    @ApiModelProperty(value = "组织名称")
    private String organizationName;

    @ApiModelProperty(value = "巡检内容")
    private String inspectionContent;
}
