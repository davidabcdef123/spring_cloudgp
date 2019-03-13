package com.david.cloud.binderhttp;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Bean;

/**
 * Created by sc on 2019-03-13.
 */
@Configurable
public class HttpMessageChannelBinderConfiguration {

    /**
     自动装配 {@link MessageReceiverController} Bean
    */
/*    @Bean
    public MessageReceiverController messageReceiverController(){
        return new MessageReceiverController();
    }*/

    @Bean
    public HttpMessageChannelBinder httpMessageChannelBinder(DiscoveryClient discoveryClient,MessageReceiverController controller){
        return new HttpMessageChannelBinder(discoveryClient, controller);

    }


}
