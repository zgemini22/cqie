package com.zds.biz.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 图表数据集计算过渡类-时间周期数据类
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PeriodNodeData {

    /**
     * 开始时间
     */
    private Date startTime;

    /**
     * 结束时间
     */
    private Date endTime;

    /**
     * 数值
     */
    private BigDecimal num;
}
