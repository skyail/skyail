package com.skyail.gateway.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@ConfigurationProperties(prefix = "auth.security")
public class AuthSecurityProperties {
    private String signKey;
    private List<String> ignore;
}
