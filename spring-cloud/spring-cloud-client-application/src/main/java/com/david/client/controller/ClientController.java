package com.david.client.controller;

import com.david.client.annotation.CustomizedLoadBalanced;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * Created by sc on 2019-03-05.
 */
@RestController
public class ClientController {

    @Autowired
    @CustomizedLoadBalanced // 依赖注入自定义 RestTemplate Bean
    private RestTemplate restTemplate;

    @Autowired
    @LoadBalanced  // 依赖注入 Ribbon RestTemplate Bean
    private RestTemplate lbRestTemplate;

    @Value("${spring.application.name}")
    private String currentServiceName;

    @Autowired
    private DiscoveryClient discoveryClient;


}
