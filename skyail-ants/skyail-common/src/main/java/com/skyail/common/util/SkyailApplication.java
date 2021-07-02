package com.skyail.common.util;

import com.skyail.common.constant.CommonConstant;
import com.skyail.common.constant.LauncherConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.util.StringUtils;

import java.util.Properties;

@Slf4j
public class SkyailApplication {

    public static void run(Class<?> primarySource,String serverName, String... args){
        Properties properties = System.getProperties();
        if(StringUtils.isEmpty(serverName)){
            throw new RuntimeException("请指定服务名称");
        }
        properties.setProperty("spring.application.name",serverName);

        String env = properties.getProperty("spring.profiles.active");
        env = StringUtils.isEmpty(env)?"dev":env;
        log.info("["+env+"]启动---");
        if( "dev".equals(env)){
            properties.setProperty("spring.cloud.nacos.discovery.server-addr", LauncherConstant.NACOS_SERVER_DEV);
            properties.setProperty("spring.cloud.nacos.config.server-addr", LauncherConstant.NACOS_SERVER_DEV);
        }else if("test".equals(env)){
            properties.setProperty("spring.cloud.nacos.discovery.server-addr", LauncherConstant.NACOS_SERVER_TEST);
            properties.setProperty("spring.cloud.nacos.config.server-addr", LauncherConstant.NACOS_SERVER_TEST);
        }else if("prod".equals(env)){
            properties.setProperty("spring.cloud.nacos.discovery.server-addr", LauncherConstant.NACOS_SERVER_PROD);
            properties.setProperty("spring.cloud.nacos.config.server-addr", LauncherConstant.NACOS_SERVER_PROD);
        }

        //properties.setProperty("spring.cloud.gateway.discovery.locator.lower-case-service-id", CommonConstant.COMMON_TRUE_STRING);
        //properties.setProperty("spring.cloud.gateway.discovery.locator.enabled", CommonConstant.COMMON_TRUE_STRING);
        //properties.setProperty("spring.cloud.nacos.config.file-extension", LauncherConstant.NACOS_FILE_TYPE);
        //properties.setProperty("spring.cloud.nacos.config.group", LauncherConstant.NACOS_CONFIG_GROUP);
        //properties.setProperty("spring.cloud.nacos.config.prefix", LauncherConstant.NACOS_CONFIG_PREFIX);
        SpringApplication.run(primarySource , args);
    }

}
