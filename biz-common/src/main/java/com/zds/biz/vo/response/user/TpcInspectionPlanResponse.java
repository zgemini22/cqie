package com.zds.biz.vo.response.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@ApiModel(description = "巡检计划列表返回")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TpcInspectionPlanResponse {

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

    @ApiModelProperty(value = "状态")
    private String status;


    @ApiModelProperty(value = "三方施工名称")
    private String constructionName; // 通过三方施工id查询tpc_consturction表

    @ApiModelProperty(value = "详细地址")
    private String address;

    @ApiModelProperty(value = "行政区域名称")
    private String areaName;

    @ApiModelProperty(value = "组织名称")
    private String organizationName;
}
