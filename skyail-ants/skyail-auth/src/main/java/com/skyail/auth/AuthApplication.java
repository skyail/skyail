package com.skyail.auth;

import com.skyail.common.util.SkyailApplication;
import org.springframework.cloud.client.SpringCloudApplication;

@SpringCloudApplication
public class AuthApplication {

    public static void main(String[] args) {
        SkyailApplication.run(AuthApplication.class,"skyail-auth",args);
    }
}
