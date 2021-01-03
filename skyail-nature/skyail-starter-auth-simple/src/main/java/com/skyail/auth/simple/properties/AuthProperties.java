package com.skyail.auth.simple.properties;

import com.skyail.launch.annotation.SkyailPropertySource;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

import java.util.List;

@ConfigurationProperties(prefix = "auth")
@Data
@PropertySource(value = "classpath:/skyail-auth-simple.yml")
public class AuthProperties  {

    private List<String> ignore;

    private boolean customUser;

    private User user = new User();

    @Data
    public class User{
        private String username;
        private String password;
    }

}
