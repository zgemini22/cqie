package com.zds.user.po.risk;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("rp_device_install_plan")
@ApiModel(value = "RpDeviceInstallPlan", description = "设备加装计划表")
public class RpDeviceInstallPlan implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    @ApiModelPropertyCheck("主键")
    private Long id;

    @ApiModelPropertyCheck("风险点ID")
    private Long riskId;

    @ApiModelPropertyCheck("设备类型")
    private String deviceType;

    @ApiModelPropertyCheck("计划加装数量")
    private Integer planQuantity;

    @ApiModelPropertyCheck("计划加装日期")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date planInstallDate;

    @ApiModelPropertyCheck("是否删除")
    private int deleted;

    @ApiModelPropertyCheck("城燃企业名称")
    private String organizationName;
}
