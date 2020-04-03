package com.crawler.scheduler;

import com.crawler.utils.IPUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SchedulerTask {

    @Value("${computer.id}")
    private String computerId;

    //cron表达式：每隔5秒执行一次
    @Scheduled(cron = "0/10 * * * * *")
    @Async("taskExecutor")
    public void scheduled() {
        IPUtils.saveIP(computerId);//检查保存新的ip
    }

    //上一次 启动时间点之后每5秒执行一次
//如果任务时长超过 fixedRate不会启动多个任务实例，只不过会在上次任务执行完后立即启动下一轮
//除非这个类或 Job 方法用 @Async 注解了，使得任务不在 TaskScheduler 线程池中执行，而是每次创建新线程来执行。
//    @Scheduled(fixedRate = 5000)
//    public void scheduled1() {
//        log.info("使用fixedRate {}");
//    }

    //上一次 结束时间点之后 每5秒执行一次
//    @Scheduled(fixedDelay = 5000)
//    public void scheduled2() {
//        log.info("使用fixedDelay {}");
//    }

    //第一次延迟 10秒执行，之后按照fixedRate的规则每6秒执行
//    @Scheduled(initialDelay = 10000, fixedRate = 6000)
//    public void scheduled3() {
//        log.info("使用initialDelay {}");
//    }
}