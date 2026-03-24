package com.zds.user.service;

import com.zds.biz.vo.CoordinateVo;
import com.zds.biz.vo.request.user.AreaRequest;
import com.zds.biz.vo.response.user.AreaResponse;

import java.util.List;
import java.util.Map;

public interface AreaService {

    /**
     * 查询指定省市区编码范围的省市区名称
     */
    Map<String, String> findAreaMapByCode(List<String> list);

    /**
     * 查询省市区编码
     */
    List<AreaResponse> findAreaList(AreaRequest request);

    /**
     * 查询重庆指定区名称范围的区街道编码
     */
    Map<String, String> findAreaNameMapByCode(List<String> list);

    /**
     * 经纬度查询镇街
     */
    Map<String, String> findStreet(List<CoordinateVo> request);

}
