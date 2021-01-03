package com.skyail.auth.simple.Interceptor;

import com.skyail.auth.simple.annotation.AuthIgnore;
import com.skyail.auth.simple.entity.User;
import com.skyail.auth.simple.service.IUserService;
import com.skyail.auth.simple.util.JwtUtil;
import com.skyail.common.constant.ExceptionConstants;
import com.skyail.common.exception.AuthException;
import com.skyail.common.exception.GlobalException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.util.concurrent.ExecutionException;


/**
 * 登录认证拦截器
 */
@Component
@Slf4j
public class AuthenticationInterceptor implements HandlerInterceptor {

    @Autowired
    private IUserService userService;


    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object object) throws Exception {


        String token = httpServletRequest.getHeader("token");// 从 http 请求头中取出 token

        //1.注解判断，判断方法上是否有 @AuthIgnore 注解，如果含有该注解，则直接跳过token认证
        if(hasAuthIgnore(object)){
            return true;
        }

        //2.校验token
        //2.1 查看token是否为空
        if(StringUtils.isEmpty(token)){
            log.error(ExceptionConstants.TOKEN_IS_NULL);
            throw new AuthException(HttpServletResponse.SC_UNAUTHORIZED, ExceptionConstants.TOKEN_IS_NULL_DESC);
        }
        //2.2 获取token中的用户信息，并验证
        User user = JwtUtil.getUserInfoFromToken(token);
        if(validUser(user)){
            return true;
        }else{
            throw new AuthException(HttpServletResponse.SC_UNAUTHORIZED, ExceptionConstants.TOKEN_USERNAME_OR_PASSWORD_ERROR_DESC);
        }
    }

    /**
     * 校验用户信息
     * @param user
     * @return
     */
    private boolean validUser(User user){
        String username = user.getUsername();
        String password = user.getPassword();
        //1.判断用户名或者密码是否为空
        if(StringUtils.isEmpty(username) || StringUtils.isEmpty(password)){
            return false;
        }
        //2.判断用户是否存在，用户名和密码是否匹配
        User userInStore = userService.loadUserByUsername(username);
        if(username.equals(userInStore.getUsername()) && password.equals(userInStore.getPassword())){
            return true;
        }
        return false;
    }

    /**
     * 判断类或者方法上是否有放权注解 @AuthIgnore
     * @param object
     * @return
     */
    private boolean hasAuthIgnore(Object object){
        if(object instanceof HandlerMethod){
            //1.首先判断类上是否含有 @AuthIgnore注解
            HandlerMethod handlerMethod = (HandlerMethod)object;
            Class clazz = handlerMethod.getBeanType();
            Annotation annotation = clazz.getAnnotation(AuthIgnore.class);
            if(annotation == null){
                //2.如果类上不包含该注解，那么再看方法上是否含有该注解
                annotation = handlerMethod.getMethodAnnotation(AuthIgnore.class);
                if(annotation == null){
                    return false;
                }
            }
        }

        //如果 Object 不是 HandlerMethod 类，说明请求不存在(没有定位到方法，对于前后端分离，没有定位到方法就可以认为404) ，则不用拦截 ，spring 已自动拦截，只需要在 GlabalInterceptor 中进行处理即可
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
