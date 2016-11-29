package com.qhkj.seed.crontab;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.qhkj.seed.utils.ConfigHelper;

@Component
public class ScheduledTask {
    
    @Autowired private ConfigHelper configHelper;
    
    @Scheduled(cron = "0 0 3 * * ?")
    public void reportCurrentTimeCron() throws InterruptedException {
        System.out.println("3点定时任务");
    }
}
