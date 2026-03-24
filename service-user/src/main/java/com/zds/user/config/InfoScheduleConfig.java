package com.zds.user.config;


import com.zds.user.dao.AppVersionDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;

/**
 * 定时任务配置
 */
@Configuration
@EnableScheduling
public class InfoScheduleConfig {

    private Logger log = LoggerFactory.getLogger(InfoScheduleConfig.class);

    @Autowired
    private AppVersionDao appVersionDao;


    @Value("${timed-task.isopen}")
    private boolean isopen;

    private static boolean gatherStart = false;


    private String getThreadStr() {
        return ",当前线程:" + Thread.currentThread().getName();
    }


    //每60分钟执行 清理数据库错误连接数据
    @Scheduled(cron = "0 0/60 * * * ?")
    public void flushHost() {
        if (!isopen) {
            return;
        }
        log.info("执行<清理数据库错误连接数据>定时任务,时间:" + LocalDateTime.now() + getThreadStr());

        if (!gatherStart) {
            gatherStart = true;
            long start = System.currentTimeMillis();
            appVersionDao.flushHosts();
            gatherStart = false;
            long finish = System.currentTimeMillis();
            log.info("执行<清理数据库错误连接数据>定时任务,耗时:" + (finish-start) + "ms" + getThreadStr());
        } else {
            log.info("终止,已存在执行中的定时任务" + getThreadStr());
        }
    }
}
