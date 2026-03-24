package com.zds.biz.util;

import com.zds.biz.vo.request.user.FlashLettersRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Hex;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * 亿美满意通-语音、闪信、文字短信
 */
@Slf4j
public class VoiceUtil {

    public static void main(String[] args) {
//        voice("13883811280","", 1);
        send("13883811280","【重庆市沙坪坝区经济和信息化委员会】尊敬的用户：您的气感报警设备刚刚发出燃气泄漏告警通知，请及时妥善处理。您也可以打开告警信息链接 https://zdnk.zdhrsoft.com/#/close?id=868550067107784 ，查看详情并关闭告警声音。");
//        FlashLettersRequest flashLettersRequest=new FlashLettersRequest();
//        flashLettersRequest.setDeviceType(2);
//        flashLettersRequest.setMobile("13883811280");
//        flashLetters(flashLettersRequest);
    }

    /**
     * 语音短信
     * deviceType 设备类型，1：烟感设备，2：气感设备
     */
    public static void voice(String mobile, String content, int deviceType) {
        try {
            String ipPort = "http://voice.b2m.cn:80";
            String templateId = deviceType == 1 ? "1725592580873002000" : "1725592720080002000";
            String appId = "EUCP-EMY-VOC1-746DT";
            String secretKey = "0AAF87B9D6378BA9";
            String timestamp = new java.text.SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().getTime());
            String sign = getMD5(appId + secretKey + timestamp);
            String url = ipPort + "/voice/templateSend?" +
                    "appId=" + appId + "&timestamp=" + timestamp + "&" +
                    "sign=" + sign + "&templateId=" + templateId + "&" +
                    "mobiles=" + mobile + "&" +
                    "smsContent=" + content + "&triggerConditions=4&" +
                    "connectTime=2";
            String body = "{}";
            log.info(url);
            String json = HttpClientUtil.postJson(url, body);
            log.info(json);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //MD5加密
    public static String getMD5(String str) {
        String md5 = "";
        try {
            md5 = Hex.encodeHexString(MessageDigest.getInstance("MD5").digest(org.apache.commons.codec.binary.StringUtils.getBytesUtf8(str)));
        } catch (Exception e) {
        }
        return md5;
    }

    /**
     * 文字短信
     */
    public static void send(String mobile, String contents) {
        try {
            String ipPort = "http://www.btom.cn:8080";
            String appId = "EUCP-EMY-SMS1-6XPMJ";
            String secretKey = "E66D13A31FCBA994";
            String timestamp = new java.text.SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().getTime());
            String sign = getMD5(appId + secretKey + timestamp);
            String url = ipPort + "/simpleinter/sendSMS?"+
                    "appId=" + appId + "&timestamp=" + timestamp + "&" +
                    "sign=" + sign + "&" +
                    "mobiles=" + mobile + "&" +
                    "content=" + URLEncoder.encode(contents, StandardCharsets.UTF_8);
            String body = "";
            log.info(contents);
            log.info(url);
            String json = HttpClientUtil.postJson(url,body);
            log.info(json);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 闪信
     */
    public static void flashLetters(FlashLettersRequest flashLettersRequest) {
        try {
            String ipPort = "http://www.btom.cn:8080";
            String appId = "EUCP-EMY-SMS1-2NI5C";
            String secretKey = "4E0CB299F7916C44";
            String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().getTime());
            String sign = getMD5(appId + secretKey + timestamp);
            String content = "";
            if (flashLettersRequest.getDeviceType() == 1) {
                content = "【重庆市沙坪坝区经济和信息化委员会】消息提醒:您接到来电是“燃气安全一件事”的火灾隐患报警电话，请注意接听。也可以查看短信、打开链接并关闭告警。";
            } else if (flashLettersRequest.getDeviceType() == 2) {
                content = "【重庆市沙坪坝区经济和信息化委员会】消息提醒:您接到来电是“燃气安全一件事”的燃气泄漏报警电话，请注意接听。也可以查看短信、打开链接并关闭告警。";
            }
            String mobiles = flashLettersRequest.getMobile();

            String url = ipPort + "/simpleinter/sendSMS?"+
                    "appId=" + appId + "&timestamp=" + timestamp + "&" +
                    "sign=" + sign + "&" +
                    "mobiles=" + mobiles + "&" +
                    "content=" + content;
            String body = "";
            log.info(url);
            String json = HttpClientUtil.postJson(url,body);
            log.info(json);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
