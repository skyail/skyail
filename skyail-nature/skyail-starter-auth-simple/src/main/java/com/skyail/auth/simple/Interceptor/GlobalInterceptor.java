package com.skyail.auth.simple.Interceptor;

import com.skyail.auth.simple.service.IUserService;
import com.skyail.common.exception.GlobalException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * 全局请求拦截器（专为拦截报错的请求 /error）
 */
@Component
@Slf4j
public class GlobalInterceptor implements HandlerInterceptor {

    @Autowired
    private IUserService userService;


    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object object) throws Exception {

        String url = httpServletRequest.getServletPath();
        Integer statusCode = (Integer)httpServletRequest.getAttribute("javax.servlet.error.status_code");
        //如果是 /error，则表示请求出错了
        if(url.indexOf("/error") >= 0){

            //发生 /error 请求之后的处理，此处抛出异常不会继续进行拦截，其他地方抛出异常之后，会再次进行拦截
            //此处才是拦截处理的最终位置

            log.error("请求发生异常");
            if(statusCode == HttpServletResponse.SC_NOT_FOUND){
                throw new GlobalException(HttpServletResponse.SC_NOT_FOUND,"[404] 请求不存在" );
            }
            throw new GlobalException(HttpServletResponse.SC_BAD_REQUEST,"请求发生异常");
        }
        return true;

    }



    @Override
    public void postHandle(HttpServletRequest httpServletRequest,
                           HttpServletResponse httpServletResponse,
                           Object o, ModelAndView modelAndView) throws Exception {

    }
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest,
                                HttpServletResponse httpServletResponse,
                                Object o, Exception e) throws Exception {
    }
}
