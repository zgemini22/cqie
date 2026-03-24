package com.zds.biz.vo.response.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@ApiModel(description = "巡检内容详情返回")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TpcInspectionContentDetailResponse {

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

    @ApiModelProperty(value = "组织名称")
    private String organizationName;

    @ApiModelProperty(value = "三方施工名称")
    private String constructionName;

    @ApiModelProperty(value = "行政区域名称")
    private String areaName;

    @ApiModelProperty(value = "详细地址")
    private String address;

    @ApiModelProperty(value = "巡检人名称")
    private String inspectorName;

    @ApiModelProperty(value = "巡检状态")
    private String inspectStatus;

    @ApiModelProperty(value = "巡检内容")
    private String inspectionContent;

/*    @ApiModelProperty(value = "巡检情况") todo：目前数据库没有该字段
    private String inspectorName;*/
}
