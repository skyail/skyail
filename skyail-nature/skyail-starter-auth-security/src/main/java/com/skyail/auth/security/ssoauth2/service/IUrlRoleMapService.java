package com.skyail.auth.security.ssoauth2.service;

import java.util.Map;

/**
 * 动态加载url权限
 */
public interface IUrlRoleMapService {

    /**
     * 自定义加载权限的逻辑，可以从数据库中加载，redis中加载，或者其他方式加载
     * 该方法每次在加载时，都会被调用，所以可以动态控制接口权限
     * 可以读取缓存或者数据库，因为每次请求都要调用，建议使用缓存，可以外部缓存或者JVM缓存
     * @return
     */
    Map<String,String> loadRoleMap();
}
