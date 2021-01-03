package com.skyail.tool.config;

import com.skyail.tool.filter.XssFilter;
import com.skyail.tool.properties.XssConfigurationProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

@Configuration
@ConditionalOnWebApplication
@EnableConfigurationProperties({XssConfigurationProperties.class})
public class XssFilterConfig {

    @Resource
    private XssConfigurationProperties properties;

    @Bean
    public XssFilter xssFilter(){
        return new XssFilter(properties);
    }

}
