package com.crawler.scheduler;

import com.crawler.service.DataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SchedulerTask {
    private static Logger logger = LoggerFactory.getLogger(SchedulerTask.class);

    @Autowired
    private DataService dataService;

    //cron表达式：每隔10秒执行一次
    @Scheduled(cron = "0/10 * * * * *")
    @Async("taskExecutor")
    public void scheduled() {
        boolean result = dataService.saveIP();
        if (result) {
            logger.info("********定时任务启动********更新成功********");
        } else {
            logger.info("********定时任务启动********更新失败********");
        }
    }
}