package com.skyail.auth.props;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "auth.security")
@Data
public class AuthSecurityProperties {
    private String signKey;
    private List<String> ignore;
}
