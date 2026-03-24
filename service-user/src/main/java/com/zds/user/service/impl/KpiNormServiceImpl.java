package com.zds.user.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zds.biz.constant.BaseException;
import com.zds.biz.constant.user.DictGroupEnum;
import com.zds.biz.constant.user.NormUnitEnum;
import com.zds.biz.util.ThreadLocalUtil;
import com.zds.biz.util.TimeChartUtil;
import com.zds.biz.vo.IdRequest;
import com.zds.biz.vo.SelectResponse;
import com.zds.biz.vo.TableBase;
import com.zds.biz.vo.request.user.*;
import com.zds.biz.vo.response.user.*;
import com.zds.user.dao.*;
import com.zds.user.manager.KpiNormManager;
import com.zds.user.po.*;
import com.zds.user.service.KpiNormService;
import net.sourceforge.pinyin4j.PinyinHelper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class KpiNormServiceImpl implements KpiNormService {

    @Autowired
    private ThreadLocalUtil threadLocalUtil;

    @Autowired
    private KpiNormClassifyDao kpiNormClassifyDao;

    @Autowired
    private KpiNormInfoDao kpiNormInfoDao;

    @Autowired
    private KpiDataDao kpiDataDao;

    @Autowired
    private KpiDataDetailDao kpiDataDetailDao;

    @Autowired
    private KpiNormInfoFormulaDao kpiNormInfoFormulaDao;

    @Autowired
    private KpiBenchmarkInfoDao kpiBenchmarkInfoDao;

    @Autowired
    private KpiBenchmarkAlarmDao kpiBenchmarkAlarmDao;

    @Autowired
    private KpiBenchmarkAlarmDetailDao kpiBenchmarkAlarmDetailDao;

    @Autowired
    private TblDictSubsetDao dictSubsetDao;

    @Autowired
    private KpiNormManager kpiNormManager;

    @Value("classpath:kpiJson.json")
    private Resource kpiJsonResource;

    @Override
    public List<AdminKpiNormListResponse> list(AdminKpiNormListRequest request) {
        List<KpiNormClassify> list;
        if (StringUtils.isNotEmpty(request.getClassifyName())) {
            list = findLike(request.getClassifyName());
        } else {
            list = findNotLike();
        }
        List<AdminKpiNormListResponse> classifyList = list.stream().map(
                x -> AdminKpiNormListResponse
                        .builder()
                        .id(x.getId())
                        .parentId(x.getParentId())
                        .classifyName(x.getClassifyName())
                        .levelNum(x.getLevelNum())
                        .build())
                .collect(Collectors.toList());
        return AdminKpiNormListResponse.buildTree(classifyList, 0L);
    }

    /**
     * 模糊查询
     */
    private List<KpiNormClassify> findLike(String classifyName) {
        List<KpiNormClassify> list = new ArrayList<>();
        LambdaQueryWrapper<KpiNormClassify> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(KpiNormClassify::getDeleted, false);
        queryWrapper.like(KpiNormClassify::getClassifyName, classifyName);
        List<KpiNormClassify> classifyList = kpiNormClassifyDao.selectList(queryWrapper);
        if (CollectionUtils.isNotEmpty(classifyList)) {
            Set<String> parentIds = new HashSet<>();
            for (KpiNormClassify classify : classifyList) {
                String[] split = classify.getParentUrl().split("/");
                String key = "/" + split[2] + "/";
                parentIds.add(key);
            }
            LambdaQueryWrapper<KpiNormClassify> queryWrapper1 = new LambdaQueryWrapper<>();
            queryWrapper1.eq(KpiNormClassify::getDeleted, false);
            List<KpiNormClassify> classifyLists = kpiNormClassifyDao.selectList(queryWrapper1);
            for (KpiNormClassify classify : classifyLists) {
                for (String parentId : parentIds) {
                    if (classify.getParentUrl().contains(parentId)) {
                        list.add(classify);
                    }
                }
            }
        }
        return list;
    }

    /**
     * 不模糊查询
     */
    private List<KpiNormClassify> findNotLike() {
        LambdaQueryWrapper<KpiNormClassify> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(KpiNormClassify::getDeleted, false);
        return kpiNormClassifyDao.selectList(queryWrapper);
    }

    @Override
    public boolean saveOrUpdate(AdminKpiNormSaveUpdateRequest request) {
        //判断参数
        boolean flag;
        int count = 1;
        Long organizationId = threadLocalUtil.getOrganizationId();
        Long parentId = request.getParentId() == null ? 0L : request.getParentId();
        KpiNormClassify parentClassify = kpiNormClassifyDao.selectById(parentId);
        String parentUrl;
        Integer levelNum = 1;
        if (parentClassify == null) {
            parentUrl = "/0/";
        } else {
            parentUrl = parentClassify.getParentUrl();
            levelNum = parentClassify.getLevelNum() + 1;
        }
        //判断是否名称重复
        LambdaQueryWrapper<KpiNormClassify> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(KpiNormClassify::getDeleted, false);
        queryWrapper.eq(KpiNormClassify::getClassifyName, request.getClassifyName());
        queryWrapper.eq(KpiNormClassify::getParentId, parentId);
        List<KpiNormClassify> classifyList = kpiNormClassifyDao.selectList(queryWrapper);
        if (CollectionUtils.isNotEmpty(classifyList) && !classifyList.get(0).getId().equals(request.getId())) {
            throw new BaseException("类别已存在");
        }
        KpiNormClassify classify = new KpiNormClassify();
        classify.setOrganizationId(organizationId);
        classify.setDeleted(false);
        classify.setClassifyName(request.getClassifyName());
        classify.setParentId(request.getParentId());
        classify.setParentUrl(parentUrl);
        classify.setLevelNum(levelNum);
        //新增
        if (request.getId() == null) {
            kpiNormClassifyDao.insert(classify);
        } else {
            classify.setId(request.getId());
        }
        classify.setParentUrl(classify.getParentUrl() + classify.getId() + "/");
        flag = count == kpiNormClassifyDao.updateById(classify);
        return flag;
    }

    @Override
    public AdminKpiNormDetailResponse detail(Long id) {
        if (id == null) {
            throw new BaseException("id不能为空");
        }
        KpiNormClassify kpiNormClassify = kpiNormClassifyDao.selectById(id);
        return AdminKpiNormDetailResponse
                .builder()
                .id(kpiNormClassify.getId())
                .classifyName(kpiNormClassify.getClassifyName())
                .parentId(kpiNormClassify.getParentId())
                .levelNum(kpiNormClassify.getLevelNum())
                .build();
    }

    @Override
    public boolean delete(Long id) {
        if (id == null) {
            throw new BaseException("id不能为空");
        }
        LambdaQueryWrapper<KpiNormClassify> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(KpiNormClassify::getDeleted, false);
        queryWrapper.ne(KpiNormClassify::getId, id);
        queryWrapper.like(KpiNormClassify::getParentUrl, id);
        List<KpiNormClassify> classifyList = kpiNormClassifyDao.selectList(queryWrapper);
        if (CollectionUtils.isNotEmpty(classifyList)) {
            throw new BaseException("该类别还存在下级类别");
        }
        LambdaQueryWrapper<KpiNormInfo> queryWrapper1 = new LambdaQueryWrapper<>();
        queryWrapper1.eq(KpiNormInfo::getDeleted, false);
        queryWrapper1.eq(KpiNormInfo::getNormClassifyId, id);
        List<KpiNormInfo> normInfoList = kpiNormInfoDao.selectList(queryWrapper1);
        if (CollectionUtils.isNotEmpty(normInfoList)) {
            throw new BaseException("该类别已被指标绑定");
        }
        KpiNormClassify eduPlanClassify = new KpiNormClassify();
        eduPlanClassify.setId(id);
        eduPlanClassify.setDeleted(true);
        return kpiNormClassifyDao.updateById(eduPlanClassify) > 0;
    }

    @Override
    public List<AdminKpiNormListResponse> selectList() {
        List<KpiNormClassify> list = findNotLike();
        List<AdminKpiNormListResponse> classifyList = list.stream().map(
                x -> AdminKpiNormListResponse
                        .builder()
                        .id(x.getId())
                        .parentId(x.getParentId())
                        .classifyName(x.getClassifyName())
                        .levelNum(x.getLevelNum())
                        .build())
                .collect(Collectors.toList());
        return AdminKpiNormListResponse.buildTree(classifyList, 0L);
    }

    @Override
    public IPage<AdminKpiNormInfoListResponse> normPageList(AdminKpiNormInfoListRequest request) {
        //分页
        Page<KpiNormInfo> page = new Page<>(request.getPageNum(), request.getPageSize());
        //组装条件
        LambdaQueryWrapper<KpiNormInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(KpiNormInfo::getNormStatus).orderByAsc(KpiNormInfo::getNormClassifyId).orderByAsc(KpiNormInfo::getSort).orderByDesc(KpiNormInfo::getCreateTime);
        queryWrapper.eq(KpiNormInfo::getDeleted, false);
        List<Long> ids = new ArrayList<>();
        if (request.getNormClassifyId() != null) {
            LambdaQueryWrapper<KpiNormClassify> queryWrapper1 = new LambdaQueryWrapper<>();
            queryWrapper1.eq(KpiNormClassify::getDeleted, false);
            queryWrapper1.like(KpiNormClassify::getParentUrl, "/" + request.getNormClassifyId() + "/");
            ids = kpiNormClassifyDao.selectList(queryWrapper1).stream().map(KpiNormClassify::getId).collect(Collectors.toList());
        }
        queryWrapper.in(CollectionUtils.isNotEmpty(ids), KpiNormInfo::getNormClassifyId, ids);
        queryWrapper.eq(request.getNormStatus() != null, KpiNormInfo::getNormStatus, request.getNormStatus());
        queryWrapper.like(StringUtils.isNotEmpty(request.getNormName()), KpiNormInfo::getNormName, request.getNormName());
        Page<KpiNormInfo> list = kpiNormInfoDao.selectPage(page, queryWrapper);
        //指标类别id
        List<Long> classifyIds = list.getRecords().stream().map(KpiNormInfo::getNormClassifyId).distinct().collect(Collectors.toList());
        //查询指标类别转换map
        Map<Long, String> classifyMap = getClassifyMap(classifyIds);
        return list.convert(date -> {
            AdminKpiNormInfoListResponse vo = new AdminKpiNormInfoListResponse();
            BeanUtils.copyProperties(date, vo);
            vo.setNormClassifyName(classifyMap.get(date.getNormClassifyId()));
            return vo;
        });
    }

    private Map<Long, String> getClassifyMap(List<Long> classifyIds) {
        Map<Long, String> map = new HashMap<>();
        if (CollectionUtils.isNotEmpty(classifyIds)) {
            LambdaQueryWrapper<KpiNormClassify> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(KpiNormClassify::getDeleted, false);
            queryWrapper.in(KpiNormClassify::getId, classifyIds);
            map = kpiNormClassifyDao.selectList(queryWrapper).stream().collect(Collectors.toMap(KpiNormClassify::getId, KpiNormClassify::getClassifyName));
        }
        return map;
    }


    @Override
    public boolean normSaveUpdate(AdminKpiNormInfoSaveRequest request) {
        //判断是否名称重复
        LambdaQueryWrapper<KpiNormInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(KpiNormInfo::getDeleted, false);
        queryWrapper.eq(KpiNormInfo::getNormName, request.getNormName());
        List<KpiNormInfo> normInfoList = kpiNormInfoDao.selectList(queryWrapper);
        if (CollectionUtils.isNotEmpty(normInfoList) && !normInfoList.get(0).getId().equals(request.getId())) {
            throw new BaseException("指标名称已存在");
        }
        if (request.getId() == null) { //新增
            normSave(request);
        } else { //修改
            normUpdate(request);
        }
        return true;
    }

    //修改
    private void normUpdate(AdminKpiNormInfoSaveRequest request) {
        Long userId = threadLocalUtil.getUserId();
        Date date = new Date();
        //保存指标
        KpiNormInfo norm = KpiNormInfo
                .builder()
                .id(request.getId())
                .updateId(userId)
                .updateTime(date)
                .normName(request.getNormName())
                .normClassifyId(request.getNormClassifyId())
                .normSign(request.getNormSign())
                .normIndicatorType(request.getNormIndicatorType())
                .normDimensions(request.getNormDimensions())
                .normUnit(request.getNormUnit())
                .sort(request.getSort())
                .formulaDesc(request.getFormulaDesc())
                .normDesc(request.getNormDesc())
                .decimalDigit(request.getDecimalDigit())
                .normDimensionsSort(request.getNormDimensionsSort())
                .build();
        kpiNormInfoDao.updateById(norm);
        //删除原指标数据源
        LambdaQueryWrapper<KpiNormInfoFormula> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(KpiNormInfoFormula::getNormId, request.getId());
        kpiNormInfoFormulaDao.delete(queryWrapper);
        //保存指标公式
        List<KpiNormInfoFormula> formulaList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(request.getDataIds())) {
            for (int i = 1; i <= request.getDataIds().size(); i++) {
                KpiNormInfoFormula vo = new KpiNormInfoFormula();
                vo.setNormId(norm.getId());
                vo.setDataId(request.getDataIds().get(i - 1).getDataId());
                vo.setSort(request.getDataIds().get(i - 1).getSort());
                vo.setNumber(request.getDataIds().get(i - 1).getNumber());
                formulaList.add(vo);
            }
        }
        if (CollectionUtils.isNotEmpty(formulaList)) {
            kpiNormInfoFormulaDao.insertList(formulaList);
        }
    }


    //新增
    private void normSave(AdminKpiNormInfoSaveRequest request) {
        Long userId = threadLocalUtil.getUserId();
        Long organizationId = threadLocalUtil.getOrganizationId();
        Date date = new Date();
        //保存指标
        KpiNormInfo norm = KpiNormInfo
                .builder()
                .createId(userId)
                .createTime(date)
                .organizationId(organizationId)
                .deleted(false)
                .normName(request.getNormName())
                .normCode(getNormCode(request.getNormClassifyId(), date))
                .normClassifyId(request.getNormClassifyId())
                .normSign(request.getNormSign())
                .normIndicatorType(request.getNormIndicatorType())
                .normDimensions(request.getNormDimensions())
                .normUnit(request.getNormUnit())
                .sort(request.getSort())
                .formulaDesc(request.getFormulaDesc())
                .normDesc(request.getNormDesc())
                .decimalDigit(request.getDecimalDigit())
                .normDimensionsSort(request.getNormDimensionsSort())
                .build();
        kpiNormInfoDao.insert(norm);
        //保存指标公式
        List<KpiNormInfoFormula> formulaList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(request.getDataIds())) {
            for (int i = 1; i <= request.getDataIds().size(); i++) {
                KpiNormInfoFormula vo = new KpiNormInfoFormula();
                vo.setNormId(norm.getId());
                vo.setDataId(request.getDataIds().get(i - 1).getDataId());
                vo.setSort(request.getDataIds().get(i - 1).getSort());
                vo.setNumber(request.getDataIds().get(i - 1).getNumber());
                formulaList.add(vo);
            }
        }
        if (CollectionUtils.isNotEmpty(formulaList)) {
            kpiNormInfoFormulaDao.insertList(formulaList);
        }
    }

    private String getNormCode(Long normClassifyId, Date date) {
        //指标类别首字母
        String classifyName = kpiNormClassifyDao.selectById(normClassifyId).getClassifyName();
        //添加时间
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        String strTime = format.format(date);
        //随机四位数
        String strNum = getFourNum();
        return getFirstChar(classifyName) + strTime + strNum;
    }

    private String getFourNum() {
        Random random = new Random();
        return random.nextInt(9000) + 1000 + "";
    }

    //获取首字母
    private String getFirstChar(String str) {
        char[] charArray = str.toCharArray();
        StringBuilder sb = new StringBuilder();
        for (char c : charArray) {
            String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(c);
            if (pinyinArray != null) {
                sb.append(pinyinArray[0].charAt(0));
            } else {
                sb.append(c);
            }
        }
        return sb.toString().toUpperCase();
    }

    @Override
    public AdminKpiNormInfoDetailResponse normDetail(Long id) {
        KpiNormInfo normInfo = kpiNormInfoDao.selectById(id);
        return AdminKpiNormInfoDetailResponse
                .builder()
                .id(normInfo.getId())
                .normName(normInfo.getNormName())
                .normCode(normInfo.getNormCode())
                .normClassifyIds(getNormClassifyIds(normInfo.getNormClassifyId()))
                //.normClassifyName(kpiNormClassifyDao.selectById(normInfo.getNormClassifyId()).getClassifyName())
                .normSign(normInfo.getNormSign())
                .normIndicatorType(normInfo.getNormIndicatorType())
                .normDimensions(normInfo.getNormDimensions())
                .normUnit(normInfo.getNormUnit())
                .sort(normInfo.getSort())
                .formalList(getFormalList(id))
                .formulaDesc(normInfo.getFormulaDesc())
                .normDesc(normInfo.getNormDesc())
                .normStatus(normInfo.getNormStatus())
                .decimalDigit(normInfo.getDecimalDigit())
                .normDimensionsSort(normInfo.getNormDimensionsSort())
                .build();
    }

    private List<Long> getNormClassifyIds(Long normClassifyId) {
        List<Long> list = new ArrayList<>();
        KpiNormClassify kpiNormClassify = kpiNormClassifyDao.selectById(normClassifyId);
        String parentUrl = kpiNormClassify.getParentUrl();
        String[] split = parentUrl.split("/");
        for (String s : split) {
            if (StringUtils.isNotBlank(s) && !"0".equals(s)) {
                list.add(Long.parseLong(s));
            }
        }
        return list;
    }


    //查询公式数据源
    private List<AdminKpiNormFormalResponse> getFormalList(Long id) {
        LambdaQueryWrapper<KpiNormInfoFormula> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(KpiNormInfoFormula::getNormId, id);
        List<KpiNormInfoFormula> formulaList = kpiNormInfoFormulaDao.selectList(queryWrapper);
        List<Long> dataIds = formulaList.stream().map(KpiNormInfoFormula::getDataId).distinct().collect(Collectors.toList());
        //查询数据源map
        Map<Long, String> dataMap = getDataMap(dataIds);
        return formulaList.stream().map(x -> AdminKpiNormFormalResponse
                .builder()
                .id(x.getDataId())
                .dataName(dataMap.get(x.getDataId()))
                .sort(x.getSort())
                .number(x.getNumber())
                .build()).collect(Collectors.toList());
    }

    private Map<Long, String> getDataMap(List<Long> dataIds) {
        Map<Long, String> map = new HashMap<>();
        if (CollectionUtils.isNotEmpty(dataIds)) {
            LambdaQueryWrapper<KpiData> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(KpiData::getDeleted, false);
            queryWrapper.in(KpiData::getId, dataIds);
            map = kpiDataDao.selectList(queryWrapper).stream().collect(Collectors.toMap(KpiData::getId, KpiData::getDataName));
        }
        return map;
    }

    @Override
    public boolean normDelete(Long id) {
        KpiNormInfo normInfo = kpiNormInfoDao.selectById(id);
        if (2 == normInfo.getNormStatus()) {
            throw new BaseException("该指标还未停用");
        }
        LambdaQueryWrapper<KpiBenchmarkInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(KpiBenchmarkInfo::getDeleted, false);
        queryWrapper.eq(KpiBenchmarkInfo::getNormId, id);
        List<KpiBenchmarkInfo> list = kpiBenchmarkInfoDao.selectList(queryWrapper);
        if (CollectionUtils.isNotEmpty(list)) {
            throw new BaseException("该指标已被标杆绑定");
        }
        normInfo.setDeleted(true);
        return kpiNormInfoDao.updateById(normInfo) == 1;
    }

    @Override
    public List<KpiNormSelectResponse> normSelect(AdminKpiSelectRequest request) {
        List<KpiNormInfo> list = kpiNormInfoDao.selectList(KpiNormInfo.getWrapper()
                .eq(StringUtils.isNotEmpty(request.getNormSign()), KpiNormInfo::getNormSign, request.getNormSign())
                .eq(StringUtils.isNotEmpty(request.getNormIndicatorType()), KpiNormInfo::getNormIndicatorType, request.getNormIndicatorType())
                .eq(KpiNormInfo::getDeleted, false));
        return list.stream()
                .map(x -> KpiNormSelectResponse.builder()
                        .id(x.getId())
                        .name(x.getNormName())
                        .status(x.getNormStatus() == 1 ? 0 : 1)
                        .normUnit(x.getNormUnit())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public boolean normStatusUpdate(AdminKpiNormStatusRequest request) {
        KpiNormInfo kpiNormInfo = new KpiNormInfo();
        kpiNormInfo.setId(request.getId());
        kpiNormInfo.setNormStatus(request.getNormStatus());
        return kpiNormInfoDao.updateById(kpiNormInfo) == 1;
    }

    @Override
    public IPage<AdminKpiDataListResponse> dataList(AdminKpiDataListRequest request) {
        Page<KpiData> list = kpiDataDao.selectPage(new Page<>(request.getPageNum(), request.getPageSize()), KpiData.getWrapper()
                .orderByAsc(KpiData::getStatus)
                .orderByDesc(KpiData::getCreateTime)
                .eq(KpiData::getDeleted, false)
                .eq(KpiData::getDataType, 1)
                .like(StringUtils.isNotEmpty(request.getDataName()), KpiData::getDataName, request.getDataName())
                .eq(request.getStatus() != null, KpiData::getStatus, request.getStatus()));
        return list.convert(po -> {
            AdminKpiDataListResponse vo = new AdminKpiDataListResponse();
            BeanUtils.copyProperties(po, vo);
            return vo;
        });
    }

    @Override
    public List<SelectResponse> dataSelect() {
        return kpiDataDao.selectList(KpiData.getWrapper()
                .eq(KpiData::getDeleted, false)
                .eq(KpiData::getDataType, 1)
                .eq(KpiData::getStatus, 1))
                .stream()
                .map(x -> SelectResponse.builder()
                        .id(x.getId())
                        .name(x.getDataName())
                        .status(1)
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public List<SelectResponse> operatorSelect() {
        return kpiDataDao.selectList(KpiData.getWrapper()
                .eq(KpiData::getDeleted, false)
                .eq(KpiData::getDataType, 2)
                .eq(KpiData::getStatus, 1))
                .stream()
                .map(x -> SelectResponse.builder()
                        .id(x.getId())
                        .name(x.getDataName())
                        .status(1)
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public AdminKpiDataListResponse dataDetail(Long id) {
        KpiData po = kpiDataDao.selectById(id);
        AdminKpiDataListResponse vo = new AdminKpiDataListResponse();
        BeanUtils.copyProperties(po, vo);
        return vo;
    }

    @Override
    public boolean dataDelete(Long id) {
        return kpiDataDao.updateById(KpiData.builder()
                .id(id)
                .deleted(true)
                .build()) == 1;
    }

    @Override
    public boolean dataStartStop(AdminKpiDataStopRequest request) {
        if (request.getId() == null) {
            throw new BaseException("ID不能为空");
        }
        return kpiDataDao.updateById(KpiData.builder()
                .id(request.getId())
                .status(request.getStatus())
                .build()) > 0;
    }

    @Override
    public IPage<AdminKpiDetailListResponse> dataDetailList(AdminKpiDetailListRequest request) {
        Page<KpiDataDetail> list = kpiDataDetailDao.selectPage(new Page<>(request.getPageNum(), request.getPageSize()), KpiDataDetail.getWrapper()
                .eq(KpiDataDetail::getDeleted, false)
                .eq(KpiDataDetail::getKpiDataId, request.getKpiDataId())
                .and(StringUtils.isNotEmpty(request.getCompanyName()), wq -> wq.like(KpiDataDetail::getCompanyName, request.getCompanyName())
                        .or().like(KpiDataDetail::getStreet, request.getCompanyName()))
                .between(request.getStartTime() != null && request.getEndTime() != null, KpiDataDetail::getDataDate, request.getStartTime(), request.getEndTime()));
        return list.convert(po -> {
            AdminKpiDetailListResponse vo = new AdminKpiDetailListResponse();
            BeanUtils.copyProperties(po, vo);
            return vo;
        });
    }

    @Override
    public IPage<AdminKpiBenchmarkInfoListResponse> benchmarkInfoList(AdminKpiBenchmarkInfoListRequest request) {
        List<Long> normId = new ArrayList<>();
        if (ObjectUtil.isNotEmpty(request.getNormName())) {
            normId = kpiNormInfoDao.selectList(KpiNormInfo.getWrapper().eq(KpiNormInfo::getDeleted, false).like(KpiNormInfo::getNormName, request.getNormName()))
                    .stream()
                    .map(KpiNormInfo::getId)
                    .collect(Collectors.toList());
            if (normId.size() <= 0) {
                return new Page<>();
            }
        }
        Page<KpiBenchmarkInfo> list = kpiBenchmarkInfoDao.selectPage(new Page<>(request.getPageNum(), request.getPageSize()), KpiBenchmarkInfo.getWrapper()
                .orderByAsc(KpiBenchmarkInfo::getStatus)
                .orderByDesc(KpiBenchmarkInfo::getCreateTime)
                .eq(KpiBenchmarkInfo::getDeleted, false)
                .in(normId.size() > 0, KpiBenchmarkInfo::getNormId, normId)
                .eq(ObjectUtil.isNotEmpty(request.getStatus()), KpiBenchmarkInfo::getStatus, request.getStatus()));
        Map<Long, KpiNormInfo> map = new HashMap<>();
        if (list.getRecords().size() > 0) {
            List<Long> normIds = list.getRecords().stream().map(KpiBenchmarkInfo::getNormId).collect(Collectors.toList());
            map = kpiNormInfoDao.selectList(KpiNormInfo.getWrapper().eq(KpiNormInfo::getDeleted, false).in(KpiNormInfo::getId, normIds))
                    .stream()
                    .collect(Collectors.toMap(KpiNormInfo::getId, KpiNormInfo -> KpiNormInfo, (a, b) -> b));
        }
        Map<Long, KpiNormInfo> finalMap = map;
        return list.convert(po -> {
            AdminKpiBenchmarkInfoListResponse vo = new AdminKpiBenchmarkInfoListResponse();
            BeanUtils.copyProperties(po, vo);
            if (finalMap.containsKey(po.getNormId())) {
                KpiNormInfo kpiNormInfo = finalMap.get(po.getNormId());
                vo.setNormCode(kpiNormInfo.getNormCode());
                vo.setNormName(kpiNormInfo.getNormName());
            }
            return vo;
        });
    }

    @Override
    public boolean benchmarkInfoSave(AdminKpiBenchmarkInfoSaveRequest request) {
        //指标是否存在
        LambdaQueryWrapper<KpiBenchmarkInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(KpiBenchmarkInfo::getDeleted, false);
        queryWrapper.eq(KpiBenchmarkInfo::getNormId, request.getNormId());
        List<KpiBenchmarkInfo> normInfoList = kpiBenchmarkInfoDao.selectList(queryWrapper);
        if (CollectionUtils.isNotEmpty(normInfoList) && !normInfoList.get(0).getId().equals(request.getId())) {
            throw new BaseException("指标已存在");
        }

        KpiBenchmarkInfo info = new KpiBenchmarkInfo();
        BeanUtils.copyProperties(request, info);
        info.setStatus(2);
        if (request.getBenchmarkAlarm().size() < 1) {
            throw new BaseException("标杆值/指标告警阈值为空！");
        }
        if (request.getBenchmarkType() == 1) {
            request.getBenchmarkAlarm().forEach(x -> {
                if (x.getBenchmarkAlarmDetail().size() <= 0) {
                    throw new BaseException("公式为空！");
                }
            });
        } else {
            request.getBenchmarkAlarm().forEach(x -> {
                if (x.getValue().scale() == 0) {
                    if (x.getValue().toPlainString().length() > 7) {
                        throw new BaseException("位数过长！");
                    }
                } else {
                    if (x.getValue().scale() > 9 || (x.getValue().toString().length() - x.getValue().scale() - 1) > 7) {
                        System.out.println(x.getValue().scale());
                        System.out.println(x.getValue().toPlainString().length());
                        throw new BaseException("位数过长！");
                    }
                }
            });
        }
        Map<Integer, List<AdminKpiBenchmarkAlarmDetailRequest>> addMap = new HashMap<>();
        if (ObjectUtil.isNotEmpty(request.getId())) {
            //修改
            kpiBenchmarkInfoDao.updateById(info);
            List<KpiBenchmarkAlarm> alarmList = kpiBenchmarkAlarmDao.selectList(KpiBenchmarkAlarm.getWrapper()
                    .eq(KpiBenchmarkAlarm::getBenchmarkId, info.getId()));
            List<Long> alarmIds = new ArrayList<>();
            for (KpiBenchmarkAlarm alarm : alarmList) {
                alarmIds.add(alarm.getId());
            }
            //全部告警值逻辑删除
            kpiBenchmarkAlarmDao.update(KpiBenchmarkAlarm.builder()
                            .deleted(true).build(),
                    KpiBenchmarkAlarm.getWrapper().eq(KpiBenchmarkAlarm::getDeleted, false)
                            .eq(KpiBenchmarkAlarm::getBenchmarkId, info.getId()));
            //删除公式
            kpiBenchmarkAlarmDetailDao.delete(KpiBenchmarkAlarmDetail.getWrapper().in(KpiBenchmarkAlarmDetail::getAlarmId, alarmIds));
            List<AdminKpiBenchmarkAlarmRequest> alarmRequests = request.getBenchmarkAlarm();
            List<KpiBenchmarkAlarm> add = new ArrayList<>();
            List<KpiBenchmarkAlarm> update = new ArrayList<>();
            if (alarmList.size() >= request.getBenchmarkAlarm().size()) {
                for (int i = 0; i < alarmRequests.size(); i++) {
                    KpiBenchmarkAlarm alarm = new KpiBenchmarkAlarm();
                    BeanUtils.copyProperties(alarmRequests.get(i), alarm);
                    alarm.setBenchmarkId(info.getId());
                    alarm.setDeleted(false);
                    alarm.setId(alarmList.get(i).getId());
                    update.add(alarm);
                    if (info.getBenchmarkType() == 1) {
                        addMap.put(alarmRequests.get(i).getGrade(), alarmRequests.get(i).getBenchmarkAlarmDetail());
                    }
                }

            }
            if (alarmList.size() < request.getBenchmarkAlarm().size()) {
                for (int i = 0; i < alarmRequests.size(); i++) {
                    KpiBenchmarkAlarm alarm = new KpiBenchmarkAlarm();
                    if (alarmList.size() > i) {
                        BeanUtils.copyProperties(alarmRequests.get(i), alarm);
                        alarm.setBenchmarkId(info.getId());
                        alarm.setDeleted(false);
                        alarm.setId(alarmList.get(i).getId());
                        update.add(alarm);
                    } else {
                        BeanUtils.copyProperties(alarmRequests.get(i), alarm);
                        alarm.setBenchmarkId(info.getId());
                        add.add(alarm);
                    }
                    if (info.getBenchmarkType() == 1) {
                        addMap.put(alarmRequests.get(i).getGrade(), alarmRequests.get(i).getBenchmarkAlarmDetail());
                    }

                }
            }
            //批量新增
            if (add.size() > 0) {
                kpiBenchmarkAlarmDao.insertList(add);
            }
            //批量修改
            if (update.size() > 0) {
                kpiBenchmarkAlarmDao.updateList(update);
            }
            //公式批量添加
            if (addMap.size() > 0) {
                List<KpiBenchmarkAlarm> all = new ArrayList<>();
                all.addAll(add);
                all.addAll(update);
                List<KpiBenchmarkAlarmDetail> detailAdd = new ArrayList<>();
                for (KpiBenchmarkAlarm addAlarm : all) {
                    if (addMap.containsKey(addAlarm.getGrade())) {
                        List<AdminKpiBenchmarkAlarmDetailRequest> detailRequests = addMap.get(addAlarm.getGrade());
                        detailAdd.addAll(detailRequests.stream().map(x -> {
                            KpiBenchmarkAlarmDetail detail = new KpiBenchmarkAlarmDetail();
                            BeanUtils.copyProperties(x, detail);
                            detail.setAlarmId(addAlarm.getId());
                            return detail;
                        }).collect(Collectors.toList()));
                    }
                }
                //新增公式
                if (detailAdd.size() > 0) {
                    kpiBenchmarkAlarmDetailDao.insertList(detailAdd);
                }
            }
        } else {
            //新增
            kpiBenchmarkInfoDao.insert(info);
            List<AdminKpiBenchmarkAlarmRequest> alarmRequests = request.getBenchmarkAlarm();
            List<KpiBenchmarkAlarm> add = alarmRequests.stream().map(x -> {
                KpiBenchmarkAlarm alarm = new KpiBenchmarkAlarm();
                BeanUtils.copyProperties(x, alarm);
                alarm.setBenchmarkId(info.getId());
                if (info.getBenchmarkType() == 1) {
                    addMap.put(x.getGrade(), x.getBenchmarkAlarmDetail());
                }
                return alarm;
            }).collect(Collectors.toList());
            kpiBenchmarkAlarmDao.insertList(add);
            //新增公式
            if (addMap.size() > 0) {
                List<KpiBenchmarkAlarm> all = new ArrayList<>();
                all.addAll(add);
                List<KpiBenchmarkAlarmDetail> detailAdd = new ArrayList<>();
                for (KpiBenchmarkAlarm addAlarm : all) {
                    if (addMap.containsKey(addAlarm.getGrade())) {
                        List<AdminKpiBenchmarkAlarmDetailRequest> detailRequests = addMap.get(addAlarm.getGrade());
                        detailAdd.addAll(detailRequests.stream().map(x -> {
                            KpiBenchmarkAlarmDetail detail = new KpiBenchmarkAlarmDetail();
                            BeanUtils.copyProperties(x, detail);
                            detail.setAlarmId(addAlarm.getId());
                            return detail;
                        }).collect(Collectors.toList()));
                    }
                }
                //新增公式
                if (detailAdd.size() > 0) {
                    kpiBenchmarkAlarmDetailDao.insertList(detailAdd);
                }
            }
        }
        return true;
    }

    @Override
    public AdminKpiBenchmarkInfoDetailResponse benchmarkInfoDetail(IdRequest request) {
        AdminKpiBenchmarkInfoDetailResponse response = new AdminKpiBenchmarkInfoDetailResponse();
        request.toRequestCheck();
        KpiBenchmarkInfo info = kpiBenchmarkInfoDao.selectById(request.getId());
        BeanUtils.copyProperties(info, response);
        List<Long> alarmIds = new ArrayList<>();
        List<AdminKpiBenchmarkAlarmResponse> alarmResponses = kpiBenchmarkAlarmDao.selectList(KpiBenchmarkAlarm.getWrapper()
                .eq(KpiBenchmarkAlarm::getDeleted, false)
                .eq(KpiBenchmarkAlarm::getBenchmarkId, info.getId())).stream().map(x -> {
            AdminKpiBenchmarkAlarmResponse alarmResponse = new AdminKpiBenchmarkAlarmResponse();
            BeanUtils.copyProperties(x, alarmResponse);
            alarmIds.add(x.getId());
            return alarmResponse;
        }).collect(Collectors.toList());
        if (info.getBenchmarkType() == 1) {
            List<KpiBenchmarkAlarmDetail> alarmDetails = kpiBenchmarkAlarmDetailDao.selectList(KpiBenchmarkAlarmDetail.getWrapper().in(KpiBenchmarkAlarmDetail::getAlarmId, alarmIds));
            List<Long> dataIds = alarmDetails.stream().map(KpiBenchmarkAlarmDetail::getDataId).distinct().collect(Collectors.toList());
            Map<Long, String> dataMap = kpiDataDao.selectList(KpiData.getWrapper().in(KpiData::getId, dataIds)).stream().collect(Collectors.toMap(KpiData::getId, KpiData::getDataName, (a, b) -> b));
            Map<Long, List<KpiBenchmarkAlarmDetail>> map = alarmDetails.stream().collect(Collectors.groupingBy(KpiBenchmarkAlarmDetail::getAlarmId));
            alarmResponses.forEach(x -> {
                if (map.containsKey(x.getId())) {
                    x.setBenchmarkAlarmDetail(map.get(x.getId()).stream().map(o -> {
                        AdminKpiBenchmarkAlarmDetailResponse alarmDetailResponse = new AdminKpiBenchmarkAlarmDetailResponse();
                        BeanUtils.copyProperties(o, alarmDetailResponse);
                        alarmDetailResponse.setDataName(dataMap.getOrDefault(o.getDataId(), ""));
                        return alarmDetailResponse;
                    }).collect(Collectors.toList()));
                }
            });
        }
        response.setBenchmarkAlarm(alarmResponses);
        return response;
    }

    @Override
    public boolean benchmarkInfoStartStop(AdminKpiBenchmarkInfoStopRequest request) {
        if (request.getId() == null) {
            throw new BaseException("ID不能为空");
        }
        return kpiBenchmarkInfoDao.updateById(KpiBenchmarkInfo.builder()
                .id(request.getId())
                .status(request.getStatus())
                .build()) > 0;
    }

    @Override
    public boolean benchmarkInfoDelete(IdRequest request) {
        request.toRequestCheck();
        KpiBenchmarkInfo info = kpiBenchmarkInfoDao.selectById(request.getId());
        if (info.getStatus() == 2) {
            kpiBenchmarkInfoDao.updateById(KpiBenchmarkInfo.builder()
                    .id(request.getId())
                    .deleted(true).build());
            List<Long> alarmIds = kpiBenchmarkAlarmDao.selectList(KpiBenchmarkAlarm.getWrapper().eq(KpiBenchmarkAlarm::getDeleted, false).eq(KpiBenchmarkAlarm::getBenchmarkId, request.getId()))
                    .stream()
                    .map(x -> x.getId())
                    .collect(Collectors.toList());
            kpiBenchmarkAlarmDao.update(KpiBenchmarkAlarm.builder()
                            .deleted(true)
                            .build(),
                    KpiBenchmarkAlarm.getWrapper().eq(KpiBenchmarkAlarm::getBenchmarkId, request.getId()));
            if (info.getBenchmarkType() == 1) {
                kpiBenchmarkAlarmDetailDao.delete(KpiBenchmarkAlarmDetail.getWrapper().in(KpiBenchmarkAlarmDetail::getAlarmId, alarmIds));
            }
            return true;
        }
        throw new BaseException("只有停用状态能删除！");
    }

    @Override
    public BiKpiNormVo findByCode(BiKpiNormRequest request) {
        BiKpiNormVo data = BiKpiNormVo.builder()
                .normResult(BigDecimal.ZERO)
                .lastNormResult(BigDecimal.ZERO)
                .floatRatio(BigDecimal.ZERO)
                .benchmarkFlag(false)
                .build();
        KpiNormInfo normInfo = kpiNormInfoDao.selectOne(KpiNormInfo.getWrapper()
                .eq(KpiNormInfo::getDeleted, false)
                .eq(KpiNormInfo::getNormCode, request.getNormCode())
                .eq(KpiNormInfo::getNormStatus, 2)
                .last("limit 1"));
        if (normInfo != null) {
            data.setNormUnit(normInfo.getNormUnit());
            TblDictSubset dictSubset = dictSubsetDao.selectOne(TblDictSubset.getWrapper()
                    .eq(TblDictSubset::getDeleted, false)
                    .eq(TblDictSubset::getGroupKey, DictGroupEnum.GAS_NORM_UNIT.getKey())
                    .eq(TblDictSubset::getDictValue, normInfo.getNormUnit())
                    .last("limit 1"));
            if (dictSubset != null) {
                data.setNormUnitValue(dictSubset.getDictName());
            }
            //指标结果
            KpiNormResultVo normResultVo = kpiNormManager.computeNormById(normInfo.getId(), request.getStartTime(), request.getEndTime(), request.getCompanySearchFlag(), request.getStreetSearchFlag());
            data.setNormId(normInfo.getId());
            data.setNormResult(normResultVo.getNormResult());
            data.setNormResultByCompany(normResultVo.getNormResultByCompany());
            data.setNormResultByStreet(normResultVo.getNormResultByStreet());
//            data.setNormResultByCAS(normResultVo.getNormResultByCAS());
            //是否查询往期指标结果
            if (request.getLastSearchFlag()) {
                //往期指标结果
                Integer section = TimeChartUtil.getDateSection(request.getStartTime(), request.getEndTime());
                Date startTime = null;
                Date endTime = null;
                switch (section) {
                    case 1://上月
                        startTime = TimeChartUtil.getLastMonth(request.getStartTime());
                        endTime = TimeChartUtil.getLastMonth(request.getEndTime());
                        break;
                    case 2://上季度
                        startTime = TimeChartUtil.getLastQuarter(request.getStartTime());
                        endTime = TimeChartUtil.getLastQuarter(request.getEndTime());
                        break;
                    case 3://上年
                        startTime = TimeChartUtil.getLastYear(request.getStartTime());
                        endTime = TimeChartUtil.getLastYear(request.getEndTime());
                        break;
                }
                KpiNormResultVo lastNormResultVo = kpiNormManager.computeNormById(normInfo.getId(), startTime, endTime, request.getCompanySearchFlag(), request.getStreetSearchFlag());
                data.setLastNormResult(lastNormResultVo.getNormResult());
                //较往期浮动比例
                if (normInfo.getNormUnit().equals(NormUnitEnum.NORM_PERCENTAGE.getKey())) {
                    if (lastNormResultVo.getNormResult() != null && lastNormResultVo.getNormResult().compareTo(BigDecimal.ZERO) != 0) {
                        data.setFloatRatio(data.getNormResult()
                                .subtract(data.getLastNormResult())
                                .multiply(BigDecimal.valueOf(100))
                                .divide(data.getLastNormResult(), 2, RoundingMode.HALF_UP));
                    } else {
                        data.setFloatRatio(new BigDecimal("100"));
                    }
                } else {
                    boolean flag = lastNormResultVo.getNormResult() != null && lastNormResultVo.getNormResult().compareTo(BigDecimal.ZERO) != 0;
                    data.setFloatRatio(data.getNormResult().subtract(flag ? data.getLastNormResult() : BigDecimal.ZERO));
                }
                Map<String, BigDecimal> companyMap = lastNormResultVo.getNormResultByCompany().stream().collect(Collectors.toMap(KpiNormCompanyResultVo::getCompanyName, KpiNormCompanyResultVo::getNormResult, (a, b) -> b));
                Map<String, BigDecimal> streetMap = lastNormResultVo.getNormResultByStreet().stream().collect(Collectors.toMap(KpiNormStreetResultVo::getStreet, KpiNormStreetResultVo::getNormResult, (a, b) -> b));
//                Map<String, BigDecimal> casMap = lastNormResultVo.getNormResultByCAS().stream().collect(Collectors.toMap(x -> x.getCompanyName() + "_" + x.getStreet(), KpiNormCASResultVo::getNormResult, (a, b) -> b));
                for (KpiNormCompanyResultVo vo : data.getNormResultByCompany()) {
                    if (normInfo.getNormUnit().equals(NormUnitEnum.NORM_PERCENTAGE.getKey())) {
                        if (companyMap.containsKey(vo.getCompanyName()) && companyMap.get(vo.getCompanyName()).compareTo(BigDecimal.ZERO) != 0) {
                            vo.setLastNormResult(companyMap.get(vo.getCompanyName()));
                            vo.setFloatRatio(vo.getNormResult()
                                    .subtract(vo.getLastNormResult())
                                    .multiply(BigDecimal.valueOf(100))
                                    .divide(vo.getLastNormResult(), 2, RoundingMode.HALF_UP));
                        } else {
                            vo.setLastNormResult(BigDecimal.ZERO);
                            vo.setFloatRatio(new BigDecimal("100"));
                        }
                    } else {
                        boolean flag = companyMap.containsKey(vo.getCompanyName()) && companyMap.get(vo.getCompanyName()).compareTo(BigDecimal.ZERO) != 0;
                        vo.setLastNormResult(flag ? companyMap.get(vo.getCompanyName()) : BigDecimal.ZERO);
                        vo.setFloatRatio(vo.getNormResult().subtract(flag ? vo.getLastNormResult() : BigDecimal.ZERO));
                    }
                }
                for (KpiNormStreetResultVo vo : data.getNormResultByStreet()) {
                    if (normInfo.getNormUnit().equals(NormUnitEnum.NORM_PERCENTAGE.getKey())) {
                        if (streetMap.containsKey(vo.getStreet()) && streetMap.get(vo.getStreet()).compareTo(BigDecimal.ZERO) != 0) {
                            vo.setLastNormResult(streetMap.get(vo.getStreet()));
                            vo.setFloatRatio(vo.getNormResult()
                                    .subtract(vo.getLastNormResult())
                                    .multiply(BigDecimal.valueOf(100))
                                    .divide(vo.getLastNormResult(), 2, RoundingMode.HALF_UP));
                        } else {
                            vo.setLastNormResult(BigDecimal.ZERO);
                            vo.setFloatRatio(new BigDecimal("100"));
                        }
                    } else {
                        boolean flag = streetMap.containsKey(vo.getStreet()) && streetMap.get(vo.getStreet()).compareTo(BigDecimal.ZERO) != 0;
                        vo.setLastNormResult(flag ? streetMap.get(vo.getStreet()) : BigDecimal.ZERO);
                        vo.setFloatRatio(vo.getNormResult().subtract(flag ? vo.getLastNormResult() : BigDecimal.ZERO));
                    }
                }
//                for (KpiNormCASResultVo vo : data.getNormResultByCAS()) {
//                    String key = vo.getCompanyName() + "_" + vo.getStreet();
//                    if (casMap.containsKey(key) && casMap.get(key).compareTo(BigDecimal.ZERO) != 0) {
//                        vo.setLastNormResult(casMap.get(key));
//                        vo.setFloatRatio(vo.getNormResult()
//                                .subtract(vo.getLastNormResult())
//                                .multiply(BigDecimal.valueOf(100))
//                                .divide(vo.getLastNormResult(), 2, RoundingMode.HALF_UP));
//                    } else {
//                        vo.setLastNormResult(BigDecimal.ZERO);
//                        vo.setFloatRatio(new BigDecimal("100"));
//                    }
//                }
            }
            //是否查询标杆
            if (request.getBenchmarkSearchFlag()) {
                //标杆结果
                KpiNormBenchmarkVo benchmarkVo = kpiNormManager.computeNormBenchmarkById(normInfo.getId(), request.getStartTime(), request.getEndTime(), normInfo.getDecimalDigit());
                if (benchmarkVo.getValue() != null) {
                    data.setBenchmarkFlag(true);
                    data.setValue(benchmarkVo.getValue());
                    data.setSymbol(benchmarkVo.getSymbol());
                    data.setAlarmList(benchmarkVo.getAlarmList());
                }
            }
            if (data.getNormResultByCompany() != null && data.getNormResultByCompany().size() > 0) {
                if (normInfo.getNormDimensionsSort() == null || normInfo.getNormDimensionsSort() == 0) {//倒序
                    data.setNormResultByCompany(data.getNormResultByCompany().stream().sorted(Comparator.comparing(KpiNormCompanyResultVo::getNormResult).reversed()).collect(Collectors.toList()));
                } else {//顺序
                    data.setNormResultByCompany(data.getNormResultByCompany().stream().sorted(Comparator.comparing(KpiNormCompanyResultVo::getNormResult)).collect(Collectors.toList()));
                }
            }
            if (data.getNormResultByStreet() != null && data.getNormResultByStreet().size() > 0) {
                if (normInfo.getNormDimensionsSort() == null || normInfo.getNormDimensionsSort() == 0) {//倒序
                    data.setNormResultByStreet(data.getNormResultByStreet().stream().sorted(Comparator.comparing(KpiNormStreetResultVo::getNormResult).reversed()).collect(Collectors.toList()));
                } else {//顺序
                    data.setNormResultByStreet(data.getNormResultByStreet().stream().sorted(Comparator.comparing(KpiNormStreetResultVo::getNormResult)).collect(Collectors.toList()));
                }
            }
        }
        return data;
    }

    @Override
    public List<BiKpiNormListResponse> biKpiNormList(IdRequest request) {
        //查询类别
        LambdaQueryWrapper<KpiNormClassify> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(KpiNormClassify::getDeleted, false);
        Long parentId = 0L;
        if (request.getId() != null) {
            KpiNormClassify normClassify = kpiNormClassifyDao.selectById(request.getId());
            if (normClassify != null) {
                parentId = normClassify.getParentId();
            }
            queryWrapper.like(KpiNormClassify::getParentUrl, "/" + request.getId() + "/");
        }
        List<KpiNormClassify> list = kpiNormClassifyDao.selectList(queryWrapper);
        //查询指标
        LambdaQueryWrapper<KpiNormInfo> queryWrapper1 = new LambdaQueryWrapper<>();
        queryWrapper1.orderByDesc(KpiNormInfo::getNormStatus).orderByAsc(KpiNormInfo::getNormClassifyId).orderByAsc(KpiNormInfo::getSort).orderByDesc(KpiNormInfo::getCreateTime);
        queryWrapper1.eq(KpiNormInfo::getDeleted,false);
        queryWrapper1.eq(KpiNormInfo::getNormStatus,2);
        List<KpiNormInfo> normInfoList = kpiNormInfoDao.selectList(queryWrapper1);
        Map<Long,List<BiKpiNormInfoResponse>> map = new HashMap<>();
        //组装指标数据
        for (KpiNormInfo normInfo : normInfoList) {
            BiKpiNormInfoResponse response = BiKpiNormInfoResponse
                    .builder()
                    .id(normInfo.getId())
                    .normName(normInfo.getNormName())
                    .normCode(normInfo.getNormCode())
                    .normClassifyId(normInfo.getNormClassifyId())
                    .normSign(normInfo.getNormSign())
                    .normIndicatorType(normInfo.getNormIndicatorType())
                    .normDimensions(normInfo.getNormDimensions())
                    .normUnit(normInfo.getNormUnit())
                    .sort(normInfo.getSort())
                    .decimalDigit(normInfo.getDecimalDigit())
                    .build();
            if (map.containsKey(normInfo.getNormClassifyId())) {
                List<BiKpiNormInfoResponse> responses = map.get(normInfo.getNormClassifyId());
                responses.add(response);
            } else {
                List<BiKpiNormInfoResponse> responses = new ArrayList<>();
                responses.add(response);
                map.put(normInfo.getNormClassifyId(),responses);
            }
        }
        List<BiKpiNormListResponse> listResponses = list.stream().map(
                x -> BiKpiNormListResponse
                        .builder()
                        .id(x.getId())
                        .parentId(x.getParentId())
                        .classifyName(x.getClassifyName())
                        .levelNum(x.getLevelNum())
                        .kpiNormInfoList(map.get(x.getId()))
                        .build())
                .collect(Collectors.toList());
        return BiKpiNormListResponse.buildTree(listResponses, parentId);
    }

    @Override
    @Transactional
    public Boolean allInsert(String time) {
        //time = "2025-10-01"
        //查询上个月细项数据,如果没有则查询25年6月的基础
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        List<KpiDataDetail> insertList = null;
        try{
            //查上个月的数据，如果上个月出现跨年问题
            Date startTime = sdf.parse(time);
            //检查当月是否存在数据如果存在，返回false
            if(checkDataExist(startTime)){
                return false;
            }
            calendar.setTime(startTime);
            LocalDate nowDate = LocalDate.of(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH)+1, 1);
            LocalDate oldDate = nowDate.minusMonths(1);
            startTime = Date.from(oldDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            Date endTime = getLastDayOfMonth(startTime);
            List<KpiDataDetail> details = kpiDataDetailDao.selectList(KpiDataDetail.getWrapper()
                    .eq(KpiDataDetail::getDeleted,false)
                    .between(KpiDataDetail::getDataDate,startTime,endTime));
            if(CollectionUtils.isNotEmpty(details)){
                //提取数据并修改月份与值，重新新增数据
                insertList = new ArrayList<>();
                for(KpiDataDetail detail : details){
                    KpiDataDetail insert = KpiDataDetail.builder().build();
                    BeanUtils.copyProperties(detail,insert);
                    insert.setCreateTime(new Date());
                    insert.setUpdateTime(new Date());
                    //更新时间和值
                    insert.setDataDate(calendar.getTime());
                    //更新值，除了100的，其他值上下浮动1~2
                    if(detail.getValue().compareTo(new BigDecimal("100.000000")) == 0){
                        insert.setValue(detail.getValue());
                    }else{
                        insert.setValue(fluctuate(detail.getValue(),2));
                    }
                    if(insert.getValue().compareTo(new BigDecimal("100.00"))> 0){
                        insert.setValue(new BigDecimal("100.00"));
                    }
                    if(insert.getValue().compareTo(new BigDecimal("0.00"))< 0){
                        insert.setValue(new BigDecimal("0.00"));
                    }
                    if(insert.getKpiDataId().equals(51L)){
                        if(insert.getValue().compareTo(new BigDecimal("2.00")) > 0){
                            insert.setValue(new BigDecimal("0.05"));
                        }
                    }
                    insertList.add(insert);
                }
                System.out.println(insertList);
                kpiDataDetailDao.insertList(insertList);
            }else{
                //查询25年6月的基础数据
      /*          startTime = Date.from(LocalDate.of(2025,6,1).atStartOfDay(ZoneId.systemDefault()).toInstant());
                endTime = Date.from(LocalDate.of(2025,6,30).atStartOfDay(ZoneId.systemDefault()).toInstant());
                List<KpiDataDetail> detailsOld = kpiDataDetailDao.selectList(KpiDataDetail.getWrapper()
                        .eq(KpiDataDetail::getDeleted,false)
                        .between(KpiDataDetail::getDataDate,startTime,endTime));*/

                try (BufferedReader reader = new BufferedReader(
                        new InputStreamReader(kpiJsonResource.getInputStream(), StandardCharsets.UTF_8))) {

                    // 步骤2：读取文件内容为 JSON 字符串
                    StringBuilder jsonContent = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        jsonContent.append(line);
                    }
                    List<KpiDataDetail> detailsOld = JSON.parseArray(jsonContent.toString(), KpiDataDetail.class);
                    //提取数据并修改月份与值，重新新增数据
                    insertList = new ArrayList<>();
                    for(KpiDataDetail detail : detailsOld){
                        KpiDataDetail insert = KpiDataDetail.builder().build();
                        BeanUtils.copyProperties(detail,insert);
                        //更新时间和值
                        insert.setCreateTime(new Date());
                        insert.setUpdateTime(new Date());
                        insert.setDataDate(calendar.getTime());
                        //更新值，除了100的，其他值上下浮动1~2
                        if(detail.getValue().compareTo(new BigDecimal("100.000000")) == 0){
                            insert.setValue(detail.getValue());
                        }else{
                            insert.setValue(fluctuate(detail.getValue(),2));
                        }
                        if(insert.getValue().compareTo(new BigDecimal("100.00"))> 0){
                            insert.setValue(new BigDecimal("100.00"));
                        }
                        if(insert.getValue().compareTo(new BigDecimal("0.00"))< 0){
                            insert.setValue(new BigDecimal("0.00"));
                        }
                        if(insert.getKpiDataId().equals(51L)){
                            if(insert.getValue().compareTo(new BigDecimal("2.00")) > 0){
                                insert.setValue(new BigDecimal("0.05"));
                            }
                        }
                        insertList.add(insert);
                    }
                    System.out.println(insertList);
                    kpiDataDetailDao.insertList(insertList);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return true;
    }

    public boolean checkDataExist(Date time){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        Date lastDate = calendar.getTime();
        List<KpiDataDetail> details = kpiDataDetailDao.selectList(KpiDataDetail.getWrapper()
                .eq(KpiDataDetail::getDeleted,false)
                .between(KpiDataDetail::getDataDate,time,lastDate));
        if(CollectionUtils.isNotEmpty(details)){
            return true;
        }
        return false;
    }

    /**
     * 根据输入的日期字符串，返回该月最后一天的日期字符串
     * @param date 输入日期字符串，格式为"yyyy-MM-dd"
     * @return 该月最后一天的日期字符串，格式为"yyyy-MM-dd"
     * @throws ParseException 如果输入日期格式不正确则抛出此异常
     */
    public Date getLastDayOfMonth(Date date) {
        // 使用Calendar处理日期
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        // 将日期设置为当月的最后一天
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        // 格式化并返回最后一天的日期字符串
        return calendar.getTime();
    }

    /**
     * 将BigDecimal值上下浮动1到2之间的随机值
     * @param original 原始BigDecimal值
     * @param scale 结果保留的小数位数
     * @return 浮动后的BigDecimal值
     */
    public static BigDecimal fluctuate(BigDecimal original, int scale) {

        Random RANDOM = new Random();

        if (original == null) {
            throw new IllegalArgumentException("原始值不能为null");
        }

        // 生成-2到2之间的随机数（不包括2）
        double fluctuation = (RANDOM.nextDouble() * 4) - 2;
        // 确保波动范围在[0, 2)之间
        if (fluctuation > -1 && fluctuation < 1) {
            fluctuation = fluctuation < 0 ? fluctuation + 1 : fluctuation + 2;
        }

        // 转换为BigDecimal并进行计算
        BigDecimal fluctuationBigDecimal = new BigDecimal(fluctuation)
                .setScale(scale, RoundingMode.HALF_UP);

        // 计算结果并设置精度
        return original.add(fluctuationBigDecimal)
                .setScale(scale, RoundingMode.HALF_UP);
    }

    @Override
    public TableBase openExportKpi() {
        TableBase data = new TableBase();
        List<String> thList = new ArrayList<>();
        thList.add("指标一级名称");
        thList.add("指标二级名称");
        thList.add("指标三级名称");
        thList.add("时间");
        thList.add("指标结果");
        thList.add("往期指标结果");
        thList.add("较往期浮动比例");
        thList.add("是否有企业分组");
        thList.add("是否有镇街分组");
        thList.add("企业数据json字符串");
        thList.add("镇街数据json字符串");
        thList.add("是否标杆指标");
        data.setThList(thList);
        data.setExcelName("驾驶舱kpi数据导出");
        List<List<String>> trList = new ArrayList<>();
        LambdaQueryWrapper<KpiNormClassify> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(KpiNormClassify::getDeleted, false);
        List<KpiNormClassify> classifyList = kpiNormClassifyDao.selectList(queryWrapper);
        Map<Long,KpiNormClassify> calssifyMap = classifyList.stream().collect(Collectors.toMap(KpiNormClassify::getId,x->x));
        //查询指标
        LambdaQueryWrapper<KpiNormInfo> queryWrapper1 = new LambdaQueryWrapper<>();
        queryWrapper1.orderByDesc(KpiNormInfo::getNormStatus).orderByAsc(KpiNormInfo::getNormClassifyId).orderByAsc(KpiNormInfo::getSort).orderByDesc(KpiNormInfo::getCreateTime);
        queryWrapper1.eq(KpiNormInfo::getDeleted,false);
        queryWrapper1.eq(KpiNormInfo::getNormStatus,2);
        queryWrapper1.orderByAsc(KpiNormInfo::getNormClassifyId);
        List<KpiNormInfo> normInfoList = kpiNormInfoDao.selectList(queryWrapper1);

        LocalDateTime date = LocalDateTime.of(2025,11,1,0,0,0).atZone(ZoneId.systemDefault()).toLocalDateTime();
        List<BiKpiNormVo> kpiVoList = new ArrayList<>();
        for(KpiNormInfo info : normInfoList){
            BiKpiNormRequest request = new BiKpiNormRequest();
            request.setNormCode(info.getNormCode());
            request.setLastSearchFlag(true);
            request.setBenchmarkSearchFlag(true);

            request.setStartTime(Date.from(date.atZone(ZoneId.systemDefault()).toInstant()));
            request.setEndTime(Date.from(date.with(TemporalAdjusters.lastDayOfMonth()).atZone(ZoneId.systemDefault()).toInstant()));
            String dimensions = info.getNormDimensions();
            request.setCompanySearchFlag(true);
            if(dimensions.contains(",")){
                request.setStreetSearchFlag(true);
            }
            BiKpiNormVo kpiVo = this.findByCode(request);
            kpiVoList.add(kpiVo);
        }
        Map<Long,BiKpiNormVo> voMap = kpiVoList.stream().collect(Collectors.toMap(BiKpiNormVo::getNormId,x->x));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        for(KpiNormInfo info : normInfoList){
            List<String> itemList = new ArrayList<>();
            Long classfyId = info.getNormClassifyId();
            //查询一级目录
            Long secondId = calssifyMap.get(classfyId).getParentId();
            Long rootId = calssifyMap.get(secondId).getParentId();
            itemList.add(calssifyMap.getOrDefault(rootId,null)==null?"":calssifyMap.get(rootId).getClassifyName());
            itemList.add(calssifyMap.getOrDefault(secondId,null)==null?"":calssifyMap.get(secondId).getClassifyName());
            itemList.add(info.getNormName());
            itemList.add(date.format(formatter)+"至"+date.with(TemporalAdjusters.lastDayOfMonth()).format(formatter));
            BiKpiNormVo vo = voMap.get(info.getId());
            if(vo!=null){
                itemList.add(vo.getNormResult().toPlainString()+"%");
                itemList.add(vo.getLastNormResult().toPlainString()+"%");
                itemList.add(vo.getFloatRatio().toPlainString()+"%");
                itemList.add(CollectionUtils.isNotEmpty(vo.getNormResultByCompany())==true?"是":"否");
                itemList.add(CollectionUtils.isNotEmpty(vo.getNormResultByStreet())==true?"是":"否");
                if(CollectionUtils.isNotEmpty(vo.getNormResultByCompany())){
                    itemList.add(JSON.toJSONString(vo.getNormResultByCompany()));
                }else{
                    itemList.add("[]");
                }
                if(CollectionUtils.isNotEmpty(vo.getNormResultByStreet())){
                    itemList.add(JSON.toJSONString(vo.getNormResultByStreet()));
                }else{
                    itemList.add("[]");
                }
                itemList.add(vo.getBenchmarkFlag()==true?"是":"否");
            }else{
                itemList.add("0%");
                itemList.add("0%");
                itemList.add("0%");
                itemList.add("false");
                itemList.add("false");
                itemList.add("[]");
                itemList.add("[]");
                itemList.add("false");
            }
            trList.add(itemList);
        }
        data.setTrList(trList);
        return data;
    }
}
