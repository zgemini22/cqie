package com.zds.user.po;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.math.BigDecimal;
import java.util.Date;

@Data
@TableName("station_device_record")
public class StationDeviceRecord {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long deviceId;
    private BigDecimal monitorValue;
    private String monitorLocation;
    private Date lastCollectTime;
    private String runningStatus;
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    private Long createId;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
    private Long updateId;
    @TableLogic
    private Integer deleted;
}