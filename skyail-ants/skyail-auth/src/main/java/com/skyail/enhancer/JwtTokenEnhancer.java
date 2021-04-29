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
        info.put("license","skyail");


        ((DefaultOAuth2AccessToken)oAuth2AccessToken).setAdditionalInformation(info);
        //此处用于强制设置token失效时间，默认会根据 client 表中的时间进行设置，不建议在这里强制写固定
        /*Calendar now = Calendar.getInstance();
        now.add(Calendar.MINUTE,1);
        ((DefaultOAuth2AccessToken) oAuth2AccessToken).setExpiration(now.getTime());*/
        return oAuth2AccessToken;
    }
}
