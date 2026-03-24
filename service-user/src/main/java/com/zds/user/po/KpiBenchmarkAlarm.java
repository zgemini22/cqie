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
@TableName("kpi_benchmark_alarm")
@ApiModel(value = "KpiBenchmarkAlarm对象", description = "kpi标杆管理-警阈值")
public class KpiBenchmarkAlarm implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelPropertyCheck(value = "kpi标杆管理警阈值ID")
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

    @ApiModelPropertyCheck(value = "标杆ID")
    private Long benchmarkId;

    @ApiModelPropertyCheck(value = "符号（1=(<=),2=(>=),3=(>),4=(<)）")
    private Integer symbol;

    @ApiModelPropertyCheck(value = "告警阈值/标杆值")
    private BigDecimal value;

    @ApiModelPropertyCheck(value = "等级（1=1级，2=2级，3=3级，4=标杆值）")
    private Integer grade;

    @ApiModelPropertyCheck(value = "序号")
    private Integer sort;

    public static LambdaQueryWrapper<KpiBenchmarkAlarm> getWrapper() {
        return new LambdaQueryWrapper<>();
    }
}
