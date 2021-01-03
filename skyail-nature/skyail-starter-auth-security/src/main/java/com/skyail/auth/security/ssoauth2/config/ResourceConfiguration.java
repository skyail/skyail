package com.skyail.auth.security.ssoauth2.config;

import com.skyail.auth.security.ssoauth2.handler.AuthExceptionEntryPoint;
import com.skyail.auth.security.ssoauth2.props.AuthSecurityProperties;
import com.skyail.auth.security.ssoauth2.service.IUrlRoleMapService;
import com.skyail.auth.security.ssoauth2.handler.CustomAccessDeniedExceptionHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

import javax.annotation.Resource;
import java.util.List;

@Configuration
@EnableResourceServer
@Slf4j
public class ResourceConfiguration extends ResourceServerConfigurerAdapter {

    @Resource
    private TokenStore tokenStore;
    @Resource
    private AuthSecurityProperties properties;
    @Resource
    private CustomAccessDeniedExceptionHandler customAccessDeniedExceptionHandler;
    @Resource
    private AuthExceptionEntryPoint authExceptionEntryPoint;

    /**
     * 指定token的持久化策略
     * 其下有   RedisTokenStore保存到redis中，
     * JdbcTokenStore保存到数据库中，
     * InMemoryTokenStore保存到内存中等实现类，
     * 这里我们选择保存在数据库中
     *
     * @return
     */

    public void configure(ResourceServerSecurityConfigurer resources)  {
        resources
                .resourceId("product_api")//指定当前资源的id，非常重要！必须写！
                .accessDeniedHandler(customAccessDeniedExceptionHandler) //写了token但是权限不足时的处理
                .authenticationEntryPoint(authExceptionEntryPoint) //没有写token时的处理
                .tokenStore(tokenStore);//指定保存token的方式
    }

    public void configure(HttpSecurity http) throws Exception {

        //设置自定义放权的url
        List<String> ignores = properties.getIgnore();
        for(String url : ignores){
            http.authorizeRequests().antMatchers(url).permitAll();
        }

        http.authorizeRequests()
                //设置可以直接访问的请求
                .antMatchers(
                        "/actuator/**",
                        "/oauth/captcha",
                        "/oauth/logout",
                        "/oauth/clear-cache",
                        "/oauth/render/**",
                        "/oauth/callback/**",
                        "/oauth/revoke/**",
                        "/oauth/refresh/**",
                        "/token/**"
                        ).permitAll() //对指定的默认请求放权
                .anyRequest().authenticated() //对所有请求都要求获取授权之后访问
                .and()
                //自定义 未授权时的处理逻辑
                .headers().addHeaderWriter((request, response) -> {
            response.addHeader("Access-Control-Allow-Origin", "*");//允许跨域
            if (request.getMethod().equals("OPTIONS")) {//如果是跨域的预检请求，则原封不动向下传达请求头信息
                response.setHeader("Access-Control-Allow-Methods", request.getHeader("Access-Control-Request-Method"));
                response.setHeader("Access-Control-Allow-Headers", request.getHeader("Access-Control-Request-Headers"));
            }
        });
    }



}
