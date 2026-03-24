package com.zds.user;

import com.zds.biz.config.AfterConfigListener;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableAsync
@EnableFeignClients
@EnableDiscoveryClient
@MapperScan(basePackages = {"com.zds.user.dao"})
@SpringBootApplication(scanBasePackages = {"com.zds.biz","com.zds.user"})
@EnableScheduling
@EnableAspectJAutoProxy(exposeProxy = true)
public class UserApplication {

    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(UserApplication.class);
        springApplication.addListeners(new AfterConfigListener());
        springApplication.run(args);
//        SpringApplication.run(UserApplication.class, args);
    }

}
