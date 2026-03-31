// TblOrganizationPo.java
package com.zds.user.po;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.util.Date;

@Data
@TableName("tbl_organization")
public class TblOrganizationPo {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("organization_name")
    private String organizationName;  // 组织名称

    @TableField("organization_code")
    private String organizationCode;  // 组织编码

    @TableField("parent_id")
    private Integer parentId;

    @TableField("organization_type")
    private String organizationType;  // 组织类型

    @TableField("status")
    private Integer status;

    @TableField("create_time")
    private Date createTime;

    @TableField("update_time")
    private Date updateTime;

    @TableField("deleted")
    private Integer deleted;
}