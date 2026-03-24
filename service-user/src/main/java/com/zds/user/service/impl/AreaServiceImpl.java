package com.zds.user.service.impl;

import com.zds.biz.util.BeanConvertUtil;
import com.zds.biz.util.ThreadLocalUtil;
import com.zds.biz.vo.CoordinateVo;
import com.zds.biz.vo.request.user.AreaRequest;
import com.zds.biz.vo.response.user.AreaResponse;
import com.zds.user.dao.TblAreaNodeDao;
import com.zds.user.dao.TblAreaRangeDao;
import com.zds.user.po.TblAreaNode;
import com.zds.user.po.TblAreaRange;
import com.zds.user.service.AreaService;
import org.apache.commons.lang3.StringUtils;
import org.locationtech.jts.geom.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class AreaServiceImpl implements AreaService {

    @Autowired
    private TblAreaNodeDao areaNodeDao;

    @Autowired
    private TblAreaRangeDao tblAreaRangeDao;

    @Override
    public Map<String, String> findAreaMapByCode(List<String> list) {
        return areaNodeDao.selectList(TblAreaNode.getWrapper().in(list != null && list.size() > 0, TblAreaNode::getCode, list))
                .stream()
                .collect(Collectors.toMap(TblAreaNode::getCode, TblAreaNode::getAreaName, (a, b) -> b));
    }

    @Override
    public List<AreaResponse> findAreaList(AreaRequest request) {
        return areaNodeDao.selectList(TblAreaNode.getWrapper()
                        .orderByAsc(TblAreaNode::getCode)
                        .eq(request.getLevel() != null && request.getLevel() > 0, TblAreaNode::getLevel, request.getLevel())
                        .eq(StringUtils.isNotEmpty(request.getParentCode()), TblAreaNode::getParentCode, request.getParentCode()))
                .stream()
                .map(x -> BeanConvertUtil.convertTo(x, AreaResponse::new))
                .collect(Collectors.toList());
    }

    @Override
    public Map<String, String> findAreaNameMapByCode(List<String> list) {
        List<TblAreaNode> areaNodeList = areaNodeDao.selectList(TblAreaNode.getWrapper().in(list != null && !list.isEmpty(), TblAreaNode::getAreaName, list));
        Map<String, String> map = new HashMap<>();
        Map<String, String> item = new HashMap<>();
        List<String> codeList = new ArrayList<>();
        for (TblAreaNode po : areaNodeList) {
            map.put(po.getAreaName(), po.getCode());
            item.put(po.getCode(), po.getAreaName());
            codeList.add(po.getCode());
        }
        List<TblAreaNode> areaNodeList2 = areaNodeDao.selectList(TblAreaNode.getWrapper().in(list != null && !list.isEmpty(), TblAreaNode::getParentCode, codeList));
        for (TblAreaNode po : areaNodeList2) {
            map.put(item.get(po.getParentCode()) + "_" + po.getAreaName(), po.getCode());
        }
        return map;
    }

    @Override
    public Map<String, String> findStreet(List<CoordinateVo> request) {
        Map<String, String> resultMap = new HashMap<>();

        List<TblAreaRange> allAreaPoints = tblAreaRangeDao.selectList(null);

        Map<String, Polygon> codePolygonMap = new HashMap<>();
        GeometryFactory geometryFactory = new GeometryFactory();

        Map<String, List<TblAreaRange>> groupByCode = allAreaPoints.stream()
                .collect(Collectors.groupingBy(TblAreaRange::getCode));

        for (Map.Entry<String, List<TblAreaRange>> entry : groupByCode.entrySet()) {
            String code = entry.getKey();
            List<TblAreaRange> sortedPoints = entry.getValue().stream()
                    .sorted(Comparator.comparing(TblAreaRange::getSort))
                    .collect(Collectors.toList());

            List<Coordinate> coords = sortedPoints.stream()
                    .map(p -> new Coordinate(p.getLongitude().doubleValue(), p.getLatitude().doubleValue()))
                    .collect(Collectors.toList());

            if (!coords.get(0).equals2D(coords.get(coords.size() - 1))) {
                coords.add(coords.get(0));
            }

            LinearRing ring = geometryFactory.createLinearRing(coords.toArray(new Coordinate[0]));
            Polygon polygon = geometryFactory.createPolygon(ring);
            codePolygonMap.put(code, polygon);
        }

        for (CoordinateVo vo : request) {
            String key = vo.getLng() + "-" + vo.getLat();
            Point point = geometryFactory.createPoint(new Coordinate(vo.getLng().doubleValue(), vo.getLat().doubleValue()));

            for (Map.Entry<String, Polygon> entry : codePolygonMap.entrySet()) {
                if (entry.getValue().contains(point)) {
                    resultMap.put(key, entry.getKey());
                    break;
                }
            }
        }

        return resultMap;
    }
}
