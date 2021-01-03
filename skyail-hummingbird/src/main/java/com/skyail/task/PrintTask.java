package com.skyail.task;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@EnableScheduling
@Component
public class PrintTask {

    private final  String timer = "*/5 * * * * *";

    @Scheduled(cron = timer)
    public void print(){
        //System.out.println("this is ok");
    }

}
