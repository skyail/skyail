package com.skyail.auth.simple.config;

import com.skyail.launch.annotation.SkyailPropertySource;
import org.springframework.context.annotation.Configuration;

@Configuration
@SkyailPropertySource(value = "classpath:/skyail-auth-simple.yml")
public class AuthDefaultPropertiesConfiguration {
}
