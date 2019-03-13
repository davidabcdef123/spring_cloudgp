package com.david.cloud.binderhttp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import sun.net.www.protocol.http.HttpURLConnection;

/**
 * Created by sc on 2019-03-13.
 */
@Configuration
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class HttpMessageChannelWebAutoConfiguration {

    @Bean
    public MessageReceiverController controller(){
        return new MessageReceiverController();
    }

    @Bean
    public MessageReceiverHandlerInterceptor interceptor(){
        return new MessageReceiverHandlerInterceptor();
    }

    @Autowired
    private MessageReceiverHandlerInterceptor interceptor;

    @Configuration
    public class MyWebMvcConfigurer implements WebMvcConfigurer{
        public void addInterceptors(InterceptorRegistry registry){
            registry.addInterceptor(interceptor);
        }
    }
}
