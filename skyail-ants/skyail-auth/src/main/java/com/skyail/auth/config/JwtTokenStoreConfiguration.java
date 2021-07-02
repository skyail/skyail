package com.skyail.auth.config;

import com.skyail.auth.enhancer.JwtTokenEnhancer;
import com.skyail.auth.props.AuthSecurityProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import javax.annotation.Resource;
import java.security.KeyPair;

@Configuration
@EnableConfigurationProperties({AuthSecurityProperties.class})
public class JwtTokenStoreConfiguration {

    @Resource
    private AuthSecurityProperties properties;

    @Value("${test}")
    private String test;

    @Bean
    public TokenStore jwtTokenStore(){
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter(){
        JwtAccessTokenConverter accessTokenConverter = new JwtAccessTokenConverter();
        //accessTokenConverter.setSigningKey(properties.getSignKey()); //设置token加密key，对称加密
        accessTokenConverter.setKeyPair(keyPair());
        return accessTokenConverter;
    }

    @Bean
    public KeyPair keyPair() {
        KeyStoreKeyFactory factory = new KeyStoreKeyFactory(
                new ClassPathResource("skyail.jks"), "123456".toCharArray());
        KeyPair keyPair = factory.getKeyPair(
                "skyail", "123456".toCharArray());
        return keyPair;
    }

    @Bean
    public TokenEnhancer tokenEnhancer(){
        return new JwtTokenEnhancer();
    }
}
