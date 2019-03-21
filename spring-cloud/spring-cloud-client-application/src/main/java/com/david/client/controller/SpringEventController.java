package com.david.client.controller;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.PayloadApplicationEvent;
import org.springframework.context.event.EventListener;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by sc on 2019-03-21.
 */
@RestController
public class SpringEventController implements ApplicationEventPublisherAware {

    private ApplicationEventPublisher publisher;

    @GetMapping("/send/event")
    public String sendEvent(@RequestParam String message){
        publisher.publishEvent(message);
        return "send";
    }

    @EventListener
    public void onMessage(PayloadApplicationEvent event){
        System.err.println("接受事件：" + event.getPayload());
    }


    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.publisher = applicationEventPublisher;
    }
}
