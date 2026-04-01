package com.zds.user.po.risk;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "RiskPointDetail", description = "风险点+设备加装计划详情")
public class RiskPointDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    // rp_risk_point
    @ApiModelPropertyCheck("风险点ID")
    private Long id;

    @ApiModelPropertyCheck("风险点名称")
    private String riskName;

    @ApiModelPropertyCheck("风险点编号")
    private String riskCode;

    @ApiModelPropertyCheck("行政区域")
    private String townStreetName;

    @ApiModelPropertyCheck("风险点位置")
    private String address;

    @ApiModelPropertyCheck("风险点状态")
    private String preventStatus;

    @ApiModelPropertyCheck("城燃企业名称")
    private String organizationName;

    // rp_device_install_plan
    @ApiModelPropertyCheck("计划加装设备类型")
    private String deviceType;

    @ApiModelPropertyCheck("计划加装数量")
    private Integer planQuantity;

    @ApiModelPropertyCheck("单位（台/套）")
    private String unit;

    @ApiModelPropertyCheck("计划加装日期")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date planInstallDate;

    @ApiModelPropertyCheck("计划加装开始日期")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date planInstallStartDate;

    @ApiModelPropertyCheck("计划加装结束日期")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date planInstallEndDate;
}