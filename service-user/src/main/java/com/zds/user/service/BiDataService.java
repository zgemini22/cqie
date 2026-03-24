package com.zds.user.service;

import com.zds.biz.vo.IdRequest;
import com.zds.biz.vo.request.user.*;
import com.zds.biz.vo.response.user.BiDataExampleDetailResponse;
import com.zds.biz.vo.response.user.BiDataExampleResponse;
import com.zds.biz.vo.response.user.BiDataListResponse;
import com.zds.biz.vo.response.user.BiDataModelResponse;
import com.zds.user.po.TblBiDataAll;
import com.zds.user.po.TblBiDataQy;
import com.zds.user.po.TblBiDataZj;

import java.util.List;

/**
 * 大屏填报信息模块
 */
public interface BiDataService {

    List<BiDataListResponse> findList();

    boolean save(BiDataSaveRequest request);

    boolean delete(IdRequest request);

    BiDataModelResponse findModel(IdRequest request);

    boolean saveModel(BiDataModelSaveRequest request);

    BiDataExampleResponse findExample(IdRequest request);

    boolean saveExample(BiDataExampleSaveRequest request);

    BiDataExampleDetailResponse selectExampleByKey(BiDataExampleFindRequest request);


    List<TblBiDataAll> biDataAlls(BiDataAllsRequest request);

    List<TblBiDataQy> biDataQys(BiDataQysRequest request);

    List<TblBiDataZj> biDataZjs(BiDataZjsRequest request);

    /**
     * 按月新增决策处置指标细项
     */
    Boolean jcczInsert(String time);
}
