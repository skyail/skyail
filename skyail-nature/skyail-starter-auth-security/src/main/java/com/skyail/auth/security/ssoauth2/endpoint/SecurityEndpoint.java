package com.skyail.auth.security.ssoauth2.endpoint;

import org.springframework.context.ApplicationContext;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.annotation.security.PermitAll;
import java.lang.reflect.Field;
import java.util.List;

@RestController
public class SecurityEndpoint {

    @Resource
    private ApplicationContext context;

    /**
     * 刷新放权
     * @return
     */
    @GetMapping("/ignore_refresh")
    @PermitAll
    public String testIgnore(){
        addIgnoreUrl("/myTest/show2");
        return "haha";
    }


    /**
     * 动态添加 放权url
     * @param url
     * @return
     */
    private List<SecurityFilterChain> addIgnoreUrl(String url){
        FilterChainProxy obj = (FilterChainProxy) context.getBean("springSecurityFilterChain");
        List<SecurityFilterChain> filterChains = (List<SecurityFilterChain>) getProperty(obj,"filterChains");
        filterChains.add(0,new DefaultSecurityFilterChain(new AntPathRequestMatcher(url, null)));
        DefaultSecurityFilterChain chain = (DefaultSecurityFilterChain)filterChains.get(0);
        AntPathRequestMatcher matcher =  (AntPathRequestMatcher)chain.getRequestMatcher();
        String matcherPattern = matcher.getPattern();
        System.out.println(matcherPattern);
        return filterChains;
    }

    /**
     * 动态删除 放权url
     * @param url
     * @return
     */
    private List<SecurityFilterChain> removeIgnoreUrl(String url){
        FilterChainProxy obj = (FilterChainProxy) context.getBean("springSecurityFilterChain");
        List<SecurityFilterChain> filterChains = (List<SecurityFilterChain>) getProperty(obj,"filterChains");
        for(SecurityFilterChain chain : filterChains){
            DefaultSecurityFilterChain chainD = (DefaultSecurityFilterChain)chain;
            AntPathRequestMatcher matcher =  (AntPathRequestMatcher)chainD.getRequestMatcher();
            String matcherPattern = matcher.getPattern();
            if(matcherPattern.equals(url)){
                filterChains.remove(chain);
            }
        }
        return filterChains;
    }

    private static Object getProperty(Object obj, String fieldName) {
        try {
            Field field = obj.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(obj);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
