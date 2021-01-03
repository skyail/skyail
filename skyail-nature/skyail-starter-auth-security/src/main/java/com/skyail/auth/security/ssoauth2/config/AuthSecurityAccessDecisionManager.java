package com.skyail.auth.security.ssoauth2.config;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.FilterInvocation;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;

@Component
public class AuthSecurityAccessDecisionManager implements AccessDecisionManager {

    /**
     * 决策器决策方法
     * 第一个参数 Authentication 为 token 中带有的 本次请求的 权限
     * 第三个参数 configAttributes 为数据库中配置的 某个请求url所需的权限
     * @param authentication
     * @param object
     * @param configAttributes
     * @throws AccessDeniedException
     * @throws InsufficientAuthenticationException
     */
    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {
        if(authentication == null){
            throw new AccessDeniedException("无访问权限");
        }
        // 当前用户所具有的权限
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        for(ConfigAttribute attribute : configAttributes){
            for (GrantedAuthority grantedAuthority : authorities) {
                //如果其中一项角色匹配，或者角色为 admin , 则允许访问
                if (grantedAuthority.getAuthority().equals(attribute.getAttribute()) || grantedAuthority.getAuthority().toUpperCase().equals("ROLE_ADMIN")) {
                    return;
                }
            }
        }
        throw new AccessDeniedException("无访问权限");
    }

    /**
     * 复制默认方法，使得@PreAuthorize("hasRole('ROLE_ADMIN')") 可用
     */
    @Override
    public boolean supports(ConfigAttribute attribute) {
        return false;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}
