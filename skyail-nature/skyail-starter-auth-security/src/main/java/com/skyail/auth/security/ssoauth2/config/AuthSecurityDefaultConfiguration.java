package com.skyail.auth.security.ssoauth2.config;

import com.skyail.launch.annotation.SkyailPropertySource;
import org.springframework.context.annotation.Configuration;

/**
 * 加载默认配置
 */
@Configuration
@SkyailPropertySource(value = "classpath:/skyail-auth-security.yml")
public class AuthSecurityDefaultConfiguration {
}
