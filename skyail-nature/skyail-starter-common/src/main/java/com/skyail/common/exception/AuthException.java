package com.skyail.common.exception;

import lombok.Data;

/**
 * 登录异常
 */
@Data
public class AuthException extends RuntimeException{

    private String message;
    private int code;

    public AuthException(int code , String message){
        this.message = message;
        this.code = code;
    }

    public AuthException(String message){
        this.code = 500;
        this.message = message;
    }

}
