package com.skyail.common.constant;

public interface CommonConstants {

    //**********************JWT中使用参数**************************
    String SUBJECT_NAME = "jwt_Test";

    String SIGN_KEY = "www.skyail.com";

    String USERNAME = "username";

    String PASSWORD = "password";

    Long TOKEN_DEFAULT_EXPIRE = 2*3600*1000L;


    //********************数据库/配置加密使用参数*************************************
    String SECRET_KEY = "dbcdb84ed2862d6b2b2b30b6fefd848c";

}
