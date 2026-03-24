package com.zds.biz.vo;

import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(description = "kpi通用类")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KpiMainVo {

    @ApiModelPropertyCheck("bb")
    private Integer bb;

    @ApiModelPropertyCheck("cc")
    private Integer cc;

    @ApiModelPropertyCheck("dd")
    private Integer dd;

    @ApiModelPropertyCheck("ee")
    private Integer ee;
}
