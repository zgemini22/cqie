package com.zds.user.service.impl;

import com.zds.user.service.SmsService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SmsServiceImpl implements SmsService {

    /**
     * 是否启用短信
     */
    @Value("${user-settings.smsIsOpen}")
    private boolean smsIsOpen;

    @Override
    public void sendSmsOfCode(String mobile, String code) {
        //
    }
}
