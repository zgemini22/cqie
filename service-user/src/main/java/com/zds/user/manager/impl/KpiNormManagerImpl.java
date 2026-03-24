package com.zds.user.manager.impl;

import com.alibaba.fastjson.JSON;
import com.zds.biz.constant.BaseException;
import com.zds.biz.constant.user.IndicatorTypeEnum;
import com.zds.biz.constant.user.NormDimensionsEnum;
import com.zds.biz.vo.response.user.*;
import com.zds.user.dao.*;
import com.zds.user.manager.KpiNormManager;
import com.zds.user.po.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.nfunk.jep.JEP;
import org.nfunk.jep.Node;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class KpiNormManagerImpl implements KpiNormManager {

    @Autowired
    private KpiNormInfoDao kpiNormInfoDao;

    @Autowired
    private KpiNormInfoFormulaDao kpiNormInfoFormulaDao;

    @Autowired
    private KpiDataDao kpiDataDao;

    @Autowired
    private KpiDataDetailDao kpiDataDetailDao;

    @Autowired
    private KpiBenchmarkInfoDao kpiBenchmarkInfoDao;

    @Autowired
    private KpiBenchmarkAlarmDao kpiBenchmarkAlarmDao;

    @Autowired
    private KpiBenchmarkAlarmDetailDao kpiBenchmarkAlarmDetailDao;

    @Override
    public KpiNormResultVo computeNormById(Long normId, Date startTime, Date endTime) {
        return computeNormById(normId, startTime, endTime, true, true);
    }

    @Override
    public KpiNormResultVo computeNormById(Long normId, Date startTime, Date endTime, Boolean companySearchFlag, Boolean streetSearchFlag) {
        KpiNormInfo normInfo = kpiNormInfoDao.selectById(normId);
        List<KpiNormInfoFormula> formulaList = kpiNormInfoFormulaDao.selectList(KpiNormInfoFormula.getWrapper().eq(KpiNormInfoFormula::getNormId, normId).orderByAsc(KpiNormInfoFormula::getSort));
        List<Long> dataIds = formulaList.stream().map(KpiNormInfoFormula::getDataId).collect(Collectors.toList());
        List<KpiData> dataList = kpiDataDao.selectList(KpiData.getWrapper().eq(KpiData::getDeleted, false).in(KpiData::getId, dataIds));
        Map<Long, KpiData> dataMap = dataList.stream().collect(Collectors.toMap(KpiData::getId, x -> x, (a, b) -> b));
        List<KpiDataDetail> dataDetailList = kpiDataDetailDao.selectList(KpiDataDetail.getWrapper()
                .eq(KpiDataDetail::getDeleted, false)
                .in(KpiDataDetail::getKpiDataId, dataIds)
                .between(startTime != null && endTime != null, KpiDataDetail::getDataDate, startTime, endTime));
        KpiNormResultVo vo = KpiNormResultVo.builder().normId(normId).normResultByCompany(new ArrayList<>()).normResultByStreet(new ArrayList<>()).build();
        //计算指标结果
        Map<Long, List<KpiDataDetail>> detailMap = dataDetailList.stream().filter(x -> StringUtils.isEmpty(x.getStreet())).collect(Collectors.groupingBy(KpiDataDetail::getKpiDataId));
        vo.setNormResult(computeData(formulaList, dataMap, detailMap, normInfo));
        //是否计算指标细化纬度
        if (StringUtils.isNotEmpty(normInfo.getNormDimensions())) {
            //计算按企业分组的指标结果
            if (normInfo.getNormDimensions().contains(NormDimensionsEnum.NORM_URBAN_COMPANY.getKey()) && companySearchFlag) {
                Map<String, List<KpiDataDetail>> detailGroupMap = dataDetailList.stream().filter(x -> StringUtils.isEmpty(x.getStreet())).collect(Collectors.groupingBy(x -> x.getKpiDataId() + "_" + x.getCompanyName()));
                vo.setNormResultByCompany(computeDataGroupByCompany(formulaList, dataMap, detailGroupMap, normInfo));
            }
            //计算按街道分组的指标结果
            if (normInfo.getNormDimensions().contains(NormDimensionsEnum.NORM_STREET.getKey()) && streetSearchFlag) {
                Map<String, List<KpiDataDetail>> detailGroupMap = dataDetailList.stream().filter(x -> StringUtils.isNotEmpty(x.getStreet())).collect(Collectors.groupingBy(x -> x.getKpiDataId() + "_" + x.getStreet()));
                vo.setNormResultByStreet(computeDataGroupByStreet(formulaList, dataMap, detailGroupMap, normInfo));
            }
//            //计算按企业街道分组的指标结果
//            if (normInfo.getNormDimensions().contains(NormDimensionsEnum.NORM_URBAN_COMPANY.getKey()) && normInfo.getNormDimensions().contains(NormDimensionsEnum.NORM_STREET.getKey())) {
//                Map<String, List<KpiDataDetail>> detailGroupMap = dataDetailList.stream().collect(Collectors.groupingBy(x -> x.getKpiDataId() + "_" + x.getCompanyName() + "_" + x.getStreet()));
//                vo.setNormResultByCAS(computeDataGroupByCAS(formulaList, dataMap, detailGroupMap, normInfo));
//            }
        }
        log.info(JSON.toJSONString(vo));
        return vo;
    }

    private List<KpiNormCompanyResultVo> computeDataGroupByCompany(List<KpiNormInfoFormula> formulaList, Map<Long, KpiData> dataMap, Map<String, List<KpiDataDetail>> detailMap, KpiNormInfo normInfo) {
        Map<String, Map<Long, List<KpiDataDetail>>> map = new HashMap<>();
        for (String key : detailMap.keySet()) {
            String[] split = key.split("_");
            if (split.length == 2 && StringUtils.isNotEmpty(split[1]) && !"null".equals(split[1])) {
                String company = split[1];
                Long dataId = Long.valueOf(split[0]);
                Map<Long, List<KpiDataDetail>> itemMap = map.getOrDefault(company, new HashMap<>());
                List<KpiDataDetail> itemList = itemMap.getOrDefault(dataId, new ArrayList<>());
                itemList.addAll(detailMap.get(key));
                itemMap.put(dataId, itemList);
                map.put(company, itemMap);
            }
        }
        List<KpiNormCompanyResultVo> resultVoList = new ArrayList<>();
        for (String company : map.keySet()) {
            resultVoList.add(KpiNormCompanyResultVo.builder()
                    .companyName(company)
                    .normResult(computeData(formulaList, dataMap, map.get(company), normInfo))
                    .build());
        }
        return resultVoList;
    }

    private List<KpiNormStreetResultVo> computeDataGroupByStreet(List<KpiNormInfoFormula> formulaList, Map<Long, KpiData> dataMap, Map<String, List<KpiDataDetail>> detailMap, KpiNormInfo normInfo) {
        Map<String, Map<Long, List<KpiDataDetail>>> map = new HashMap<>();
        for (String key : detailMap.keySet()) {
            String[] split = key.split("_");
            if (split.length == 2 && StringUtils.isNotEmpty(split[1]) && !"null".equals(split[1])) {
                String street = split[1];
                Long dataId = Long.valueOf(split[0]);
                Map<Long, List<KpiDataDetail>> itemMap = map.getOrDefault(street, new HashMap<>());
                List<KpiDataDetail> itemList = itemMap.getOrDefault(dataId, new ArrayList<>());
                itemList.addAll(detailMap.get(key));
                itemMap.put(dataId, itemList);
                map.put(street, itemMap);
            }
        }
        List<KpiNormStreetResultVo> resultVoList = new ArrayList<>();
        for (String street : map.keySet()) {
            resultVoList.add(KpiNormStreetResultVo.builder()
                    .street(street)
                    .normResult(computeData(formulaList, dataMap, map.get(street), normInfo))
                    .build());
        }
        return resultVoList;
    }

    private List<KpiNormCASResultVo> computeDataGroupByCAS(List<KpiNormInfoFormula> formulaList, Map<Long, KpiData> dataMap, Map<String, List<KpiDataDetail>> detailMap, KpiNormInfo normInfo) {
        Map<String, Map<Long, List<KpiDataDetail>>> map = new HashMap<>();
        for (String key : detailMap.keySet()) {
            String[] split = key.split("_");
            if (split.length == 3 && StringUtils.isNotEmpty(split[1]) && StringUtils.isNotEmpty(split[2]) && !"null".equals(split[1]) && !"null".equals(split[2])) {
                String company = split[1];
                String street = split[2];
                Long dataId = Long.valueOf(split[0]);
                Map<Long, List<KpiDataDetail>> itemMap = map.getOrDefault(company + "_" + street, new HashMap<>());
                List<KpiDataDetail> itemList = itemMap.getOrDefault(dataId, new ArrayList<>());
                itemList.addAll(detailMap.get(key));
                itemMap.put(dataId, itemList);
                map.put(company + "_" + street, itemMap);
            }
        }
        List<KpiNormCASResultVo> resultVoList = new ArrayList<>();
        for (String companyStreet : map.keySet()) {
            String[] split = companyStreet.split("_");
            resultVoList.add(KpiNormCASResultVo.builder()
                    .companyName(split[0])
                    .street(split[1])
                    .normResult(computeData(formulaList, dataMap, map.get(companyStreet), normInfo))
                    .build());
        }
        return resultVoList;
    }

    private BigDecimal computeData(List<KpiNormInfoFormula> formulaList, Map<Long, KpiData> dataMap, Map<Long, List<KpiDataDetail>> detailMap, KpiNormInfo normInfo) {
        if (normInfo.getNormIndicatorType().equals(IndicatorTypeEnum.BASIC_NORM.getKey())) {
            List<KpiDataDetail> dataDetails = new ArrayList<>();
            for (List<KpiDataDetail> value : detailMap.values()) {
                dataDetails.addAll(value);
            }
            return dataDetails.size() == 0 ? BigDecimal.ZERO : new BigDecimal(BigDecimal.valueOf(dataDetails.stream().mapToDouble(x -> x.getValue().doubleValue()).average().getAsDouble()).setScale(normInfo.getDecimalDigit(), RoundingMode.HALF_UP).stripTrailingZeros().toPlainString());
        }
        String func = "";//计算公式
        for (int i = 0; i < formulaList.size(); i++) {
            KpiNormInfoFormula formula = formulaList.get(i);
            if (dataMap.containsKey(formula.getDataId())) {
                KpiData data = dataMap.get(formula.getDataId());
                //判断是否为数值输入
                if ("数值输入".equals(data.getDataCode())) {
                    func += formula.getNumber().toPlainString();
                } else if ("sum".equals(data.getDataCode()) || "count".equals(data.getDataCode()) || "avg".equals(data.getDataCode()) || "min".equals(data.getDataCode()) || "max".equals(data.getDataCode())) {//判断是否为聚合函数
                    BigDecimal num = BigDecimal.ZERO;
                    Long dataId = formulaList.get(i + 2).getDataId();
                    List<KpiDataDetail> detailList = detailMap.get(dataId);
                    if (detailList != null && detailList.size() > 0) {
                        switch (data.getDataCode()) {
                            case "sum":
                                num = detailList.stream().map(KpiDataDetail::getValue).reduce(BigDecimal.ZERO, BigDecimal::add);
                                break;
                            case "count":
                                num = BigDecimal.valueOf(detailList.size());
                                break;
                            case "avg":
                                num = BigDecimal.valueOf(detailList.stream().mapToDouble(x -> x.getValue().doubleValue()).average().getAsDouble());
                                break;
                            case "min":
                                num = BigDecimal.valueOf(detailList.stream().mapToDouble(x -> x.getValue().doubleValue()).min().getAsDouble());
                                break;
                            case "max":
                                num = BigDecimal.valueOf(detailList.stream().mapToDouble(x -> x.getValue().doubleValue()).max().getAsDouble());
                                break;
                        }
                    }
                    func += num.setScale(2, RoundingMode.HALF_UP).toPlainString();
                    i += 3;
                } else if ("100%".equals(data.getDataCode())) {
                    func += "100";
                } else if (data.getDataType() == 1) {
                    BigDecimal num = detailMap.get(data.getId()).size() == 1 ? detailMap.get(data.getId()).get(0).getValue() : BigDecimal.ZERO;
                    func += num.setScale(2, RoundingMode.HALF_UP).toPlainString();
                } else {//组装公式
                    func += data.getDataCode();
                }
            } else {
                throw new BaseException("指标公式引用的数据源已停用,请重新设置指标公式");
            }
        }
        String num = "0";
        try {
            JEP jep = new JEP();
            Node node = jep.parse(func);
            String item = String.valueOf(jep.evaluate(node));
            if (!"NaN".equals(item) && !"Infinity".equals(item)) {
                num = new BigDecimal(item).setScale(normInfo.getDecimalDigit(), RoundingMode.HALF_UP).stripTrailingZeros().toPlainString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new BigDecimal(num);
    }

    @Override
    public KpiNormBenchmarkVo computeNormBenchmarkById(Long normId, Date startTime, Date endTime, Integer decimalDigit) {
        KpiNormBenchmarkVo data = KpiNormBenchmarkVo.builder().normId(normId).alarmList(new ArrayList<>()).build();
        KpiBenchmarkInfo benchmarkInfo = kpiBenchmarkInfoDao.selectOne(KpiBenchmarkInfo.getWrapper()
                .eq(KpiBenchmarkInfo::getDeleted, false)
                .eq(KpiBenchmarkInfo::getStatus, 1)
                .eq(KpiBenchmarkInfo::getNormId, normId)
                .last("limit 1"));
        if (benchmarkInfo == null) {//未设置标杆
            return data;
        }
        List<KpiBenchmarkAlarm> alarmList = kpiBenchmarkAlarmDao.selectList(KpiBenchmarkAlarm.getWrapper()
                .eq(KpiBenchmarkAlarm::getDeleted, false)
                .eq(KpiBenchmarkAlarm::getBenchmarkId, benchmarkInfo.getId())
                .orderByAsc(KpiBenchmarkAlarm::getSort));
        Map<Long, List<KpiBenchmarkAlarmDetail>> alarmDetailMap = new HashMap<>();
        if (benchmarkInfo.getBenchmarkType() == 1) {
            alarmDetailMap = kpiBenchmarkAlarmDetailDao.selectList(KpiBenchmarkAlarmDetail.getWrapper()
                    .in(KpiBenchmarkAlarmDetail::getAlarmId, alarmList.stream().map(KpiBenchmarkAlarm::getId).collect(Collectors.toList()))
                    .orderByAsc(KpiBenchmarkAlarmDetail::getSort))
                    .stream().collect(Collectors.groupingBy(KpiBenchmarkAlarmDetail::getAlarmId));
        }
        for (KpiBenchmarkAlarm alarm : alarmList) {
            if (alarm.getGrade() == 4) {
                data.setSymbol(alarm.getSymbol());
                if (benchmarkInfo.getBenchmarkType() == 2) {//手动维护
                    data.setValue(alarm.getValue().setScale(decimalDigit, RoundingMode.HALF_UP));
                } else if (benchmarkInfo.getBenchmarkType() == 1) {//自动维护
                    data.setValue(getValue(alarmDetailMap, alarm, startTime, endTime, benchmarkInfo));
                }
            } else if (alarm.getGrade() == 1 || alarm.getGrade() == 2 || alarm.getGrade() == 3) {
                KpiNormBenchmarkAlarmVo alarmVo = KpiNormBenchmarkAlarmVo.builder()
                        .symbol(alarm.getSymbol())
                        .grade(alarm.getGrade())
                        .sort(alarm.getSort())
                        .build();
                if (benchmarkInfo.getBenchmarkType() == 2) {//手动维护
                    alarmVo.setValue(alarm.getValue().setScale(decimalDigit, RoundingMode.HALF_UP));
                } else if (benchmarkInfo.getBenchmarkType() == 1) {//自动维护
                    alarmVo.setValue(getValue(alarmDetailMap, alarm, startTime, endTime, benchmarkInfo));
                }
                List<KpiNormBenchmarkAlarmVo> alarmList1 = data.getAlarmList();
                alarmList1.add(alarmVo);
            }
        }
        return data;
    }

    //计算自动维护的标杆值或指标告警阈值
    private BigDecimal getValue(Map<Long, List<KpiBenchmarkAlarmDetail>> alarmDetailMap, KpiBenchmarkAlarm alarm, Date startTime, Date endTime, KpiBenchmarkInfo benchmarkInfo) {
        List<KpiNormInfoFormula> formulaList = alarmDetailMap.get(alarm.getId()).stream()
                .map(x -> KpiNormInfoFormula.builder()
                        .normId(x.getAlarmId())
                        .dataId(x.getDataId())
                        .sort(x.getSort())
                        .number(x.getNumber())
                        .build())
                .collect(Collectors.toList());
        List<Long> dataIds = formulaList.stream().map(KpiNormInfoFormula::getDataId).collect(Collectors.toList());
        List<KpiData> dataList = kpiDataDao.selectList(KpiData.getWrapper().eq(KpiData::getDeleted, false).in(KpiData::getId, dataIds));
        Map<Long, KpiData> dataMap = dataList.stream().collect(Collectors.toMap(KpiData::getId, x -> x, (a, b) -> b));
        List<KpiDataDetail> dataDetailList = kpiDataDetailDao.selectList(KpiDataDetail.getWrapper()
                .eq(KpiDataDetail::getDeleted, false)
                .in(KpiDataDetail::getKpiDataId, dataIds)
                .between(startTime != null && endTime != null, KpiDataDetail::getDataDate, startTime, endTime));
        //计算指标结果
        Map<Long, List<KpiDataDetail>> detailMap = dataDetailList.stream().collect(Collectors.groupingBy(KpiDataDetail::getKpiDataId));
        return computeData(formulaList, dataMap, detailMap, KpiNormInfo.builder()
                .decimalDigit(benchmarkInfo.getDecimalDigit())
                .normIndicatorType(IndicatorTypeEnum.COMPOSITE_NORM.getKey())
                .build());
    }
}
