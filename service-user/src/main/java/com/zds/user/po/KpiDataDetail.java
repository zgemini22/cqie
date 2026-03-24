package com.zds.user.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
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
@TableName("kpi_data_detail")
@ApiModel(value = "KpiDataDetail对象", description = "kpi数据源管理-明细")
public class KpiDataDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelPropertyCheck(value = "kpi数据源管理明细ID")
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

    @ApiModelPropertyCheck(value = "数据源ID")
    private Long kpiDataId;

    @ApiModelPropertyCheck(value = "企业名称")
    private String companyName;

    @ApiModelPropertyCheck(value = "街道名称")
    private String street;

    @ApiModelPropertyCheck(value = "数据日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date dataDate;

    @ApiModelPropertyCheck(value = "数据值")
    private BigDecimal value;

    public static LambdaQueryWrapper<KpiDataDetail> getWrapper() {
        return new LambdaQueryWrapper<>();
    }
}
