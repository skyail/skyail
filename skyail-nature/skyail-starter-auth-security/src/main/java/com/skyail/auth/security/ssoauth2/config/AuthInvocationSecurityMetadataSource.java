package com.skyail.auth.security.ssoauth2.config;

import com.skyail.auth.security.ssoauth2.service.IUrlRoleMapService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Map;

@Service
@Slf4j
public class AuthInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    @Resource
    private FilterInvocationSecurityMetadataSource superMetadataSource;

    @Resource
    private IUrlRoleMapService urlRoleMapService;


    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }


    private final AntPathMatcher antPathMatcher = new AntPathMatcher();

    //动态加载自定义url权限映射
    private  Map<String,String> urlRoleMap ;

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        FilterInvocation fi = (FilterInvocation) object;
        String url = fi.getRequestUrl();
        urlRoleMap = urlRoleMapService.loadRoleMap();
        for(Map.Entry<String,String> entry:urlRoleMap.entrySet()){
            if(antPathMatcher.match(entry.getKey(),url)){
                return SecurityConfig.createList(entry.getValue());
            }
        }

        //  返回代码定义的默认配置
        if(!(superMetadataSource instanceof AuthInvocationSecurityMetadataSource)){
            return superMetadataSource.getAttributes(object);
        }
        return null;

    }

    @Override
    public boolean supports(Class<?> clazz) {
        return FilterInvocation.class.isAssignableFrom(clazz);
    }
}
