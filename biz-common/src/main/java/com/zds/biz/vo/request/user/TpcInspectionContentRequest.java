package com.zds.biz.vo.request.user;

import com.zds.biz.vo.PageRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@ApiModel(description = "巡检记录")
@Data
public class TpcInspectionContentRequest extends PageRequest {

    @ApiModelProperty(value = "巡检计划名称")
    private String planName;

    @ApiModelProperty(value = "城燃企业")
    private String organizationName;

    @ApiModelProperty(value = "巡检类别")
    private String planCategory;

    @ApiModelProperty(value = "三方施工名称")
    private String constructionName;

    @ApiModelProperty(value = "巡检状态")
    private String status;

    @ApiModelProperty(value = "巡检时间开始")
    private Date inspectTimeStart;

    @ApiModelProperty(value = "巡检时间结束")
    private Date inspectTimeEnd;

}
