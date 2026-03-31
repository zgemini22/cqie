package com.zds.biz.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class GasSupplyRequest {

    private Integer id;

    // 供气原因
    @NotBlank(message = "供气原因不能为空")
    private String supplyReason;

    // 开始时间
    @NotNull(message = "计划供气开始时间不能为空")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date startTime;

    // 结束时间
    @NotNull(message = "计划供气结束时间不能为空")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date endTime;

    // 影响户数
    private Integer affectedHouseholds;

    // 备注
    private String remark;
}