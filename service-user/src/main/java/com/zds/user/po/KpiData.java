package com.zds.user.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import com.zds.biz.targetcheck.ApiModelPropertyCheck;
import io.swagger.annotations.ApiModel;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonFormat;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("kpi_data")
@ApiModel(value = "KpiData对象", description = "kpi数据源管理-主表")
public class KpiData implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelPropertyCheck(value = "kpi数据源管理ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelPropertyCheck(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;

    @ApiModelPropertyCheck(value = "创建人")
    private Long createId;

    @ApiModelPropertyCheck(value = "修改时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date updateTime;

    @ApiModelPropertyCheck(value = "修改人")
    private Long updateId;

    @ApiModelPropertyCheck(value = "是否删除")
    private Boolean deleted;

    @ApiModelPropertyCheck(value = "组织ID")
    private Long organizationId;

    @ApiModelPropertyCheck(value = "数据源编号")
    private String dataCode;

    @ApiModelPropertyCheck(value = "数据源名称")
    private String dataName;

    @ApiModelPropertyCheck(value = "数据来源（1=数据库，2=接口对接）")
    private Integer dataSource;

    @ApiModelPropertyCheck(value = "备注")
    private String remarks;

    @ApiModelPropertyCheck(value = "数据类型（1=数据源，2=运算符）")
    private Integer dataType;

    @ApiModelPropertyCheck(value = "数据状态（1=启用，2=停用）")
    private Integer status;

    public static LambdaQueryWrapper<KpiData> getWrapper() {
        return new LambdaQueryWrapper<>();
    }
}
