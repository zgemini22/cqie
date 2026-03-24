package com.zds.biz.util;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 两个坐标距离距离计算
 */
@Component
@Slf4j
public class DistanceCalculationUtil {

    public static void main(String[] args) {
        Boolean isNear = isPointNearLine(29.695061632000034, 106.36661536300005, 29.695038473000068, 106.36653926400004,29.615504496814008,106.49779373253847,100);
        System.out.println(isNear);
    }

    /**
     * 地球平均半径（单位：米）
     */
    private static final double EARTH_RADIUS = 6371000;

    public static boolean isPointNearLine(double startLat, double startLon, double endLat, double endLon, double checkLat, double checkLon, double distanceThreshold) {
        // 将经纬度转换为弧度
        double startLatRad = Math.toRadians(startLat);
        double startLonRad = Math.toRadians(startLon);
        double endLatRad = Math.toRadians(endLat);
        double endLonRad = Math.toRadians(endLon);
        double checkLatRad = Math.toRadians(checkLat);
        double checkLonRad = Math.toRadians(checkLon);

        // 计算直线方向向量
        double dLon = endLonRad - startLonRad;
        double dLat = endLatRad - startLatRad;

        // 计算检查点到起点的向量
        double dCheckLon = checkLonRad - startLonRad;
        double dCheckLat = checkLatRad - startLatRad;

        // 计算投影长度
        double projectionLength = (dCheckLon * dLon + dCheckLat * dLat) / (dLon * dLon + dLat * dLat);

        // 计算最近点坐标
        double closestLatRad = startLatRad + projectionLength * dLat;
        double closestLonRad = startLonRad + projectionLength * dLon;

        // 计算最近点与检查点之间的距离
        double distance = haversineDistance(closestLatRad, closestLonRad, checkLatRad, checkLonRad);

        return distance <= distanceThreshold;
    }

    // 使用Haversine公式计算两点之间的距离
    public static double haversineDistance(double lat1, double lon1, double lat2, double lon2) {
        double dLat = lat2 - lat1;
        double dLon = lon2 - lon1;
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(lat1) * Math.cos(lat2) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return EARTH_RADIUS * c;
    }
}
