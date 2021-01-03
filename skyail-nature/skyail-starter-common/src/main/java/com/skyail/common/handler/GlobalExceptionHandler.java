package com.skyail.common.handler;

import com.skyail.common.exception.AuthException;
import com.skyail.common.exception.GlobalException;
import com.skyail.common.util.R;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 统一异常处理
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 登录认证异常处理
     * @param e
     * @return
     */
    @ExceptionHandler(AuthException.class)
    public R AuthExceptionHandle(AuthException e){
        return R.fail(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
    }


    /**
     * @Description Hibernate Validation 接口校验 异常处理：非 RequestBody 中的参数
     * @Author zxing
     * @Date 2020/10/30 11:48
     * @Company CTTIC
     * @Param [request, e]
     * @return com.cttic.hitsys.common.util.R
     **/
    @ExceptionHandler(BindException.class)
    public R handleBindException(HttpServletRequest request, BindException e) {
        List<FieldError> allErrors = e.getFieldErrors();
        StringBuilder sb = new StringBuilder();
        for (FieldError errorMessage : allErrors) {
            sb.append(errorMessage.getField()).append(": ").append(errorMessage.getDefaultMessage()).append("; ");
        }
        return R.fail(sb.toString());
    }

    /**
     * @Description Hibernate Validation 接口校验 异常处理：RequestBody 中的参数
     * @Author zxing
     * @Date 2020/10/30 11:48
     * @Company CTTIC
     * @Param [request, e]
     * @return com.cttic.hitsys.common.util.R
     **/
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public R handleMethodArgumentNotValidException(HttpServletRequest request, MethodArgumentNotValidException e) {

        List<FieldError> allErrors = e.getBindingResult().getFieldErrors();
        StringBuilder sb = new StringBuilder();
        for (FieldError errorMessage : allErrors) {
            sb.append(errorMessage.getField()).append(": ").append(errorMessage.getDefaultMessage()).append("; ");
        }
        return R.fail(sb.toString());
    }


    /**
     * 处理全局异常
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler(GlobalException.class)
    public R handleGlobalException(HttpServletRequest request, GlobalException e) {
        return R.fail(e.getCode(),e.getMessage());
    }


    /**
     * 其他异常统一处理
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    public R ExceptionHandle(Exception e){
        return R.fail(HttpServletResponse.SC_BAD_REQUEST , e.getMessage());
    }

}
