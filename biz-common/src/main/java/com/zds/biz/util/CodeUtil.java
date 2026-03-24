package com.zds.biz.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.UUID;

/**
 * 随机验证码工具类
 */
@Component
public class CodeUtil {

    /**
     * 默认随机验证码
     */
    private static String code = "666666";

    /**
     * 是否启用随机验证码
     */
    @Value("${verification_code.isopen:true}")
    private boolean isopen = true;

    /**
     * 随机获取6位数的数字验证码
     */
    public String getNumCode(){
        if (!isopen) {
            return code;
        }
        return StringUtils.leftPad(new Random().nextInt(1000000) + "", 6, "0");
    }

    /**
     * 随机获取6位数的数字加字母验证码
     */
    public String getNumAndStrCode(){
        if (!isopen) {
            return code;
        }
        return UUID.randomUUID().toString().replace("-", "").substring(0, 6);
    }
}
