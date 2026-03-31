package com.zds.biz.vo;

import lombok.Data;

@Data
public class GasSupplyQueryRequest {

    private Integer page = 1;
    private Integer pageSize = 10;
    private String createStartTime;
    private String createEndTime;
    private String startTime;
    private String endTime;
    private String status;
}