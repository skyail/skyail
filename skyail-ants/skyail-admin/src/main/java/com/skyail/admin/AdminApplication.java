package com.skyail.admin;

import com.skyail.common.util.SkyailApplication;
import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Description: TODO
 * @Auther: zxing
 * @Date: 2021/7/2 14:18
 * @company：CTTIC
 */

@SpringBootApplication
@EnableAdminServer
public class AdminApplication {

    public static void main(String[] args) {
        SkyailApplication.run(AdminApplication.class , "skayil-admin",args);
    }
}
