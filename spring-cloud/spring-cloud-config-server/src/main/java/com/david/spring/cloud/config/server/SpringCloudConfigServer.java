package com.david.spring.cloud.config.server;

import org.apache.el.stream.Stream;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.environment.Environment;
import org.springframework.cloud.config.environment.PropertySource;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.cloud.config.server.environment.EnvironmentRepository;
import org.springframework.context.annotation.Bean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sc on 2019-03-04.
 */
@SpringBootApplication
@EnableConfigServer
public class SpringCloudConfigServer {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudConfigServer.class, args);
    }

    //http://localhost:10086/config/test
    @Bean
    public EnvironmentRepository evvironmentRepository(){
        return (String applicaiton, String profile,String lable)->{
            Environment environment = new Environment("default",profile);
            List<PropertySource> propertySources=environment.getPropertySources();
            Map<String, Object> source = new HashMap<>();
            source.put("name", "cccc");
            PropertySource propertySource = new PropertySource("map",source);
            // 追加 PropertySource
            propertySources.add(propertySource);
            return environment;
        };

    }



}
