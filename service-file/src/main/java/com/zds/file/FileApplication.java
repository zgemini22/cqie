package com.zds.file;

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
@MapperScan(basePackages = {"com.zds.file.dao"})
@SpringBootApplication(scanBasePackages = {"com.zds.biz","com.zds.file"})
@EnableScheduling
@EnableAspectJAutoProxy(exposeProxy = true)
public class FileApplication {

    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(FileApplication.class);
        springApplication.addListeners(new AfterConfigListener());
        springApplication.run(args);
//        SpringApplication.run(UserApplication.class, args);
    }

}
