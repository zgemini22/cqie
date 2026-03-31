// GasSupplyOperationResponse.java
package com.zds.biz.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.util.Date;

@Data
public class GasSupplyOperationResponse {

    private Integer id;
    private String enterpriseName;  // 从组织表获取的企业名称（organization_name）
    private String supplyType;      // 供气类型
    private String supplyArea;      // 供气区域
    private String supplyReason;    // 供气原因

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date planStartTime;     // 供气时间

    private Integer affectHouseholds; // 影响户数
    private String remarks;           // 备注

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date planEndTime;       // 供气结束时间
}