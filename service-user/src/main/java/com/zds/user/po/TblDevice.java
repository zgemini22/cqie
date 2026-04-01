package com.zds.user.po;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.math.BigDecimal;
import java.util.Date;

@Data
@TableName("tbl_device")
public class TblDevice {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String deviceCode;
    private String deviceName;
    private String deviceType;
    private String deviceModel;
    private String manufacturer;
    private String monitorType;
    private String range;
    private String installAddress;
    private BigDecimal lng;
    private BigDecimal lat;
    private String monitoringPoint;
    private BigDecimal realTimeVoltage;
    private BigDecimal realTimePower;
    private BigDecimal thresholdUpper;
    private BigDecimal thresholdLower;
    private Integer bindStatus;
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    private Long createId;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
    private Long updateId;
    @TableLogic
    private Integer deleted;
}