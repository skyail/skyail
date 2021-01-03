package com.skyail.mybatis.config;

import com.skyail.launch.annotation.SkyailPropertySource;
import org.springframework.context.annotation.Configuration;

@Configuration
@SkyailPropertySource(value = "classpath:/skyail-mybatis.yml")
public class SkyailMybatisConfig {
}
