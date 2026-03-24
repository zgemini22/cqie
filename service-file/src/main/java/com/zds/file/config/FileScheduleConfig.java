package com.zds.file.config;


import com.zds.file.util.DatabaseBackupUtil;
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
public class FileScheduleConfig {
    private Logger log = LoggerFactory.getLogger(FileScheduleConfig.class);

    @Value("${timed-task.isopen}")
    private boolean isopen;

    @Autowired
    private DatabaseBackupUtil databaseBackupUtil;

    private static boolean backupStatus = false;



    private String getThreadStr() {
        return ",当前线程:" + Thread.currentThread().getName();
    }

    //每周六凌晨一点
    @Scheduled(cron = "0 0 1 ? * SAT")
    public void updateStatus() throws Exception {
        if (!isopen) {
            return;
        }
        log.info("执行<数据库备份>定时任务,时间:" + LocalDateTime.now() + getThreadStr());

        if (!backupStatus) {
            backupStatus = true;
            long start = System.currentTimeMillis();
            databaseBackupUtil.backupAllDatabases();
            backupStatus = false;
            long finish = System.currentTimeMillis();
            log.info("执行<数据库备份>定时任务,耗时:" + (finish-start) + "ms" + getThreadStr());
        } else {
            log.info("终止,已存在执行中的定时任务" + getThreadStr());
        }
    }



}
