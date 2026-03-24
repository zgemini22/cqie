package com.zds.flow;

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
@MapperScan(basePackages = {"com.zds.flow.dao"})
@SpringBootApplication(scanBasePackages = {"com.zds.biz","com.zds.flow"})
@EnableScheduling
@EnableAspectJAutoProxy(exposeProxy = true)
public class FlowApplication {
    public static void main(String... args) {
        SpringApplication springApplication = new SpringApplication(FlowApplication.class);
        springApplication.addListeners(new AfterConfigListener());
        springApplication.run(args);
    }
}
