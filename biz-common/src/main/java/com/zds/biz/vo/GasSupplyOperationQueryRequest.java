// GasSupplyOperationQueryRequest.java
package com.zds.biz.vo;

import lombok.Data;

@Data
public class GasSupplyOperationQueryRequest {
    private Long page = 1L;
    private Long pageSize = 10L;
    private String planStartTime;  // 供气开始时间（查询条件）
    private String planEndTime;    // 供气结束时间（查询条件）
    private String supplyType;     // 供气类型
}