package com.skyail.gateway.config;

import com.netflix.loadbalancer.IRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 设置权重规则为Nacos的权重规则，这样就可以使nacos中的服务实例权重设置生效
 */
@Configuration
public class GlobalNacosWeightRuleConfiguration {

    @Bean
    public IRule nacosRule (){
        return new NacosWeightRule();
    }
}
