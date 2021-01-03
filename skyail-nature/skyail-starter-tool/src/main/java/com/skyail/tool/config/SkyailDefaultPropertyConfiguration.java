package com.skyail.tool.config;

import com.skyail.launch.annotation.SkyailPropertySource;
import org.springframework.context.annotation.Configuration;

/**
 * 加载默认配置
 */
@SkyailPropertySource(value = "classpath:/skyail-tool.yml")
@Configuration
public class SkyailDefaultPropertyConfiguration {
}
