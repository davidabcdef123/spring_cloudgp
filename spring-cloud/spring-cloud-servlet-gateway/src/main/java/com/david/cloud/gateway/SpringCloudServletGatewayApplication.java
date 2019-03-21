package com.david.cloud.gateway;

import com.david.cloud.gateway.loadbalancer.ZookeeperLoadBalancer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Created by sc on 2019-03-12.
 */
@SpringBootApplication
@EnableDiscoveryClient
@ServletComponentScan(basePackages = "com.david.cloud.gateway.servlet")
@EnableScheduling
public class SpringCloudServletGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudServletGatewayApplication.class, args);
    }

    @Bean
    public ZookeeperLoadBalancer zookeeperLoadBalancer(DiscoveryClient discoveryClient) {
        return new ZookeeperLoadBalancer(discoveryClient);
    }
}
