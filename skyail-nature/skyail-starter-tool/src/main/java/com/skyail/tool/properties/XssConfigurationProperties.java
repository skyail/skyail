package com.skyail.tool.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.List;

@ConfigurationProperties(prefix = "xss")
@PropertySource(value = "classpath:/skyail-tool.yml")
@Data
public class XssConfigurationProperties {

    private Boolean enable;
    private List<String> excludes;
}
