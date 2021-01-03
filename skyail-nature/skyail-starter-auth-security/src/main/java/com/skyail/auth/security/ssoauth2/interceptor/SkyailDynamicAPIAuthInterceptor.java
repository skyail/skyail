package com.skyail.auth.security.ssoauth2.interceptor;

import com.skyail.auth.security.ssoauth2.config.AuthInvocationSecurityMetadataSource;
import com.skyail.auth.security.ssoauth2.config.AuthSecurityAccessDecisionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.FilterInvocation;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.*;
import java.io.IOException;

/**
 * 动态接口权限核心处理逻辑
 */
@Component
public class SkyailDynamicAPIAuthInterceptor extends AbstractSecurityInterceptor implements Filter {

    @Resource
    private AuthInvocationSecurityMetadataSource authInvocationSecurityMetadataSource;


    //注入(使用 @Resource 的注入方式也可以)
    @Autowired
    public void setMyAccessDecisionManager(AuthSecurityAccessDecisionManager myAccessDecisionManager) {
        super.setAccessDecisionManager(myAccessDecisionManager);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        FilterInvocation fi = new FilterInvocation(servletRequest, servletResponse, filterChain);
        System.out.println("用户上下文信息:{}"+SecurityContextHolder.getContext().getAuthentication());
        System.out.println("url地址:{}"+fi.getHttpRequest().getRequestURI());
        System.out.println("客户端信息:{}"+ fi.getHttpRequest());
        invoke(fi);
    }

    public void invoke(FilterInvocation fi) throws IOException, ServletException {
        InterceptorStatusToken token = null;
        if(SecurityContextHolder.getContext().getAuthentication()!=null){ //这里要做非空判断，因为有可能 SecurityContextHolder.getContext().getAuthentication()获取为空，导致后面报错
            token=super.beforeInvocation(fi);
        }
        try {
            fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
        } finally {
            super.afterInvocation(token, null);
        }

    }


    @Override
    public void destroy() {

    }

    @Override
    public Class<?> getSecureObjectClass() {
        return FilterInvocation.class;

    }

    @Override
    public SecurityMetadataSource obtainSecurityMetadataSource() {
        return this.authInvocationSecurityMetadataSource;
    }



}
