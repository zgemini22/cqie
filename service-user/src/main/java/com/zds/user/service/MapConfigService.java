package com.zds.user.service;

import com.zds.biz.vo.response.user.MapConfigResponse;

import java.util.List;

/**
 * 地图配置服务
 */
public interface MapConfigService {
    /**
     * 查询地图配置列表
     */
    List<MapConfigResponse> findList();
}
