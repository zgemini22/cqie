package com.zds.user.dao;

import com.zds.user.po.KpiNormInfoFormula;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KpiNormInfoFormulaDao extends BaseMapper<KpiNormInfoFormula> {

    /**
     * 批量新增指标数据源
     */
    void insertList(@Param("list") List<KpiNormInfoFormula> formulaList);
}
