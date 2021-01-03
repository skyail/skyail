package com.skyail.auth.security.ssoauth2.props;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

import java.util.List;

@ConfigurationProperties(prefix = "auth.security")
@Data
@PropertySource(value = "classpath:/skyail-auth-security.yml")
public class AuthSecurityProperties {
    private String signKey;
    private List<String> ignore;
}
