package com.skyail.common.exception;

import lombok.Data;

/**
 * 全局异常类
 */
@Data
public class GlobalException extends RuntimeException {
    private int code;
    private String message;
    public GlobalException(){

    }
    public GlobalException(int code , String message){
        this.code = code ;
        this.message = message;
    }
}
