package com.skyail.auth.simple.config;

import com.skyail.auth.simple.Interceptor.AuthenticationInterceptor;
import com.skyail.auth.simple.Interceptor.GlobalInterceptor;
import com.skyail.auth.simple.properties.AuthProperties;
import com.skyail.launch.annotation.SkyailPropertySource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

@Configuration
@ConditionalOnWebApplication
@EnableConfigurationProperties({AuthProperties.class})
public class AuthWebConfiguration implements WebMvcConfigurer {

    @Resource
    private AuthenticationInterceptor interceptor;

    @Resource
    private GlobalInterceptor globalInterceptor;

    @Resource
    private AuthProperties authProperties;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //先加载全局请求拦截器，用于过滤 /error 请求
        registry.addInterceptor(globalInterceptor);
        //加载认证拦截器
        InterceptorRegistration registration = registry.addInterceptor(interceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(authProperties.getIgnore());
    }
}
