package com.zds.user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zds.biz.vo.IdRequest;
import com.zds.biz.vo.SelectResponse;
import com.zds.biz.vo.TableBase;
import com.zds.biz.vo.request.user.*;
import com.zds.biz.vo.response.user.*;

import java.util.List;

/**
 * KPI指标服务
 */
public interface KpiNormService {

    /**
     * 类别列表
     */
    List<AdminKpiNormListResponse> list(AdminKpiNormListRequest request);

    /**
     * 类别新增修改
     */
    boolean saveOrUpdate(AdminKpiNormSaveUpdateRequest request);

    /**
     * 类别详情
     */
    AdminKpiNormDetailResponse detail(Long id);

    /**
     * 类别删除
     */
    boolean delete(Long id);

    /**
     * 类别下拉
     */
    List<AdminKpiNormListResponse> selectList();

    /**
     * 指标分页列表
     */
    IPage<AdminKpiNormInfoListResponse> normPageList(AdminKpiNormInfoListRequest request);

    /**
     * 指标保存修改
     */
    boolean normSaveUpdate(AdminKpiNormInfoSaveRequest request);

    /**
     * 指标详情
     */
    AdminKpiNormInfoDetailResponse normDetail(Long id);

    /**
     * 指标删除
     */
    boolean normDelete(Long id);

    /**
     * 指标下拉
     */
    List<KpiNormSelectResponse> normSelect(AdminKpiSelectRequest request);

    /**
     * 指标状态修改
     */
    boolean normStatusUpdate(AdminKpiNormStatusRequest request);

    /**
     * 数据源分页列表
     */
    IPage<AdminKpiDataListResponse> dataList(AdminKpiDataListRequest request);

    /**
     * 数据源下拉
     */
    List<SelectResponse> dataSelect();

    /**
     * 运算符下拉
     */
    List<SelectResponse> operatorSelect();

    /**
     * 数据源详情
     */
    AdminKpiDataListResponse dataDetail(Long id);

    /**
     * 数据源删除
     */
    boolean dataDelete(Long id);
    /**
     * 数据源停用/启用
     */
    boolean dataStartStop(AdminKpiDataStopRequest request);

    /**
     * 数据源明细分页列表
     */
    IPage<AdminKpiDetailListResponse> dataDetailList(AdminKpiDetailListRequest request);

    /**
     * 标杆值维护管理分页列表
     */
    IPage<AdminKpiBenchmarkInfoListResponse> benchmarkInfoList(AdminKpiBenchmarkInfoListRequest request);

    /**
     * 标杆值维护管理保存
     */
    boolean benchmarkInfoSave(AdminKpiBenchmarkInfoSaveRequest request);
    /**
     * 标杆值维护管理查看
     */
    AdminKpiBenchmarkInfoDetailResponse benchmarkInfoDetail(IdRequest id);

    /**
     * 标杆值维护管理启用/停用
     */
    boolean benchmarkInfoStartStop(AdminKpiBenchmarkInfoStopRequest request);

    /**
     *标杆值维护管理删除
     * */
    boolean benchmarkInfoDelete(IdRequest request);

    /**
     * 查询指定指标信息
     */
    BiKpiNormVo findByCode(BiKpiNormRequest request);

    /**
     * 大屏体征指标返回
     */
    List<BiKpiNormListResponse> biKpiNormList(IdRequest request);

    /**
     * 按月新增所有指标细项
     */
    Boolean allInsert(String time);

    /**
     * 导出kpi数据
     */
    TableBase openExportKpi();
}
