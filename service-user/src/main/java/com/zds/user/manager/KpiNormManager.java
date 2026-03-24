package com.zds.user.manager;

import com.zds.biz.vo.response.user.KpiNormBenchmarkVo;
import com.zds.biz.vo.response.user.KpiNormResultVo;

import java.util.Date;

public interface KpiNormManager {
    /**
     * 计算指定指标的指定时间范围内的结果
     * @param normId 指标ID
     * @param startTime 开始日期
     * @param endTime 结束日期
     */
    KpiNormResultVo computeNormById(Long normId, Date startTime, Date endTime);

    /**
     * 计算指定指标的指定时间范围内的结果
     * @param normId 指标ID
     * @param startTime 开始日期
     * @param endTime 结束日期
     */
    KpiNormResultVo computeNormById(Long normId, Date startTime, Date endTime, Boolean companySearchFlag, Boolean streetSearchFlag);

    /**
     * 计算指定指标的指定时间范围内的标杆的结果
     * @param normId 指标ID
     * @param startTime 开始日期
     * @param endTime 结束日期
     */
    KpiNormBenchmarkVo computeNormBenchmarkById(Long normId, Date startTime, Date endTime, Integer decimalDigit);
}
