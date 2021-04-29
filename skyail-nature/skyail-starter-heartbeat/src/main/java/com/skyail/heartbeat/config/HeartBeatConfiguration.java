package com.skyail.heartbeat.config;

import com.skyail.heartbeat.controller.HeartBeatController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HeartBeatConfiguration {

    @Bean
    public HeartBeatController heartBeat(){
        return new HeartBeatController();
    }
}
