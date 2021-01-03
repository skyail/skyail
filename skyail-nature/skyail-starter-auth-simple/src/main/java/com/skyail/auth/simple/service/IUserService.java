package com.skyail.auth.simple.service;


import com.skyail.auth.simple.entity.User;

public interface IUserService {

    User loadUserByUsername(String uname);
}
