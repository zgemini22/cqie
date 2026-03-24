package com.zds.biz.vo.response.user;

import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

@ApiModel(description = "巡检内容详情返回")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TpcInspectionContentResponse {

    @ApiModelProperty(value = "主键ID")
    private Long id;

    @ApiModelProperty(value = "巡检计划名称")
    private String planName;

    @ApiModelProperty(value = "巡检类别")
    private String planCategory;

    @ApiModelProperty(value = "三方施工名称")
    private String constructionName;

    @ApiModelProperty(value = "巡检人员")
    private String inspector;

    @ApiModelProperty(value = "城燃企业")
    private String organizationName;

    @ApiModelProperty(value = "巡检时间")
    private Date inspectTime;

    @ApiModelProperty(value = "巡检状态")
    private String status;



}
