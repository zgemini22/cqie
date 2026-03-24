package com.zds.user.service.impl;

import com.zds.biz.constant.user.MapDataKeyEnum;
import com.zds.biz.vo.response.user.MapConfigResponse;
import com.zds.user.dao.TblMapDataDao;
import com.zds.user.po.TblMapData;
import com.zds.user.service.MapConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 角色服务
 */
@Service
public class MapConfigServiceImpl implements MapConfigService {

    @Autowired
    private TblMapDataDao mapDataDao;

    @Override
    public List<MapConfigResponse> findList() {
        MapDataKeyEnum[] keyEnums = MapDataKeyEnum.values();
        List<TblMapData> list = mapDataDao.selectList(TblMapData.getWrapper().eq(TblMapData::getDeleted, false));
        Map<String, TblMapData> map = list.stream().collect(Collectors.toMap(TblMapData::getDataKey, TblMapData -> TblMapData, (a, b) -> b));
        List<MapConfigResponse> data = new ArrayList<>();
        for (MapDataKeyEnum keyEnum : keyEnums) {
            if (map.containsKey(keyEnum.getKey())) {
                TblMapData po = map.get(keyEnum.getKey());
                data.add(MapConfigResponse.builder()
                        .dataKey(po.getDataKey())
                        .dataName(po.getDataName())
                        .dataValue(po.getDataValue())
                        .remarks(po.getRemarks())
                        .build());
            }
        }
        return data;
    }
}
