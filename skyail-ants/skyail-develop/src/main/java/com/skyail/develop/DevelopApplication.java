package com.skyail.develop;

import com.skyail.common.util.SkyailApplication;
import org.springframework.cloud.client.SpringCloudApplication;

@SpringCloudApplication
public class DevelopApplication {

    public static void main(String[] args) {
        SkyailApplication.run(DevelopApplication.class,"skyail-develop" , args);
    }
}
