package com.zds.biz.util;

import java.math.BigDecimal;

/**
 * Description:根据原始经纬度、偏移距离和方向计算新的经纬度
 * Author: wangzhipeng
 * Date: 2024/8/28
 */
public class GeoCalculatorUtil {

    // 地球半径 (单位: 米)
    private static final double EARTH_RADIUS = 6371000;

    /**
     * 根据原始经纬度、偏移距离和方向计算新的经纬度
     *
     * @param lat         原始纬度
     * @param lon         原始经度
     * @param distance    偏移距离（单位: 米）
     * @param bearing     偏移的方向（单位：度），0 表示北方，90 表示东面，180 表示南方，270 表示西面。
     * @return            新的经纬度数组 [纬度, 经度]
     */
    public static BigDecimal[] calculateNewCoordinates(BigDecimal lat, BigDecimal lon, double distance, double bearing) {
        // 将纬度和经度从度转换为弧度
        double latRad = Math.toRadians(lat.doubleValue());
        double lonRad = Math.toRadians(lon.doubleValue());
        double bearingRad = Math.toRadians(bearing);

        // 计算新的纬度
        double newLatRad = Math.asin(Math.sin(latRad) * Math.cos(distance / EARTH_RADIUS) +
                Math.cos(latRad) * Math.sin(distance / EARTH_RADIUS) * Math.cos(bearingRad));

        // 计算新的经度
        double newLonRad = lonRad + Math.atan2(Math.sin(bearingRad) * Math.sin(distance / EARTH_RADIUS) * Math.cos(latRad),
                Math.cos(distance / EARTH_RADIUS) - Math.sin(latRad) * Math.sin(newLatRad));

        // 将结果转换回度
        BigDecimal newLat = new BigDecimal(Math.toDegrees(newLatRad));
        BigDecimal newLon = new BigDecimal(Math.toDegrees(newLonRad));

        return new BigDecimal[]{newLat, newLon};
    }

}
