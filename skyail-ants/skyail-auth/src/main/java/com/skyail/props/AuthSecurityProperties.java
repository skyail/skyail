package com.skyail.props;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

import java.util.List;

@ConfigurationProperties(prefix = "auth.security")
@Data
public class AuthSecurityProperties {
    private String signKey;
    private List<String> ignore;
}
