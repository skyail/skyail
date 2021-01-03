package com.skyail.service.impl;

import com.skyail.auth.simple.entity.User;
import com.skyail.auth.simple.service.ICustomUserService;
import org.springframework.stereotype.Service;

@Service
public class CustomUserServiceImpl implements ICustomUserService{

    @Override
    public User loadUserInfo(String username) {
        User user = new User();
        //可以从数据库中加载用户信息
        user.setPassword("71d8b5968604e655c8a7a8a254b8f8c6");
        user.setUsername("admin");
        return user;
    }
}
