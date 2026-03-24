package com.zds.biz.vo.request.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zds.biz.vo.PageRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@ApiModel(description = "巡检计划查询请求")
@Data
public class TpcInspectionPlanRequest extends PageRequest {


    @ApiModelProperty(value = "巡检计划名称")
    private String planName;

    @ApiModelProperty(value = "巡检类别")
    private String planCategory;

    @ApiModelProperty(value = "巡检状态")
    private String status;

    @ApiModelProperty(value = "计划有效期开始")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date validStartDate;

    @ApiModelProperty(value = "计划有效期结束")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date validEndDate;

    @ApiModelProperty(value = "三方施工名称")
    private String constructionName;

    @ApiModelProperty(value = "城燃企业")
    private String organizationName;


    @ApiModelProperty(value = "行政区域名称")
    private String areaName;


}
