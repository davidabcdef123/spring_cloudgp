package com.cloud.service.discovery.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Created by sc on 2019-03-04.
 */
@SpringBootApplication
@EnableDiscoveryClient
public class ZkDSClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZkDSClientApplication.class, args);
    }
}
