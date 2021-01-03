package com.skyail.db.config;

import com.skyail.launch.annotation.SkyailPropertySource;
import org.springframework.context.annotation.Configuration;

/**
 * 添加DB的默认配置
 */
@SkyailPropertySource(value = "classpath:/skyail-db.yml")
@Configuration
public class DbDefaultConfiguration {
}
