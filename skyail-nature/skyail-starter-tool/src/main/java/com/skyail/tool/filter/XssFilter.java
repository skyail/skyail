package com.skyail.tool.filter;

import com.skyail.tool.properties.XssConfigurationProperties;
import com.skyail.tool.wrapper.XssHttpServletRequestWrapper;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class XssFilter implements Filter {

    private List<String> excludes ;

    private boolean enabled ;

    public XssFilter(XssConfigurationProperties properties){
        this.enabled = properties.getEnable();
        this.excludes = properties.getExcludes();
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;

        //如果该访问接口在排除列表里面则不拦截
        if(isExcludeUrl(request.getServletPath())){
            filterChain.doFilter(servletRequest,servletResponse);
            return;
        }
        //拦截该url并进行xss过滤
        XssHttpServletRequestWrapper xssHttpServletRequestWrapper = new XssHttpServletRequestWrapper(request);
        filterChain.doFilter(xssHttpServletRequestWrapper,servletResponse);

    }


    private boolean isExcludeUrl(String urlPath){
        if(!enabled){
            //如果xss开关关闭了，则所有url都不拦截
            return true;
        }
        if(excludes==null||excludes.isEmpty()){
            return false;
        }

        String url = urlPath;
        for(String pattern:excludes){
            Pattern p = Pattern.compile("^"+pattern);
            Matcher m = p.matcher(url);
            if(m.find()){
                return true;
            }
        }
        return false;
    }

}
