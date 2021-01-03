package com.skyail.common.constant;

/**
 * 异常常量类：
 * 说明：_DESC 为带有 错误码的描述语句；不带有_DESC的为普通错误描述
 */
public interface ExceptionConstants {

    String TOKEN_IS_NULL = "token为空";

    String TOKEN_IS_NULL_DESC = "[401]认证失败：token不能为空";

    String TOKEN_USERNAME_OR_PASSWORD_ERROR = "用户名或密码错误";

    String TOKEN_USERNAME_OR_PASSWORD_ERROR_DESC = "[401]认证失败：用户名或密码错误";

    String TOKEN_GENERATE_ERROR = "生成token失败";





}
