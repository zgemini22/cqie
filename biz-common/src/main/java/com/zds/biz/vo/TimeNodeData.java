package com.zds.biz.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 图表数据集计算过渡类-时间节点数据类
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TimeNodeData {

    /**
     * 数值
     */
    private BigDecimal num;

    /**
     * 时间
     */
    private Date time;
}
