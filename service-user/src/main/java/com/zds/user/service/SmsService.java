package com.zds.user.service;

/**
 * 短信服务
 * 子模块只定义接口，由启动类模块实现
 */
public interface SmsService {
    /**
     * 发送短信验证码
     * @param mobile 手机号
     * @param code 验证码
     */
    void sendSmsOfCode(String mobile, String code);

}
