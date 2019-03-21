package com.david.client.controller;

import com.david.client.stream.SimpleMessageService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sc on 2019-03-21.
 */
@RestController
public class MessageController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private SimpleMessageService simpleMessageService;

    //等同于localhost:8080或localhost:8080/send
    @GetMapping
    public String send(){
        rabbitTemplate.convertAndSend("hello world");
        return "ok";
    }

    @GetMapping("/stream/send")
    public boolean streamSend(@RequestParam String message){
        //获取 messageChannel
        MessageChannel messageChannel = simpleMessageService.zl();
        Map<String, Object> headers = new HashMap<>();
        headers.put("charset-encoding", "UTF-8");
        headers.put("content-type", MediaType.TEXT_PLAIN_VALUE);
        return messageChannel.send(new GenericMessage<>(message, headers));
    }
}
