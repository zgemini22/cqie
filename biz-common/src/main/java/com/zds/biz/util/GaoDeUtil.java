package com.zds.biz.util;

import org.json.JSONObject;

public class GaoDeUtil {

    /**
     * 输入地址获取转换后的transfer_url
     */
    public static String getAddressUrl(String str, String active) {
        String tiles = "";
        String url = ("prod".equals(active) ? "http://192.168.23.6:8090/out/amap" : "https://www.amap.com") + "/service/shortUrl?&address=" + str;
        try {
            System.out.println(url);
            String data = HttpClientUtil.get(url);
            JSONObject jsonObject = new JSONObject(data);
            tiles = jsonObject.getJSONObject("data").getString("transfer_url");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tiles;
    }

    /**
     * 获取高德车行导航短链接
     * @param longitude 高德GCJ02
     * @param latitude 高德GCJ02
     */
    public static String getAddressUrl(String longitude, String latitude, String active) {
        String longitude_start = "106.469129";
        String latitude_start = "29.558944";
        String device = "设备点位";
        String longUrl = "http%3A%2F%2Fwb.amap.com%2F%3Fr%3D" + latitude_start + "%252C" + longitude_start + "%252C%2525E9%252587%25258D%2525E7%252587%252583%2525E6%2525B2%252599%2525E5%252588%252586%2525E5%25258F%2525B8%252C" + latitude + "%252C" + longitude + "%252C" + device + "%252C0%252C0%252C0%26src%3Dpc_sms_dir";
        return GaoDeUtil.getAddressUrl(longUrl, active);
    }
}
