package com.zds.user.service;

/**
 * 手机验证码服务
 */
public interface TelCodeService {
    /**
     * 保存验证码
     */
    boolean saveCode(String phone, String code);
    /**
     * 核对验证码
     */
    boolean verifyCode(String phone, String code);
    /**
     * 根据手机号获取验证码
     */
    String findCode(String phone);
}
