package com.skyail.gateway.config;

import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class WhiteListConfig {

    public String[] getUrls(){
        String[] array = new String[]{
                "/test/**",
                "/test2/**"/*,
                "/skyail-user/**"*/
        };
        return array;
    }
}
