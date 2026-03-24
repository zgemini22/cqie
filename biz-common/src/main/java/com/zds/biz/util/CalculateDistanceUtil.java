package com.zds.biz.util;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class CalculateDistanceUtil {

    // 地球半径，单位：千米
    private static final double EARTH_RADIUS = 6371.0;

    /**
     * 将角度转换为弧度
     * @param degree 角度
     * @return 弧度
     */
    private static double toRadians(double degree) {
        return degree * Math.PI / 180.0;
    }

    /**
     * 使用Haversine公式计算两个经纬度点之间的距离（单位：千米）
     * @param lon1 第一个点的经度
     * @param lat1 第一个点的纬度
     * @param lon2 第二个点的经度
     * @param lat2 第二个点的纬度
     * @return 两点之间的距离（千米）
     */
    public static double calculateDistance(double lon1, double lat1, double lon2, double lat2) {
        // 将经纬度转换为弧度
        double radLat1 = toRadians(lat1);
        double radLat2 = toRadians(lat2);
        double radLon1 = toRadians(lon1);
        double radLon2 = toRadians(lon2);

        // 计算纬度差和经度差
        double deltaLat = radLat2 - radLat1;
        double deltaLon = radLon2 - radLon1;

        // Haversine公式
        double a = Math.sin(deltaLat / 2) * Math.sin(deltaLat / 2) +
                Math.cos(radLat1) * Math.cos(radLat2) *
                        Math.sin(deltaLon / 2) * Math.sin(deltaLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        // 计算距离（千米）
        return EARTH_RADIUS * c;
    }

    @Data
    public static class DistanceInfo{
        //起点
        private BigDecimal sourceLon;
        private BigDecimal sourceLat;
        //终点
        private BigDecimal tagetLon;
        private BigDecimal tagetLat;
        private Double distance;
        private Long id;
        //人员密集区-建筑物ID
        private String jzwId;
        //避难场所名称
        private String shelterName;
    }
}
