package com.skyail.enhancer;


import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class JwtTokenEnhancer implements TokenEnhancer {

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken oAuth2AccessToken, OAuth2Authentication oAuth2Authentication) {
        //写入自定义字段
        Map<String,Object> info = new HashMap<>();
        info.put("license","my-boot");


        ((DefaultOAuth2AccessToken)oAuth2AccessToken).setAdditionalInformation(info);
        //设置token过期时间，120分钟
        Calendar now = Calendar.getInstance();
        now.add(Calendar.MINUTE,120);
        ((DefaultOAuth2AccessToken) oAuth2AccessToken).setExpiration(now.getTime());
        return oAuth2AccessToken;
    }
}
