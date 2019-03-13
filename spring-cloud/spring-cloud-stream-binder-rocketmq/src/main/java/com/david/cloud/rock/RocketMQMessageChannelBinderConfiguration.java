package com.david.cloud.rock;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by sc on 2019-03-13.
 */
@Configuration
public class RocketMQMessageChannelBinderConfiguration {

    @Bean
    public RocketMQMessageChannelBinder rocketMQMessageChannelBinder(){
        return new RocketMQMessageChannelBinder();
    }
}
