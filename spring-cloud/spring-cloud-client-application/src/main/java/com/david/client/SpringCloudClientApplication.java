package com.david.client;

import com.david.client.annotation.EnableRestClient;
import com.david.client.enevt.HttpRemoteAppEventListener;
import com.david.client.service.feign.clients.SayingService;
import com.david.client.service.rest.clients.SayingRestService;
import com.david.client.stream.SimpleMessageService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Created by sc on 2019-03-05.
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableScheduling
@EnableFeignClients(clients = SayingService.class) // 引入 FeignClient spring的
@EnableRestClient(clients = SayingRestService.class) // 引入 @RestClient 自定义的
//@EnableBinding(SimpleMessageService.class)
public class SpringCloudClientApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(SpringCloudClientApplication.class)
                .web(WebApplicationType.SERVLET)
               // .listeners(new HttpRemoteAppEventListener())
                .run(args);
    }
}
