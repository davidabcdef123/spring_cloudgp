package com.cloud.service.discovery.client.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * Created by sc on 2019-03-04.
 */
@RestController
public class ServiceController {

    @Autowired
    private DiscoveryClient discoveryClient;

    @GetMapping("/services")
    public List<String> getAllServices(){
        return discoveryClient.getServices();
    }

    @GetMapping("/service/instances/{serviceName}")
    public List<String> getAllServiceInstance(@PathVariable String serviceName){
        return discoveryClient.getInstances(serviceName).stream()
                .map(s->s.getServiceId() + " - " + s.getHost() + ":" + s.getPort()).
                collect(Collectors.toList());
    }
}
