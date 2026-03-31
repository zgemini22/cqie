// GsSupplyAreaNodePo.java
package com.zds.user.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.util.Date;

@Data
@TableName("gs_supply_area_node")
public class GsSupplyAreaNodePo {

    @TableId(value = "id")
    private Long id;

    @TableField("org_id")
    private Long orgId;

    @TableField("source_type")
    private String sourceType;

    @TableField("source_id")
    private Long sourceId;

    @TableField("district_code")
    private String districtCode;

    @TableField("street_code")
    private String streetCode;

    @TableField("detail_address")
    private String detailAddress;

    @TableField("create_time")
    private Date createTime;

    @TableField("create_id")
    private Long createId;

    @TableField("deleted")
    private Integer deleted;
}