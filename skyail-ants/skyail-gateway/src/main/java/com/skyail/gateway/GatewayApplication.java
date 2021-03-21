package com.skyail.gateway;

import com.skyail.common.util.SkyailApplication;
import org.springframework.cloud.client.SpringCloudApplication;

@SpringCloudApplication
public class GatewayApplication {

    public static void main(String[] args) {
        SkyailApplication.run(GatewayApplication.class,"skyail-gateway" , args);
    }
}
