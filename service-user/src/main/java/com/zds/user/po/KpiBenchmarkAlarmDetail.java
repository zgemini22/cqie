package com.zds.user.po;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
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
@TableName("kpi_benchmark_alarm_detail")
@ApiModel(value = "KpiBenchmarkAlarmDetail对象", description = "kpi标杆管理警阈值公式表")
public class KpiBenchmarkAlarmDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelPropertyCheck(value = "kpi标杆管理警阈值ID")
    private Long alarmId;

    @ApiModelPropertyCheck(value = "数据源ID")
    private Long dataId;

    @ApiModelPropertyCheck(value = "排序")
    private Integer sort;

    @ApiModelPropertyCheck(value = "数值")
    private BigDecimal number;

    public static LambdaQueryWrapper<KpiBenchmarkAlarmDetail> getWrapper() {
        return new LambdaQueryWrapper<>();
    }
}
