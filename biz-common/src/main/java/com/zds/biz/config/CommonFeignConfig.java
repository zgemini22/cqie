package com.zds.biz.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = "com.zds.biz.feign")
public class CommonFeignConfig {
}
