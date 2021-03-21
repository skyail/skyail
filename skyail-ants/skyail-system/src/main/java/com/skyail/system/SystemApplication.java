package com.skyail.system;

import com.skyail.common.util.SkyailApplication;
import org.springframework.cloud.client.SpringCloudApplication;

@SpringCloudApplication
public class SystemApplication {

    public static void main(String[] args) {
        SkyailApplication.run(SystemApplication.class , "skyail-system" , args);
    }
}
