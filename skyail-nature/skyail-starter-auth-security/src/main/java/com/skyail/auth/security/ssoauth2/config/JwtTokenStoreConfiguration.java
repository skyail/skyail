package com.skyail.auth.security.ssoauth2.config;

import com.skyail.auth.security.ssoauth2.enhancer.JwtTokenEnhancer;
import com.skyail.auth.security.ssoauth2.props.AuthSecurityProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import javax.annotation.Resource;

@Configuration
@EnableConfigurationProperties({AuthSecurityProperties.class})
public class JwtTokenStoreConfiguration {

    @Resource
    private AuthSecurityProperties properties;

    @Bean
    public TokenStore jwtTokenStore(){
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter(){
        JwtAccessTokenConverter accessTokenConverter = new JwtAccessTokenConverter();
        accessTokenConverter.setSigningKey(properties.getSignKey()); //设置token加密key，对称加密
        return accessTokenConverter;
    }

    public TokenEnhancer tokenEnhancer(){
        return new JwtTokenEnhancer();
    }
}
