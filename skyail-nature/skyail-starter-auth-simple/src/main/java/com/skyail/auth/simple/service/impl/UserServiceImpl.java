package com.skyail.auth.simple.service.impl;


import com.skyail.auth.simple.entity.User;
import com.skyail.auth.simple.properties.AuthProperties;
import com.skyail.auth.simple.service.ICustomUserService;
import com.skyail.auth.simple.service.IUserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

@Service
public class UserServiceImpl implements IUserService {

    @Resource
    private AuthProperties properties;

    @Resource
    private ApplicationContext context;

    @Override
    public User loadUserByUsername(String uname) {
        //如果在配置文件中配置了用户名和密码，则优先使用配置文件中的用户名和密码；
        //如果配置文件中没有配置，那么就查询数据库
        User user = null;
        if(!properties.isCustomUser()){
            //如果不使用自定义用户设置，则执行默认设置(读取配置文件中的用户名密码信息)
            if(properties.getUser().getUsername().equals(uname)){
                //只有输入的用户名与配置的用户名相同，才会返回用户信息
                user = new User();
                user.setUsername(properties.getUser().getUsername());
                user.setPassword(properties.getUser().getPassword());
            }
        }else{
            //如果自定义用户名和密码设置，则执行自定义设置
            ICustomUserService customUserService = context.getBean(ICustomUserService.class);
            User cUser = customUserService.loadUserInfo(uname);
            user = cUser;
        }
        return user;
    }
}
