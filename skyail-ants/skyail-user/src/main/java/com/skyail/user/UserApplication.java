package com.skyail.user;

import com.skyail.common.util.SkyailApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringCloudApplication
@EnableDiscoveryClient
public class UserApplication {

    public static void main(String[] args) {
        SkyailApplication.run(UserApplication.class,"skyail-user",args);
    }
}
