package com.zds.user.util;

import com.alibaba.fastjson.JSONObject;
import com.zds.biz.util.HttpClientUtil;
import com.zds.biz.vo.request.user.FlashLettersRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Hex;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * 电话外呼
 */
@Slf4j
public class CallUtil {

    public static void main(String[] args) {
        getToken();
    }

    /**
     * 公共接口获取token
     */
    public static String getToken() {
        String response = "";
        try {
            String ipPort = "http://cti.zkiccs.com";
            int cid = 1437;
            String secret = "7453c9f1a4184b660d317a277304b6cb";
            String url = ipPort + "/v2/access_token?" +
                    "cid=" + cid + "&secret=" + secret;
            log.info(url);
            response = HttpClientUtil.get(url);
            JSONObject jsonObject = JSONObject.parseObject(response);
            response = jsonObject.getString("data");
            log.info(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }
}
