package com.skyail.tool.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

import java.util.List;

@ConfigurationProperties(prefix = "auto-trim")
@PropertySource(value = "classpath:/skyail-tool.yml")
@Data
public class AutoTrimConfigurationProperties {

    private Boolean enable;
}
