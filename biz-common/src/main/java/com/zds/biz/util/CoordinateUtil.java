package com.zds.biz.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.zds.biz.vo.CoordinateVo;
import com.zds.biz.vo.XataCoordinateVo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.collections.CollectionUtils;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.Polygon;

import java.awt.geom.Point2D;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 经纬度计算工具类
 */
public class CoordinateUtil {

    public static List<CoordinateVo> jsonStringToCoordList(String longitudeLatitudeJson) {
        return JSON.parseObject(longitudeLatitudeJson, new TypeReference<>() {
        });
    }

    public static LimitCoordinate toLimitCoordinate(String longitudeLatitudeJson) {
        //转换经纬度坐标集合
        List<CoordinateVo> list = jsonStringToCoordList(longitudeLatitudeJson);
        return toLimitCoordinate(list);
    }

    public static LimitCoordinate toLimitCoordinate(double lat, double lon, int raidus, List<CoordinateVo> list) {
        if (list != null && !list.isEmpty()) {
            return toLimitCoordinate(list);
        } else {
            double[] around = CoordinateUtil.getAround(lat, lon, raidus);
            double minLat = around[0];
            double minLon = around[1];
            double maxLat = around[2];
            double maxLon = around[3];
            return LimitCoordinate.builder()
                    .x_min(BigDecimal.valueOf(minLon))
                    .x_max(BigDecimal.valueOf(maxLon))
                    .y_min(BigDecimal.valueOf(minLat))
                    .y_max(BigDecimal.valueOf(maxLat))
                    .build();
        }
    }

    public static LimitCoordinate toLimitCoordinate(List<CoordinateVo> list) {
        //计算粗略范围四极坐标
        BigDecimal x_min = BigDecimal.ZERO;
        BigDecimal x_max = BigDecimal.ZERO;
        BigDecimal y_min = BigDecimal.ZERO;
        BigDecimal y_max = BigDecimal.ZERO;
        for (CoordinateVo vo : list) {
            if (vo.getLng().compareTo(x_max) > 0) {
                x_max = vo.getLng();
            }
            if (x_min.compareTo(BigDecimal.ZERO) == 0 || vo.getLng().compareTo(x_min) < 0) {
                x_min = vo.getLng();
            }
            if (vo.getLat().compareTo(y_max) > 0) {
                y_max = vo.getLat();
            }
            if (y_min.compareTo(BigDecimal.ZERO) == 0 || vo.getLat().compareTo(y_min) < 0) {
                y_min = vo.getLat();
            }
        }
        return LimitCoordinate.builder()
                .x_min(x_min)
                .x_max(x_max)
                .y_min(y_min)
                .y_max(y_max)
                .build();
    }

    public static double[] getAround(double lat, double lon, int raidus) {
        double degree = (24901 * 1609) / 360.0;
        double dpmLat = 1 / degree;
        double radiusLat = dpmLat * (double) raidus;
        double minLat = lat - radiusLat;
        double maxLat = lat + radiusLat;
        double mpdLng = degree * Math.cos(lat * (Math.PI / 180));
        double dpmLng = 1 / mpdLng;
        double radiusLng = dpmLng * (double) raidus;
        double minLng = lon - radiusLng;
        double maxLng = lon + radiusLng;
        return new double[]{minLat, minLng, maxLat, maxLng};
    }

    @Data
    @Builder
    public static class LimitCoordinate {
        private BigDecimal x_min;
        private BigDecimal x_max;
        private BigDecimal y_min;
        private BigDecimal y_max;
    }

    public static List<XataCoordinateVo> checkPointInRange(String longitudeLatitudeJson, List<XataCoordinateVo> pointList) {
        //转换经纬度坐标集合
        List<CoordinateVo> list = jsonStringToCoordList(longitudeLatitudeJson);
        return checkPointInRange(list, pointList);
    }

    public static List<XataCoordinateVo> checkPointInRange(List<CoordinateVo> rangeList, List<XataCoordinateVo> pointList) {
        List<XataCoordinateVo> list = new ArrayList<>();
        for (XataCoordinateVo vo : pointList) {
            boolean flag;
            if (vo.getPipelineType() == 1) {
                flag = checkLineInRange(rangeList, vo);
            } else {
                flag = checkPointInRange(rangeList, vo);
            }
            if (flag) {
                list.add(vo);
            }
        }
        return list;
    }

    public static List<XataCoordinateVo> checkPointInRange(double centerLat, double centerLon, double radius, List<XataCoordinateVo> pointList) {
        List<CoordinateVo> rangeList = getRangeList(centerLat, centerLon, radius, 360);
        return checkPointInRange(rangeList, pointList);
    }

    public static List<XataCoordinateVo> checkPointInRange(BigDecimal centerLat, BigDecimal centerLon, Integer radius, List<XataCoordinateVo> pointList) {
        List<CoordinateVo> rangeList = getRangeList(centerLat.doubleValue(), centerLon.doubleValue(), radius, 360);
        return checkPointInRange(rangeList, pointList);
    }

    public static boolean checkLineInRange(List<CoordinateVo> rangeList, XataCoordinateVo vo) {
        boolean flag1 = checkPointInRange(rangeList, XataCoordinateVo.builder().lng(vo.getLng()).lat(vo.getLat()).build());
        boolean flag2 = checkPointInRange(rangeList, XataCoordinateVo.builder().lng(vo.getLng2()).lat(vo.getLat2()).build());
        return flag1 && flag2;
    }

    public static boolean checkPointInRange(List<CoordinateVo> rangeList, XataCoordinateVo vo) {
        List<Point2D.Double> pointList = new ArrayList<>();
        for (CoordinateVo coordinateVo : rangeList) {
            pointList.add(new Point2D.Double(coordinateVo.getLng().doubleValue(), coordinateVo.getLat().doubleValue()));
        }
        Point2D.Double point = new Point2D.Double(vo.getLng().doubleValue(), vo.getLat().doubleValue());
        return isPointInPolygon(point, pointList);
    }

    /**
     * 检查点是否在多边形内部
     */
    public static boolean isPointInPolygon(Point2D.Double point, List<Point2D.Double> pointList) {
        if (pointList.get(0).getX() != pointList.get(pointList.size() - 1).getX() || pointList.get(0).getY() != pointList.get(pointList.size() - 1).getY()) {
            pointList.add(new Point2D.Double(pointList.get(0).getX(), pointList.get(0).getY()));
        }
        GeometryFactory geometryFactory = new GeometryFactory();
        // 创建多边形
        Coordinate[] coords = new Coordinate[pointList.size()];
        for (int i = 0; i < pointList.size(); i++) {
            coords[i] = new Coordinate(pointList.get(i).getX(), pointList.get(i).getY());
        }
        Polygon polygon = geometryFactory.createPolygon(coords);
        Point testPoint = geometryFactory.createPoint(new Coordinate(point.getX(), point.getY()));
        // 检查点是否在多边形内
        return polygon.contains(testPoint);
    }

    // 地球半径，单位米
    private static final double EARTH_RADIUS = 6371000;

    // 将角度转换为弧度
    private static double toRadians(double angle) {
        return Math.toRadians(angle);
    }

    // 计算两点间的距离（米）
    public static double haversineDistance(double lat1, double lon1, double lat2, double lon2) {
        double latDistance = toRadians(lat2 - lat1);
        double lonDistance = toRadians(lon2 - lon1);
        double a = Math.pow(Math.sin(latDistance / 2), 2)
                + Math.cos(toRadians(lat1)) * Math.cos(toRadians(lat2))
                * Math.pow(Math.sin(lonDistance / 2), 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return EARTH_RADIUS * c;
    }

    // 计算圆上的点
    public static double[][] calculatePointsOnCircle(double centerLat, double centerLon, double radius, int numPoints) {
        double[][] points = new double[numPoints][2];
        double step = 360.0 / numPoints;

        for (int i = 0; i < numPoints; i++) {
            double angle = step * i;
            double latRad = toRadians(centerLat);
            double lonRad = toRadians(centerLon);
            double angularDistance = radius / EARTH_RADIUS;

            // 简化计算，这里假设地球是完美的球体
            // 实际上，更精确的计算需要考虑地球的扁率
            double newLat = Math.asin(Math.sin(latRad) * Math.cos(angularDistance) +
                    Math.cos(latRad) * Math.sin(angularDistance) * Math.cos(toRadians(angle)));
            double newLon = lonRad + Math.atan2(Math.sin(toRadians(angle)) * Math.sin(angularDistance) * Math.cos(latRad),
                    Math.cos(angularDistance) - Math.sin(latRad) * Math.sin(newLat));

            points[i][0] = Math.toDegrees(newLat);
            points[i][1] = Math.toDegrees(newLon);
        }

        return points;
    }


    public static List<CoordinateVo> calculateCircleCoordinates(
            double centerLat,
            double centerLng,
            double radius) {
        //return calculateCircleCoordinates(centerLat, centerLng, radius, 40);
        return createPolygon(
                CoordinateVo.builder().lat(BigDecimal.valueOf(centerLat)).lng(BigDecimal.valueOf(centerLng)).build(),
                radius
        );
    }

    public static List<CoordinateVo> calculateCircleCoordinates1(
            double centerLat,
            double centerLng,
            double radius) {
        //return calculateCircleCoordinates(centerLat, centerLng, radius, 40);
//        return createPolygon(
//                CoordinateVo.builder().lat(BigDecimal.valueOf(centerLat)).lng(BigDecimal.valueOf(centerLng)).build(),
//                radius
//        );
        return getRangeList(centerLat, centerLng, radius, 360);
    }

    /**
     * 计算圆形的经纬度顶点集合
     *
     * @param centerLat   中心点纬度
     * @param centerLng   中心点经度
     * @param radius      半径(米)
     * @param pointsCount 顶点数量(越多越接近圆形，建议32-100)
     * @return 圆形顶点经纬度集合(闭合多边形)
     */
    public static List<CoordinateVo> calculateCircleCoordinates(
            double centerLat,
            double centerLng,
            double radius,
            int pointsCount) {

        List<CoordinateVo> coordinates = new ArrayList<>();

        // 将中心点经纬度转换为弧度
        double centerLatRad = Math.toRadians(centerLat);
        double centerLngRad = Math.toRadians(centerLng);

        // 计算每个顶点之间的角度间隔(弧度)
        double angleStep = 2 * Math.PI / pointsCount;

        // 生成每个顶点的坐标
        for (int i = 0; i < pointsCount; i++) {
            // 当前角度
            double angle = i * angleStep;

            // 计算相对于中心点的偏移量(米)
            double dx = radius * Math.cos(angle);  // 东西方向偏移
            double dy = radius * Math.sin(angle);  // 南北方向偏移

            // 将偏移量转换为纬度差(弧度)
            double latDiff = dy / EARTH_RADIUS;
            // 将偏移量转换为经度差(弧度)，考虑纬度影响
            double lngDiff = dx / (EARTH_RADIUS * Math.cos(centerLatRad));

            // 计算顶点经纬度(弧度转度)
            double pointLat = Math.toDegrees(centerLatRad + latDiff);
            double pointLng = Math.toDegrees(centerLngRad + lngDiff);

            coordinates.add(new CoordinateVo(BigDecimal.valueOf(pointLat), BigDecimal.valueOf(pointLng)));
        }

        // 闭合多边形，添加第一个点作为最后一个点
        if (!coordinates.isEmpty()) {
            coordinates.add(coordinates.get(0));
        }

        return coordinates;
    }

    // 计算圆上的点
    public static List<CoordinateVo> getRangeList(double centerLat, double centerLon, double radius, int numPoints) {
        double[][] points = calculatePointsOnCircle(centerLat, centerLon, radius, numPoints);
        List<CoordinateVo> list = new ArrayList<>();
        for (double[] point : points) {
            list.add(CoordinateVo.builder()
                    .lat(BigDecimal.valueOf(point[0]))
                    .lng(BigDecimal.valueOf(point[1]))
                    .build());
        }
        return list;
    }

    public static double PI = 3.14159265358979324;

    /**
     * GCJ02转WGS84
     * 高德转天地图
     */
    public static LatLng transformGCJ2WGS(LatLng latLng) {
        return transformGCJ2WGS(latLng.getLatitude(), latLng.getLongitude());
    }

    public static LatLng transformGCJ2WGS(double gcjLat, double gcjLon) {
        LatLng d = deltas(gcjLat, gcjLon);
        return new LatLng(gcjLat - d.latitude, gcjLon - d.longitude);
    }

    public static LatLng deltas(double lat, double lon) {
        double a = 6378245.0;//  a: 卫星椭球坐标投影到平面地图坐标系的投影因子。
        double ee = 0.00669342162296594323; //  ee: 椭球的偏心率。
        double dLat = transformLats(lon - 105.0, lat - 35.0);
        double dLon = transformLons(lon - 105.0, lat - 35.0);
        double radLat = lat / 180.0 * PI;
        double magic = Math.sin(radLat);
        magic = 1 - ee * magic * magic;
        double sqrtMagic = Math.sqrt(magic);
        dLat = (dLat * 180.0) / ((a * (1 - ee)) / (magic * sqrtMagic) * PI);
        dLon = (dLon * 180.0) / (a / sqrtMagic * Math.cos(radLat) * PI);
        return new LatLng(dLat, dLon);
    }

    public static double transformLats(double x, double y) {
        double ret = -100.0 + 2.0 * x + 3.0 * y + 0.2 * y * y + 0.1 * x * y + 0.2 * Math.sqrt(Math.abs(x));
        ret += (20.0 * Math.sin(6.0 * x * PI) + 20.0 * Math.sin(2.0 * x * PI)) * 2.0 / 3.0;
        ret += (20.0 * Math.sin(y * PI) + 40.0 * Math.sin(y / 3.0 * PI)) * 2.0 / 3.0;
        ret += (160.0 * Math.sin(y / 12.0 * PI) + 320 * Math.sin(y * PI / 30.0)) * 2.0 / 3.0;
        return ret;
    }

    public static double transformLons(double x, double y) {
        double ret = 300.0 + x + 2.0 * y + 0.1 * x * x + 0.1 * x * y + 0.1 * Math.sqrt(Math.abs(x));
        ret += (20.0 * Math.sin(6.0 * x * PI) + 20.0 * Math.sin(2.0 * x * PI)) * 2.0 / 3.0;
        ret += (20.0 * Math.sin(x * PI) + 40.0 * Math.sin(x / 3.0 * PI)) * 2.0 / 3.0;
        ret += (150.0 * Math.sin(x / 12.0 * PI) + 300.0 * Math.sin(x / 30.0 * PI)) * 2.0 / 3.0;
        return ret;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LatLng {
        private double latitude;
        private double longitude;
    }

    private static final double A = 6378245.0;
    private static final double EE = 0.00669342162296594323;

    /**
     * WGS84转GCJ02坐标系
     */
    public static LatLng transformWGS2GCJ(LatLng latLng) {
        double[] arr = wgs84ToGcj02(latLng.getLatitude(), latLng.getLongitude());
        return LatLng.builder().longitude(arr[1]).latitude(arr[0]).build();
    }

    /**
     * WGS84转GCJ02坐标系
     *
     * @param wgsLat WGS84纬度
     * @param wgsLng WGS84经度
     * @return 包含GCJ02经纬度的double数组，索引0为纬度，1为经度
     */
    public static double[] wgs84ToGcj02(double wgsLat, double wgsLng) {
        if (outOfChina(wgsLat, wgsLng)) {
            return new double[]{wgsLat, wgsLng};
        }
        double dLat = transformLat(wgsLng - 105.0, wgsLat - 35.0);
        double dLng = transformLng(wgsLng - 105.0, wgsLat - 35.0);
        double radLat = wgsLat / 180.0 * PI;
        double magic = Math.sin(radLat);
        magic = 1 - EE * magic * magic;
        double sqrtMagic = Math.sqrt(magic);
        dLat = (dLat * 180.0) / ((A * (1 - EE)) / (magic * sqrtMagic) * PI);
        dLng = (dLng * 180.0) / (A / sqrtMagic * Math.cos(radLat) * PI);
        return new double[]{wgsLat + dLat, wgsLng + dLng};
    }

    // 判断坐标是否在国外
    private static boolean outOfChina(double lat, double lng) {
        return !(lng >= 72.004 && lng <= 137.8347 && lat >= 0.8293 && lat <= 55.8271);
    }

    // 纬度转换算法
    private static double transformLat(double x, double y) {
        double ret = -100.0 + 2.0 * x + 3.0 * y + 0.2 * y * y + 0.1 * x * y + 0.2 * Math.sqrt(Math.abs(x));
        ret += (20.0 * Math.sin(6.0 * x * PI) + 20.0 * Math.sin(2.0 * x * PI)) * 2.0 / 3.0;
        ret += (20.0 * Math.sin(y * PI) + 40.0 * Math.sin(y / 3.0 * PI)) * 2.0 / 3.0;
        ret += (160.0 * Math.sin(y / 12.0 * PI) + 320 * Math.sin(y * PI / 30.0)) * 2.0 / 3.0;
        return ret;
    }

    // 经度转换算法
    private static double transformLng(double x, double y) {
        double ret = 300.0 + x + 2.0 * y + 0.1 * x * x + 0.1 * x * y + 0.1 * Math.sqrt(Math.abs(x));
        ret += (20.0 * Math.sin(6.0 * x * PI) + 20.0 * Math.sin(2.0 * x * PI)) * 2.0 / 3.0;
        ret += (20.0 * Math.sin(x * PI) + 40.0 * Math.sin(x / 3.0 * PI)) * 2.0 / 3.0;
        ret += (150.0 * Math.sin(x / 12.0 * PI) + 300.0 * Math.sin(x / 30.0 * PI)) * 2.0 / 3.0;
        return ret;
    }

//    /**
//     * 根据多边形和最大半径,计算其最大半径变化后,多边形等比例缩放之后的点位集合
//     *
//     * @param nodeList  多边形集合
//     * @param oldRadius 旧半径
//     * @param newRadius 新半径
//     * @return 等比例缩放之后的点位集合
//     */
//    public static List<CoordinateVo> updateScale(List<CoordinateVo> nodeList, Integer oldRadius, Integer newRadius) {
//        if (CollectionUtils.isEmpty(nodeList)) {
//            return new ArrayList<>();
//        }
//        // 检查原始多边形是否首位闭合
//        if (!isClosedStrict(nodeList)) {
//            nodeList.add(nodeList.get(0));
//        }
//        // 计算缩放距离
//        double bufferDistance = newRadius - oldRadius;
//        if (bufferDistance == 0) {
//            return nodeList;
//        }
//        // 建立投影（WGS84 -> Web Mercator EPSG:3857）
//        CRSFactory crsFactory = new CRSFactory();
//        CoordinateReferenceSystem crsWGS84 = crsFactory.createFromName("EPSG:4326");
//        CoordinateReferenceSystem crsMercator = crsFactory.createFromName("EPSG:3857");
//        CoordinateTransformFactory ctFactory = new CoordinateTransformFactory();
//        CoordinateTransform toMercator = ctFactory.createTransform(crsWGS84, crsMercator);
//        CoordinateTransform toWGS84 = ctFactory.createTransform(crsMercator, crsWGS84);
//        // 转换多边形到米单位坐标
//        GeometryFactory geometryFactory = new GeometryFactory();
//        Coordinate[] mercatorCoords = new Coordinate[nodeList.size()];
//        for (int i = 0; i < nodeList.size(); i++) {
//            CoordinateVo vo = nodeList.get(i);
//            ProjCoordinate src = new ProjCoordinate(vo.getLng().doubleValue(), vo.getLat().doubleValue());
//            ProjCoordinate dst = new ProjCoordinate();
//            toMercator.transform(src, dst);
//            mercatorCoords[i] = new Coordinate(dst.x, dst.y);
//        }
//        Polygon polygon = geometryFactory.createPolygon(mercatorCoords);
//        // 做缓冲（单位：米，正数外扩，负数内缩）
//        Geometry buffered = polygon.buffer(bufferDistance);
//        // 转换结果回经纬度
//        List<CoordinateVo> list = new ArrayList<>();
//        Coordinate[] resultCoords = buffered.getCoordinates();
//        for (Coordinate c : resultCoords) {
//            ProjCoordinate src = new ProjCoordinate(c.x, c.y);
//            ProjCoordinate dst = new ProjCoordinate();
//            toWGS84.transform(src, dst);
//            list.add(CoordinateVo.builder()
//                    .lng(BigDecimal.valueOf(dst.x))
//                    .lat(BigDecimal.valueOf(dst.y))
//                    .build());
//        }
//
//        return list;
//    }

    /**
     * 根据多边形和最大半径，计算其最大半径变化后，多边形等比例缩放后的点位集合
     *
     * @param nodeList  多边形点集合
     * @param oldRadius 旧半径
     * @param newRadius 新半径
     * @return 等比例缩放后的点位集合
     */
    public static List<CoordinateVo> updateScale(List<CoordinateVo> nodeList, double oldRadius, double newRadius) {
        if (CollectionUtils.isEmpty(nodeList)) {
            return new ArrayList<>();
        }
        // 计算缩放比例
        double scale = newRadius / oldRadius;
        if (scale == 1.0) {
            return new ArrayList<>(nodeList);
        }
        // 计算多边形中心点
        double centerLng = 0;
        double centerLat = 0;
        for (CoordinateVo vo : nodeList) {
            centerLng += vo.getLng().doubleValue();
            centerLat += vo.getLat().doubleValue();
        }
        centerLng /= nodeList.size();
        centerLat /= nodeList.size();
        // 按比例缩放每个点
        List<CoordinateVo> scaledList = new ArrayList<>();
        for (CoordinateVo vo : nodeList) {
            double lng = centerLng + (vo.getLng().doubleValue() - centerLng) * scale;
            double lat = centerLat + (vo.getLat().doubleValue() - centerLat) * scale;
            scaledList.add(CoordinateVo.builder()
                    .lng(BigDecimal.valueOf(lng))
                    .lat(BigDecimal.valueOf(lat))
                    .build());
        }
        // 可选：保持首尾闭合
        if (!isClosedStrict(scaledList)) {
            scaledList.add(scaledList.get(0));
        }

        return scaledList;
    }

    /**
     * 根据圆心和最大半径,计算随机多边形的点位集合
     *
     * @param node   圆心经纬
     * @param radius 半径
     * @return 圆形多边形的点位集合
     */
    public static List<CoordinateVo> createPolygon(CoordinateVo node, double radius) {
        // 获取偏移后的随机多边形
        List<CoordinateVo> randomList = translatePolygon(getRandomPolygon(), node);
        // 计算出随机多边形的中心点
        CoordinateVo center = calculateCentroid(randomList);
        // 计算最远半径
        double maxDistance = 0;
        for (CoordinateVo point : randomList) {
            double distance = haversineDistance(
                    center.getLat().doubleValue(),
                    center.getLng().doubleValue(),
                    point.getLat().doubleValue(),
                    point.getLng().doubleValue()
            );
            if (distance > maxDistance) {
                maxDistance = distance;
            }
        }

        return updateScale(randomList, maxDistance, radius);
    }

    /**
     * 检查传入多边形是否首尾闭合
     *
     * @param polygon 多边形点位集合
     * @return 是否首尾闭合
     */
    public static boolean isClosedStrict(List<CoordinateVo> polygon) {
        if (polygon == null || polygon.size() < 3) return false;

        CoordinateVo first = polygon.get(0);
        CoordinateVo last = polygon.get(polygon.size() - 1);

        return first.getLat().compareTo(last.getLat()) == 0
                && first.getLng().compareTo(last.getLng()) == 0;
    }

    /**
     * 变换传入的多边形到指定经纬 （经纬度差值近似，适用于城市或乡镇级别）
     *
     * @param original      原始多边形点位集合
     * @param newFirstPoint 指定经纬点位
     * @return 变换后的多边形点位集合
     */
    public static List<CoordinateVo> translatePolygon(List<CoordinateVo> original, CoordinateVo newFirstPoint) {
        if (original == null || original.isEmpty()) {
            return original;
        }
        // 计算偏移量
        BigDecimal deltaLng = newFirstPoint.getLng().subtract(original.get(0).getLng());
        BigDecimal deltaLat = newFirstPoint.getLat().subtract(original.get(0).getLat());
        List<CoordinateVo> result = new ArrayList<>();
        for (CoordinateVo point : original) {
            CoordinateVo newPoint = CoordinateVo.builder()
                    .lng(point.getLng().add(deltaLng))
                    .lat(point.getLat().add(deltaLat))
                    .build();
            result.add(newPoint);
        }

        return result;
    }

    // 随机获取一个预定义的多边形集合
    private static List<CoordinateVo> getRandomPolygon() {
        // 预定义的多边形
        List<String[]> list = new ArrayList<>();
        list.add(new String[]{
                "106.568280,106.567629,106.567562,106.566710,106.570286,106.571790,106.569300,106.568782,106.568280",
                "29.561969,29.561116,29.558927,29.556671,29.556320,29.557841,29.558844,29.561033,29.561969"
        });
        list.add(new String[]{
                "106.554763,106.554763,106.556069,106.556981,106.558080,106.558765,106.557707,106.556214,106.555385,106.554763",
                "29.563018,29.561587,29.562002,29.563226,29.563640,29.564242,29.564677,29.564760,29.563868,29.563018"
        });
        list.add(new String[]{
                "106.556152,106.555240,106.553104,106.551569,106.550781,106.551112,106.552605,106.553165,106.555238,106.556152",
                "29.558581,29.557585,29.557378,29.557523,29.557502,29.559081,29.560097,29.558728,29.558977,29.558581"
        });
        // 随机获取多边形集合
        Random random = new Random();
        int index = random.nextInt(list.size());
        // 转换数据
        String[] selected = list.get(index);
        String[] lngs = selected[0].split(",");
        String[] lats = selected[1].split(",");
        List<CoordinateVo> coordinates = new ArrayList<>();
        for (int i = 0; i < lngs.length; i++) {
            coordinates.add(new CoordinateVo(
                    new BigDecimal(lngs[i].trim()),
                    new BigDecimal(lats[i].trim())
            ));
        }

        return coordinates;
    }

    /**
     * 计算多边形的中心点（经纬度差值近似，适用于城市或乡镇级别）
     *
     * @param polygon 多边形经纬度点位集合
     * @return 多边形的中心点坐标
     */
    public static CoordinateVo calculateCentroid(List<CoordinateVo> polygon) {
        int n = polygon.size();
        if (n < 3) {
            throw new IllegalArgumentException("多边形至少需要3个点");
        }

        double area = 0;
        double cx = 0;
        double cy = 0;

        for (int i = 0; i < n; i++) {
            CoordinateVo p0 = polygon.get(i);
            CoordinateVo p1 = polygon.get((i + 1) % n);

            double x0 = p0.getLng().doubleValue();
            double y0 = p0.getLat().doubleValue();
            double x1 = p1.getLng().doubleValue();
            double y1 = p1.getLat().doubleValue();

            double cross = x0 * y1 - x1 * y0;
            area += cross;
            cx += (x0 + x1) * cross;
            cy += (y0 + y1) * cross;
        }

        area = area / 2.0;
        cx = cx / (6 * area);
        cy = cy / (6 * area);

        return new CoordinateVo(BigDecimal.valueOf(cx), BigDecimal.valueOf(cy));
    }

}
