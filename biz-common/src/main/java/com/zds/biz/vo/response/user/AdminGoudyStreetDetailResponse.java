package com.zds.biz.vo.response.user;

import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@ApiModel(description = "街道分组返回")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminGoudyStreetDetailResponse {
    @ApiModelPropertyCheck(value = "项目Id")
    private Long projectId;

    @ApiModelPropertyCheck(value = "项目代码")
    private String projectCode;

    @ApiModelPropertyCheck(value = "工程名称")
    private String projectName;

    @ApiModelPropertyCheck(value = "项目类型(group_key=PROJECT_TYPE)")
    private String projectType;

    @ApiModelPropertyCheck(value = "项目周期")
    private Integer projectCycle;

    @ApiModelPropertyCheck(value = "建设地点")
    private String address;

    @ApiModelPropertyCheck(value = "节点（0-草稿，1-立项2=投资备案3=计划许可4=开挖许可5=工程开工6=过程质量7=竣工验收 8=竣工完成）")
    private Integer point;

    @ApiModelPropertyCheck(value = "状态（1-正常，2=超时）")
    private Integer status;

    @ApiModelPropertyCheck(value = "经度")
    private BigDecimal longitude;

    @ApiModelPropertyCheck(value = "纬度")
    private BigDecimal latitude;
}
