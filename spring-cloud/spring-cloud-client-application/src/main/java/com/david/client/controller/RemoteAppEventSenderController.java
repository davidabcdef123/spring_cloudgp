package com.david.client.controller;

import com.david.client.enevt.RemoteAppEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.DefaultServiceInstance;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.web.bind.annotation.*;

import javax.sound.sampled.Port;
import java.util.Arrays;
import java.util.List;

/**
 * Created by sc on 2019-03-22.
 * 远程应用事件控制器
 */
@RestController
public class RemoteAppEventSenderController implements ApplicationEventPublisherAware {

    @Value("${spring.application.name}")
    public String currentAppName;

    private ApplicationEventPublisher publisher;

    @Autowired
    private DiscoveryClient discoveryClient;

    //给自己发消息
    @GetMapping("/send/remote/event")
    public String sendEvent(@RequestParam String message) {
        publisher.publishEvent(message);
        return "sent";
    }

  /*
    @GetMapping("/send/remote/event/{appName}")
    public String sendAppCluster(@PathVariable String appName,@RequestParam String message{
        // 发送数据到自己
        List<ServiceInstance> serviceInstances = discoveryClient.getInstances(appName);
        RemoteAppEvent remoteAppEvent = new RemoteAppEvent(message, currentAppName, appName, serviceInstances);
        // 发送事件当前上下文
        publisher.publishEvent(remoteAppEvent);
        return "ok";
    }*/

    @PostMapping("/send/remote/event/{appName}")
    public String sendAppCluster(@PathVariable String appName, @RequestBody Object data) {
        RemoteAppEvent remoteAppEvent = new RemoteAppEvent(data, appName, true);
        // 发送事件当前上下文
        publisher.publishEvent(remoteAppEvent);
        return "Ok";
    }

   /*
    @PostMapping("/send/remote/event/{appName}/{ip}/{port}")
    public String aendAppinstance(@PathVariable String appName,
                                  @PathVariable String ip,
                                  @PathVariable int Port,
                                  @RequestBody Object data) {
        ServiceInstance serviceInstance = new DefaultServiceInstance(appName, ip, Port, false);
        RemoteAppEvent remoteAppEvent = new RemoteAppEvent(data, currentAppName, appName, Arrays.asList(serviceInstance));
        // 发送事件当前上下文
        publisher.publishEvent(remoteAppEvent);
        return "ok";
    }*/

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.publisher = applicationEventPublisher;
    }
}
